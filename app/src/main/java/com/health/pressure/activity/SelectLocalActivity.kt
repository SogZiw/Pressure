package com.health.pressure.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.adapter.SelectLocalAdapter
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.bean.LocalSelection
import com.health.pressure.basic.bean.LocalState
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.ActivitySelectLocalBinding
import com.health.pressure.ext.defLang
import com.health.pressure.ext.firstLaunch
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.guideStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectLocalActivity : LifeActivity<ActivitySelectLocalBinding>() {

    override val layoutId: Int get() = R.layout.activity_select_local
    private lateinit var adapter: SelectLocalAdapter
    private val locals by lazy {
        mutableListOf(LocalState.English,
            LocalState.Traditional,
            LocalState.Japanese,
            LocalState.French,
            LocalState.German,
            LocalState.Korean,
            LocalState.Spanish,
            LocalState.Turkish,
            LocalState.Polish,
            LocalState.Italian,
            LocalState.Portuguese,
            LocalState.Thai,
            LocalState.Romanian,
            LocalState.Arabic
        )
    }
    private val fromSet by lazy { intent?.getBooleanExtra("fromSet", false) ?: false }

    override fun initView() {
        binding.btnSure.setOnClickListener {
            firstLaunch = false
            defLang = locals.getOrNull(adapter.lastPos)?.languageCode ?: LocalState.English.languageCode
            if (fromSet) goNextPage<MainActivity>(true) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
            else {
                AdInstance.saveAd.showFullScreenAd(this, "int_new_language") {
                    if (ClockManager.judgeState()) goNextPage<SelectUnitActivity>(true) else goNextPage<GuideActivity>(true)
                }
            }
        }
        if (!fromSet) guideStep = 1
        AdInstance.saveAd.loadAd(activity)
        EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to "int_new_language"))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {
        val datas = locals.map { LocalSelection(it) }.toMutableList()
        var lastPos = 0
        if (fromSet) datas.onEachIndexed { index, local ->
            if (local.localState.languageCode == defLang) {
                local.selected = true
                lastPos = index
                binding.btnSure.isEnabled = true
            }
        }
        adapter = SelectLocalAdapter(this, datas) { binding.btnSure.isEnabled = true }
        adapter.lastPos = lastPos
        binding.list.run {
            isScrollbarFadingEnabled = false
            itemAnimator = null
        }
        binding.list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (ClockManager.judgeState()) {
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

    override fun onBackPressed() = Unit

}