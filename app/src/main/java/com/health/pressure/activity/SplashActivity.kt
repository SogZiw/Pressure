package com.health.pressure.activity

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.health.pressure.R
import com.health.pressure.activity.model.SplashVM
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.databinding.ActivitySplashBinding
import com.health.pressure.ext.buildAgreement
import com.health.pressure.ext.firstLaunch
import com.health.pressure.ext.goNextPage

@SuppressLint("CustomSplashScreen")
class SplashActivity : LifeActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
    private val viewModel by viewModels<SplashVM>()

    override fun initView() {
        viewModel.progress.observe(this) {
            binding.progress.progress = it
        }
        viewModel.showAd.observe(this) {
            AdInstance.openAd.showFullScreenAd(activity) { goNext() }
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked -> binding.btnStart.isEnabled = isChecked }
        binding.btnStart.setOnClickListener {
            firstLaunch = false
            goNextPage<GuideActivity>(true)
        }
        loadAd()
    }

    override fun initData() {
        viewModel.startAnim(onUpdateValue = {
            if (AdInstance.openAd.canShowFullScreenAd(this)) {
                viewModel.maxAnim { viewModel.showAd.postValue(true) }
            }
        }, onEnd = { goNext() })
    }

    private fun loadAd() {
        AdInstance.openAd.loadAd(activity)
        AdInstance.saveAd.loadAd(activity)
    }

    private fun goNext() {
        if (firstLaunch) {
            binding.agreement.movementMethod = LinkMovementMethod.getInstance()
            binding.agreement.text = buildAgreement()
            binding.first.isVisible = true
            binding.reload.isVisible = false
        } else goNextPage<MainActivity>(true)
    }
}