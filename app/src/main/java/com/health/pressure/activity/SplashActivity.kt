package com.health.pressure.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.text.method.LinkMovementMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.health.pressure.R
import com.health.pressure.activity.model.SplashVM
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.clock.ClockUpper
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.ActivitySplashBinding
import com.health.pressure.ext.buildAgreement
import com.health.pressure.ext.firstLaunch
import com.health.pressure.ext.goNextPage

@SuppressLint("CustomSplashScreen")
class SplashActivity : LifeActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
    private val viewModel by viewModels<SplashVM>()
    private val jumpType by lazy { intent?.getIntExtra("JumpType", -1) ?: -1 }
    private val nfLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun initView() {
        viewModel.progress.observe(this) {
            binding.progress.progress = it
        }
        viewModel.showAd.observe(this) {
            AdInstance.openAd.showFullScreenAd(activity) { goNext() }
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked -> binding.btnStart.isEnabled = isChecked }
        binding.btnStart.setOnClickListener {
            goNextPage<SelectLocalActivity>(true)
        }
        loadAd()
    }

    override fun initData() {
        viewModel.startAnim(onUpdateValue = {
            if (AdInstance.openAd.canShowFullScreenAd(this)) {
                viewModel.maxAnim { viewModel.showAd.postValue(true) }
            }
        }, onEnd = { goNext() })
        EventPost.session()
        if (arrayOf(0, 1).any { it == jumpType }) ClockUpper.cancel()
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && NotificationManagerCompat.from(this).areNotificationsEnabled().not()) {
                nfLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            when (jumpType) {
                0 -> goNextPage<RecordActivity>(true)
                1 -> goNextPage<MainActivity>(true) { putExtra("ChangeTab", 2) }
                else -> goNextPage<MainActivity>(true)
            }
        }
    }
}