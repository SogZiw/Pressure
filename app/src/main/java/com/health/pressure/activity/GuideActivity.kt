package com.health.pressure.activity

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.health.pressure.R
import com.health.pressure.adapter.GuideAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityGuideBinding
import com.health.pressure.ext.goNextPage
import com.health.pressure.ext.stringValue

class GuideActivity : BaseActivity<ActivityGuideBinding>() {

    private lateinit var mAdapter: GuideAdapter
    override val layoutId: Int get() = R.layout.activity_guide
    private val guideIndex = MutableLiveData<Int>()

    override fun initView() {
        binding.btnNext.setOnClickListener {
            val nextIndex = (guideIndex.value ?: 0) + 1
            if (nextIndex > 2) {
                goNextPage<MainActivity>(true)
                return@setOnClickListener
            }
            binding.list.scrollToPosition(nextIndex)
            guideIndex.postValue(nextIndex)
        }
        guideIndex.observe(this) {
            when (it) {
                1 -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.btnNext.text = R.string.next.stringValue
                    binding.mainDesc.text = R.string.second_main_desc.stringValue
                    binding.secDesc.text = R.string.second_sec_desc.stringValue
                }
                2 -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.btnNext.text = R.string.start_record.stringValue
                    binding.mainDesc.text = R.string.third_main_desc.stringValue
                    binding.secDesc.text = R.string.third_sec_desc.stringValue
                }
                else -> {
                    binding.indicator1.setBackgroundResource(R.drawable.bg_00c5a8_r10)
                    binding.indicator2.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.indicator3.setBackgroundResource(R.drawable.bg_f0f0f0_r10)
                    binding.btnNext.text = R.string.next.stringValue
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


}