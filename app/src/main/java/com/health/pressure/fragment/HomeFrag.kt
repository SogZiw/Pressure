package com.health.pressure.fragment

import com.health.pressure.R
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragHomeBinding
import com.health.pressure.ext.formatTime

class HomeFrag : BaseFrag<FragHomeBinding>() {

    override val layoutId: Int get() = R.layout.frag_home

    override fun initView() {
        binding.title.text = System.currentTimeMillis().formatTime("MM/dd")
    }

}