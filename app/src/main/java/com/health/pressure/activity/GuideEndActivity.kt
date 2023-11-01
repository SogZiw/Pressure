package com.health.pressure.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.R
import com.health.pressure.adapter.GuideAdapter
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.admob.BaseAd
import com.health.pressure.databinding.ActivityGuideEndBinding
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.guideStep
import com.health.pressure.ext.stringValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GuideEndActivity : LifeActivity<ActivityGuideEndBinding>() {

    private lateinit var mAdapter: GuideAdapter
    override val layoutId: Int get() = R.layout.activity_guide_end
    private val guideIndex = MutableLiveData<Int>()

    override fun initView() {
        guideStep = 3
        binding.btnSkip.setOnClickListener {
            goNextPage<MainActivity>(true)
        }
        guideIndex.observe(this) {
            when (it) {
                1 -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.mainDesc.text = R.string.second_main_desc.stringValue
                    binding.secDesc.text = R.string.second_sec_desc.stringValue
                }
                2 -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.mainDesc.text = R.string.third_main_desc.stringValue
                    binding.secDesc.text = R.string.third_sec_desc.stringValue
                }
                else -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.mainDesc.text = R.string.first_main_desc.stringValue
                    binding.secDesc.text = R.string.first_sec_desc.stringValue
                }
            }
        }
    }

    override fun initData() {
        mAdapter = GuideAdapter(activity, mutableListOf(R.drawable.ic_guide_first, R.drawable.ic_guide_second, R.drawable.ic_guide_third))
        binding.list.itemAnimator = null
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.list)
        binding.list.adapter = mAdapter
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    kotlin.runCatching {
                        guideIndex.postValue(snapHelper.findTargetSnapPosition(recyclerView.layoutManager, 0, 0))
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showNative()
    }

    private var nativeAd: BaseAd? = null

    private fun showNative() {
        AdInstance.hisAd.keepLoader(this) {
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