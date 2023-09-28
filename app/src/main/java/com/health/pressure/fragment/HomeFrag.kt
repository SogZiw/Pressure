package com.health.pressure.fragment

import com.health.pressure.R
import com.health.pressure.adapter.InfoAdapter
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.InfoData
import com.health.pressure.databinding.FragHomeBinding
import com.health.pressure.ext.formatTime

class HomeFrag : BaseFrag<FragHomeBinding>() {

    override val layoutId: Int get() = R.layout.frag_home

    override fun initView() {
        binding.title.text = System.currentTimeMillis().formatTime("MM/dd")
    }

    override fun initData() {
        val adapter = InfoAdapter(activity, InfoData.values().toMutableList().subList(0, 5)) {

        }
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

}