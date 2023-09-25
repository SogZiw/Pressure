package com.health.pressure.activity

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.health.pressure.R
import com.health.pressure.activity.model.SpalshVM
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivitySplashBinding
import com.health.pressure.ext.buildAgreement
import com.health.pressure.ext.firstLaunch
import com.health.pressure.ext.goNextPage

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
    private val viewModel by viewModels<SpalshVM>()

    override fun initData() {
        viewModel.progress.observe(this) {
            binding.progress.progress = it
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked -> binding.btnStart.isEnabled = isChecked }
        binding.btnStart.setOnClickListener {
            firstLaunch = false
            goNextPage<MainActivity>(true)
        }
        if (firstLaunch) {
            binding.agreement.movementMethod = LinkMovementMethod.getInstance()
            binding.agreement.text = buildAgreement()
            binding.first.isVisible = true
            binding.reload.isVisible = false
        } else {
            binding.first.isVisible = false
            binding.reload.isVisible = true
            viewModel.startAnim {
                goNextPage<MainActivity>(true)
            }
        }
    }
}