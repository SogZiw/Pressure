package com.health.pressure.activity

import DataManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.widget.wheel.WheelView
import com.health.pressure.dao.Pressure
import com.health.pressure.dao.PressureState
import com.health.pressure.databinding.ActivityRecordBinding
import com.health.pressure.ext.formatTime
import com.health.pressure.wheelData
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class RecordActivity : BaseActivity<ActivityRecordBinding>() {

    private var datetime = System.currentTimeMillis()
    override val layoutId: Int get() = R.layout.activity_record
    private val isAdd by lazy { intent?.getBooleanExtra("isAdd", true) ?: true }
    private val data by lazy { intent?.getParcelableExtra<Pressure>("PressureData") }

    override fun initView() {
        binding.btnSave.setOnClickListener {

        }
        binding.btnDelete.setOnClickListener {
            data?.let { pressure ->
                DataManager.deleteData(pressure)
            }
            finish()
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
        binding.tvTime.text = System.currentTimeMillis().formatTime()
        binding.sysWheel.adapter = object : WheelView.Adapter() {
            override fun getItemCount(): Int = wheelData.size
            override fun getItem(position: Int): String = wheelData[position]
        }
        binding.diaWheel.adapter = object : WheelView.Adapter() {
            override fun getItemCount(): Int = wheelData.size
            override fun getItem(position: Int): String = wheelData[position]
        }
        binding.sysWheel.addOnItemSelectedListener { _, _ -> formatState() }
        binding.diaWheel.addOnItemSelectedListener { _, _ -> formatState() }
        binding.btnDelete.isVisible = !isAdd
    }

    private fun formatState() {
        val sys = wheelData.getOrNull(binding.sysWheel.currentItem)?.toIntOrNull()
        val dia = wheelData.getOrNull(binding.diaWheel.currentItem)?.toIntOrNull()
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
        binding.sysWheel.currentItem = 119 - 20
        binding.diaWheel.currentItem = 79 - 20
    }

}