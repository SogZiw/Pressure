package com.health.pressure.fragment

import com.health.pressure.Constants
import com.health.pressure.R
import com.health.pressure.activity.WebviewActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragSetBinding
import com.health.pressure.ext.goNext

class SetFrag : BaseFrag<FragSetBinding>() {

    override val layoutId: Int get() = R.layout.frag_set

    override fun initView() {
        binding.btnPrivacy.setOnClickListener {
            activity.goNext<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.PRIVACY_POLICY) }
        }
        binding.btnUserAgreement.setOnClickListener {
            activity.goNext<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.USER_AGREE) }
        }
    }

}