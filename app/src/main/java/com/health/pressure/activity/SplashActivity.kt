package com.health.pressure.activity

import android.annotation.SuppressLint
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
}