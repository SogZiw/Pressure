package com.health.pressure.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.health.pressure.R
import com.health.pressure.activity.model.SpalshVM
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivitySplashBinding
import com.health.pressure.ext.buildAgreement

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
    private val viewModel by viewModels<SpalshVM>()

    override fun initData() {
        viewModel.progress.observe(this) {
            binding.progress.progress = it
        }
        binding.agreement.text = buildAgreement()
        viewModel.startAnim {
            startActivity(Intent(activity, MainActivity::class.java))
            finish()
        }
    }
}