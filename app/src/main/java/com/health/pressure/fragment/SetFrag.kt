package com.health.pressure.fragment

import com.health.pressure.R
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragSetBinding

class SetFrag : BaseFrag<FragSetBinding>() {

    override val layoutId: Int get() = R.layout.frag_set

    override fun initView() {
        binding.btnPrivacy.setOnClickListener {

        }
        binding.btnUserAgreement.setOnClickListener {

        }
    }

}