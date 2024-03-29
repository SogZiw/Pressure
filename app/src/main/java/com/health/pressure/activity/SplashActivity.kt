package com.health.pressure.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.text.method.LinkMovementMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.activity.model.SplashVM
import com.health.pressure.basic.InfoData
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.basic.clock.ClockUpper
import com.health.pressure.basic.http.EventPost
import com.health.pressure.databinding.ActivitySplashBinding
import com.health.pressure.ext.*
import com.health.pressure.guideShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : LifeActivity<ActivitySplashBinding>() {

    override val layoutId: Int get() = R.layout.activity_splash
    private val viewModel by viewModels<SplashVM>()
    private val jumpType by lazy { intent?.getIntExtra("JumpType", -1) ?: -1 }
    private val clockEvent by lazy { intent?.getStringExtra("ClockTypeEvent") }
    private val nfLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        EventPost.firebaseEvent("bbp_POST_click", hashMapOf("success" to if (it) "yes" else "no"))
    }

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
            EventPost.firebaseEvent("launch_start")
        }
        loadAd()
        startForegroundService()
    }

    override fun initData() {
        viewModel.startAnim(onUpdateValue = {
            if (AdInstance.openAd.canShowFullScreenAd(this)) {
                viewModel.maxAnim { viewModel.showAd.postValue(true) }
            }
        }, onEnd = { goNext() })
        EventPost.session()
        if (arrayOf(0, 1).any { it == jumpType }) {
            ClockUpper.cancel()
            EventPost.firebaseEvent("bbppop_all_click")
        }
        clockEvent?.let { EventPost.firebaseEvent("${it}_click") }
        EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to AdLocation.OPEN.placeName))
        EventPost.firebaseEvent("launch_page_show")
        if (firstLaunch) EventPost.firebaseEvent("bbp_splash_page")
    }

    private fun loadAd() {
        AdInstance.openAd.loadAd(activity)
        AdInstance.saveAd.loadAd(activity)
        AdInstance.tabAd.loadAd(activity)
    }

    private fun goNext() {
        if (firstLaunch) {
            binding.agreement.movementMethod = LinkMovementMethod.getInstance()
            binding.agreement.text = buildAgreement()
            binding.first.isVisible = true
            binding.reload.isVisible = false
            guideStep = 0

            AdInstance.alarmAd.loadAd(activity)
            lifecycleScope.launch(Dispatchers.Main) {
                delay(300L)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && NotificationManagerCompat.from(activity).areNotificationsEnabled().not()) {
                    nfLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    EventPost.firebaseEvent("bbp_POST")
                }
            }
            EventPost.firebaseEvent("bbp_start_page")
        } else {
            when (jumpType) {
                0 -> {
                    goNextPage<RecordActivity>(true)
                    EventPost.firebaseEvent("bbppop_all_page")
                }
                1 -> {
                    InfoData.values().random().let {
                        goNextPage<InfoDetailActivity>(true) { putExtra("InfoData", it) }
                    }
                    EventPost.firebaseEvent("bbppop_all_page")
                }
                else -> {
                    when (guideStep) {
                        0 -> goNextPage<SelectLocalActivity>(true)
                        1 -> {
                            if (guideShow) {
                                if (ClockManager.judgeState()) goNextPage<SelectUnitActivity>(true) else goNextPage<GuideActivity>(true)
                            } else goNextPage<MainActivity>(true)
                        }
                        2 -> {
                            if (guideShow) {
                                if (AdInstance.guideSwitch && ClockManager.judgeState()) goNextPage<GuideEndActivity>(true) else goNextPage<GuideActivity>(true)
                            } else goNextPage<MainActivity>(true)
                        }
                        else -> goNextPage<MainActivity>(true)
                    }
                }
            }
        }
    }

    override fun onBackPressed() = Unit
}