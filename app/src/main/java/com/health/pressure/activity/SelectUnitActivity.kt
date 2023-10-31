package com.health.pressure.activity

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.adapter.SelectUnitAdapter
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.basic.bean.UnitItem
import com.health.pressure.databinding.ActivitySelectUnitBinding
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.isHgUnit
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelectUnitActivity : LifeActivity<ActivitySelectUnitBinding>() {

    override val layoutId: Int get() = R.layout.activity_select_unit
    private lateinit var adapter: SelectUnitAdapter
    private val fromSet by lazy { intent?.getBooleanExtra("fromSet", false) ?: false }
    private val unitList by lazy { mutableListOf(UnitItem("mmHg", isHgUnit), UnitItem("kPa", !isHgUnit)) }

    override fun initView() {
        binding.btnSure.setOnClickListener {
            isHgUnit = 0 == adapter.lastPos
            if (fromSet) goNextPage<MainActivity>(true) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) }
            else goNextPage<GuideActivity>(true)
        }
    }

    override fun initData() {
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
        showNative()
    }

    private var nativeAd: BaseAd? = null

    private fun showNative() {
        AdInstance.hisAd.nativeLoader(this) {
            if (it) {
                lifecycleScope.launch {
                    while (!resumed) delay(220L)
                    AdInstance.hisAd.showNativeAd(activity, binding.nativeView) { baseAd -> nativeAd = baseAd }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAd?.destroy()
    }


}