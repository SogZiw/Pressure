package com.health.pressure.activity

import com.health.pressure.R
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityRecordBinding
import com.health.pressure.ext.formatTime
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

class RecordActivity : BaseActivity<ActivityRecordBinding>() {

    private var currentTime = System.currentTimeMillis()

    override val layoutId: Int get() = R.layout.activity_record

    override fun initView() {
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
    }

}