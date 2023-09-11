package com.health.pressure.activity

import android.annotation.SuppressLint
import android.content.Intent
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash

    override fun initData() {
        startActivity(Intent(activity, MainActivity::class.java))
    }
}