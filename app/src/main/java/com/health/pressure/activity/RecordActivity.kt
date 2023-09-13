package com.health.pressure.activity

import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.widget.wheel.WheelView
import com.health.pressure.databinding.ActivityRecordBinding
import com.health.pressure.ext.formatTime
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

class RecordActivity : BaseActivity<ActivityRecordBinding>() {

    private var currentTime = System.currentTimeMillis()
    private val wheelData by lazy {
        val list = mutableListOf<String>()
        for (i in 20..300) {
            list.add(i.toString())
        }
        list
    }

    override val layoutId: Int get() = R.layout.activity_record

    override fun initView() {
        binding.btnSave.setOnClickListener {
            
        }
        binding.btnTime.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.set_datetime))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setMaxTime(System.currentTimeMillis())
                .setDefaultTime(currentTime)
                .setDisplayType(DateTimeConfig.YEAR, DateTimeConfig.MONTH, DateTimeConfig.DAY, DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.sure)) { milliseconds ->
                    currentTime = milliseconds
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
        binding.sysWheel.addOnItemSelectedListener { _, index -> }
        binding.sysWheel.currentItem = 119 - 20
        binding.diaWheel.currentItem = 79 - 20
    }

}