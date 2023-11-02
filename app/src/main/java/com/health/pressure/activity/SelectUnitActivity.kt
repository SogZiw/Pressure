package com.health.pressure.activity

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.adapter.SelectUnitAdapter
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.bean.UnitItem
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.ActivitySelectUnitBinding
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.guideStep
import com.health.pressure.ext.isHgUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectUnitActivity : LifeActivity<ActivitySelectUnitBinding>() {

    override val layoutId: Int get() = R.layout.activity_select_unit
    private lateinit var adapter: SelectUnitAdapter
    private val fromSet by lazy { intent?.getBooleanExtra("fromSet", false) ?: false }
    private val unitList by lazy { mutableListOf(UnitItem("mmHg"), UnitItem("kPa")) }

    override fun initView() {
        binding.btnSure.setOnClickListener {
            if (adapter.datas.all { it.checked.not() }) return@setOnClickListener
            isHgUnit = 0 == adapter.lastPos
            if (fromSet) {
                if (AdInstance.unitSwitch) {
                    AdInstance.tabAd.showFullScreenIfCan(this, "int_new_unit") {
                        goNextPage<MainActivity>(true) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
                    }
                } else goNextPage<MainActivity>(true) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
            } else {
                if (AdInstance.unitSwitch && ClockManager.judgeState()) {
                    AdInstance.tabAd.showFullScreenAd(this, "int_new_unit") {
                        if (AdInstance.guideSwitch) goNextPage<GuideEndActivity>(true) else goNextPage<GuideActivity>(true)
                    }
                } else goNextPage<GuideActivity>(true)
            }
        }
        if (!fromSet) guideStep = 2
        AdInstance.tabAd.loadAd(activity)
        EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to "int_new_unit"))
        EventPost.firebaseEvent("bbp_unit_page")
    }

    override fun initData() {
        if (fromSet) {
            unitList.firstOrNull()?.checked = isHgUnit
            unitList.getOrNull(1)?.checked = !isHgUnit
        }
        adapter = SelectUnitAdapter(this, unitList) { binding.btnSure.isEnabled = true }
        adapter.lastPos = if (true == unitList.firstOrNull()?.checked || !fromSet) 0 else 1
        binding.list.run {
            isScrollbarFadingEnabled = false
            itemAnimator = null
        }
        binding.list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (AdInstance.unitSwitch && ClockManager.judgeState()) {
            showNative()
            EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to AdLocation.ALARM.placeName))
        }
    }

    private var nativeAd: BaseAd? = null

    private fun showNative() {
        AdInstance.alarmAd.keepLoader(this) {
            if (it) {
                lifecycleScope.launch {
                    while (!resumed) delay(220L)
                    AdInstance.alarmAd.showNativeAd(activity, binding.nativeView) { baseAd -> nativeAd = baseAd }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }

    override fun onBackPressed() {
        if (fromSet || !ClockManager.judgeState()) super.onBackPressed() else Unit
    }

}