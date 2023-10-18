package com.health.pressure.activity

import android.annotation.SuppressLint
import com.health.pressure.R
import com.health.pressure.adapter.AlarmListAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityAlarmBinding
import com.health.pressure.ext.alarmInfo
import com.health.pressure.ext.createCommonDialog
import com.health.pressure.ext.formatTime
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

class AlarmActivity : BaseActivity<ActivityAlarmBinding>() {

    override val layoutId: Int get() = R.layout.activity_alarm
    private lateinit var adapter: AlarmListAdapter

    override fun initView() {
        binding.btnAdd.setOnClickListener {
            val current = System.currentTimeMillis()
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.plz_set_alarm))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setMaxTime(current)
                .setDefaultTime(current)
                .setDisplayType(DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.add)) { milliseconds ->
                    val format = milliseconds.formatTime("HH:mm")
                    
                }
                .setOnCancel(text = getString(R.string.cancel))
                .build().show()
        }
    }

    override fun initData() {
        initAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        val data = ArrayList(alarmInfo)
        adapter = AlarmListAdapter(this, data, onDel = {
            createCommonDialog(msg = getString(R.string.sure_to_del), onPositive = {
                data.remove(it)
                adapter.notifyDataSetChanged()
            })
        }, onEdit = {

        })
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

}