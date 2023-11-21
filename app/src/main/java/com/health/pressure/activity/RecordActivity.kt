package com.health.pressure.activity

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.basic.AppLife
import com.health.pressure.basic.LifeActivity
import com.health.pressure.basic.ad.AdInstance
import com.health.pressure.basic.ad.AdLocation
import com.health.pressure.basic.clock.ClockManager
import com.health.pressure.basic.http.EventPost
import com.health.pressure.basic.widget.wheel.WheelView
import com.health.pressure.dao.DataManager
import com.health.pressure.dao.Pressure
import com.health.pressure.dao.PressureState
import com.health.pressure.databinding.ActivityRecordBinding
import com.health.pressure.datas
import com.health.pressure.ext.*
import com.health.pressure.wheelData
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class RecordActivity : LifeActivity<ActivityRecordBinding>() {

    private var datetime = System.currentTimeMillis()
    override val layoutId: Int get() = R.layout.activity_record
    private val isAdd by lazy { intent?.getBooleanExtra("isAdd", true) ?: true }
    private val data by lazy { intent?.getParcelableExtra<Pressure>("PressureData") }
    private val isGuide by lazy { intent?.getBooleanExtra("isGuide", false) ?: false }
    private val nfLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun initView() {
        binding.btnSave.setOnClickListener {
            val sys = wheelData.getOrNull(binding.sysWheel.currentItem)
            val dia = wheelData.getOrNull(binding.diaWheel.currentItem)
            if (null == sys || null == dia) {
                R.string.error_tips_extra.toast()
                return@setOnClickListener
            }
            if (dia >= sys) {
                R.string.error_tips.toast()
                return@setOnClickListener
            }

            fun addNew() {
                val data = Pressure(sys = sys, dia = dia, record_time = datetime, format_time = datetime.formatTime())
                DataManager.insertData(data)
                showAdAndFinish()
            }

            if (isAdd.not() && null != data) {
                data?.run {
                    this.sys = sys
                    this.dia = dia
                    this.record_time = datetime
                    this.format_time = datetime.formatTime()
                }
                data?.let { DataManager.updateData(it) }
                showAdAndFinish()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val oldData = DataManager.sameOrNull(datetime.formatTime())
                    if (null == oldData) addNew()
                    else {
                        DataManager.updateData(oldData.also {
                            it.sys = sys
                            it.dia = dia
                            it.record_time = datetime
                            it.format_time = datetime.formatTime()
                        })
                        showAdAndFinish()
                    }
                }
            }
        }
        binding.btnDelete.setOnClickListener {
            data?.let { pressure ->
                DataManager.deleteData(pressure)
                runCatching {
                    datas.remove(pressure)
                }
            }
            showAdAndFinish()
        }
        binding.btnTime.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.set_datetime))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setMaxTime(System.currentTimeMillis())
                .setDefaultTime(datetime)
                .setDisplayType(DateTimeConfig.YEAR, DateTimeConfig.MONTH, DateTimeConfig.DAY, DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.sure)) { milliseconds ->
                    datetime = milliseconds
                    binding.tvTime.text = milliseconds.formatTime()
                }
                .setOnCancel(text = getString(R.string.cancel))
                .build().show()
        }
        binding.sysUnit.text = getUnit(true)
        binding.diaUnit.text = getUnit(true)
        binding.sysWheel.adapter = object : WheelView.Adapter() {
            override fun getItemCount(): Int = wheelData.size
            override fun getItem(position: Int): String = wheelData[position].getFormatUnit()
        }
        binding.diaWheel.adapter = object : WheelView.Adapter() {
            override fun getItemCount(): Int = wheelData.size
            override fun getItem(position: Int): String = wheelData[position].getFormatUnit()
        }
        binding.sysWheel.addOnItemSelectedListener { _, _ -> formatState() }
        binding.diaWheel.addOnItemSelectedListener { _, _ -> formatState() }
        binding.btnDelete.isVisible = !isAdd
        AdInstance.saveAd.loadAd(activity)
    }

    private fun showAdAndFinish() {
        lifecycleScope.launch(Dispatchers.Main) {
            if (AdInstance.goodSwitch) AdInstance.saveAd.showFullScreenAd(activity) { autoNext() } else autoNext()
        }
    }

    private fun formatState() {
        val sys = wheelData.getOrNull(binding.sysWheel.currentItem)
        val dia = wheelData.getOrNull(binding.diaWheel.currentItem)
        if (sys != null && dia != null) {
            val dataset = Pressure(sys = sys, dia = dia, record_time = datetime, format_time = datetime.formatTime())
            changeState(dataset.state)
        }
    }

    private fun changeState(state: PressureState) {
        fun changeBis(bis: Float) {
            val set = ConstraintSet().apply {
                clone(binding.layoutSlider)
                setHorizontalBias(R.id.tinySlider, bis)
            }
            set.applyTo(binding.layoutSlider)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            binding.state.setTextColor(state.stateColor)
            binding.stateContent.setTextColor(state.stateColor)
            binding.state.text = state.stateName
            binding.stateContent.text = state.stateContent
            binding.stateExtra.text = state.stateExtra
            changeBis(state.sliderBis)
        }
    }

    override fun initData() {
        binding.title.text = if (isAdd) R.string.new_record.stringValue else R.string.edit_record.stringValue
        datetime = data?.record_time ?: System.currentTimeMillis()
        binding.tvTime.text = data?.format_time ?: datetime.formatTime()
        binding.sysWheel.currentItem = (data?.sys ?: 119) - 20
        binding.diaWheel.currentItem = (data?.dia ?: 79) - 20
        changeState(data?.state ?: PressureState.Normal)
        EventPost.firebaseEvent("tk_ad_chance", hashMapOf("ad_pos_id" to AdLocation.SAVE.placeName))
        EventPost.firebaseEvent("record_page")
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300L)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && NotificationManagerCompat.from(activity).areNotificationsEnabled().not()) {
                nfLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        EventPost.firebaseEvent("bbp_addrecord", hashMapOf("source" to if (isGuide) "open" else "notifi"))
    }

    private fun autoNext() {
        if (AppLife.activitys.any { it is PressureRecordActivity }) finish() else goNextPage<PressureRecordActivity>(true)
    }

    override fun onBackPressed() {
        if (ClockManager.judgeState()) AdInstance.saveAd.showFullScreenAd(activity) { autoNext() } else autoNext()
    }

}