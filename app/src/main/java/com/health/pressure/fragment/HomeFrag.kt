package com.health.pressure.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.health.pressure.R
import com.health.pressure.activity.InfoDetailActivity
import com.health.pressure.activity.RecordActivity
import com.health.pressure.activity.model.MainVM
import com.health.pressure.adapter.InfoAdapter
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.InfoData
import com.health.pressure.databinding.FragHomeBinding
import com.health.pressure.ext.firstGuide
import com.health.pressure.ext.formatTime
import com.health.pressure.ext.goNextPage

class HomeFrag : BaseFrag<FragHomeBinding>() {

    private val viewModel by activityViewModels<MainVM>()
    override val layoutId: Int get() = R.layout.frag_home

    override fun initView() {
        binding.viewRecord.root.setOnClickListener {
            viewModel.changeTab.postValue(1)
            activity.goNextPage<RecordActivity>()
        }
        binding.guideLayout.setOnClickListener {
            binding.guideLayout.isVisible = false
            binding.viewRecord.root.performClick()
        }
        if (firstGuide) {
            firstGuide = false
            binding.guideLayout.isVisible = true
        }
    }

    override fun initData() {
        val adapter = InfoAdapter(activity, InfoData.values().toMutableList().subList(0, 5)) {
            activity.goNextPage<InfoDetailActivity> {
                putExtra("InfoData", it)
            }
        }
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.title.text = System.currentTimeMillis().formatTime("MM/dd")
    }

}