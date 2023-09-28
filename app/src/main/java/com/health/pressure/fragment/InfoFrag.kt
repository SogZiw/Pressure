package com.health.pressure.fragment

import com.health.pressure.R
import com.health.pressure.activity.InfoDetailActivity
import com.health.pressure.adapter.InfoAdapter
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.InfoData
import com.health.pressure.databinding.FragInfoBinding
import com.health.pressure.ext.goNextPage

class InfoFrag : BaseFrag<FragInfoBinding>() {

    override val layoutId: Int get() = R.layout.frag_info

    override fun initView() {
        val adapter = InfoAdapter(activity, InfoData.values().toMutableList()) {
            activity.goNextPage<InfoDetailActivity> {
                putExtra("InfoData", it)
            }
        }
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

}