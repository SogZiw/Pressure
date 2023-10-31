package com.health.pressure.fragment

import com.health.pressure.Constants
import com.health.pressure.R
import com.health.pressure.activity.AlarmActivity
import com.health.pressure.activity.SelectLocalActivity
import com.health.pressure.activity.SelectUnitActivity
import com.health.pressure.activity.WebviewActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.FragSetBinding
import com.health.pressure.ext.goNextPage

class SetFrag : BaseFrag<FragSetBinding>() {

    override val layoutId: Int get() = R.layout.frag_set

    override fun initView() {
        binding.btnLang.setOnClickListener {
            activity.goNextPage<SelectLocalActivity> { putExtra("fromSet", true) }
        }
        binding.btnUnit.setOnClickListener {
            activity.goNextPage<SelectUnitActivity> { putExtra("fromSet", true) }
        }
        binding.btnPrivacy.setOnClickListener {
            activity.goNextPage<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.PRIVACY_POLICY) }
        }
        binding.btnUserAgreement.setOnClickListener {
            activity.goNextPage<WebviewActivity> { putExtra(Constants.WEBVIEW_URL, Constants.USER_AGREE) }
        }
        binding.btnAlarm.setOnClickListener {
            activity.goNextPage<AlarmActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        EventPost.firebaseEvent("setting")
    }

}