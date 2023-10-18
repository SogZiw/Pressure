package com.health.pressure.activity

import android.annotation.SuppressLint
import com.health.pressure.R
import com.health.pressure.adapter.AlarmListAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.basic.bean.AlarmItem
import com.health.pressure.databinding.ActivityAlarmBinding
import com.health.pressure.ext.alarmInfo
import com.health.pressure.ext.createCommonDialog
import com.health.pressure.ext.formatTime
import com.health.pressure.ext.hhmmPattern
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : BaseActivity<ActivityAlarmBinding>() {

    override val layoutId: Int get() = R.layout.activity_alarm
    private lateinit var adapter: AlarmListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        binding.btnAdd.setOnClickListener {
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.plz_set_alarm))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setDefaultTime(System.currentTimeMillis())
                .setDisplayType(DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.add)) { milliseconds ->
                    val format = milliseconds.formatTime(hhmmPattern)
                    if (adapter.datas.any { it.timeFormat == format }) {
                        adapter.datas.removeIf { it.timeFormat == format }
                    }
                    adapter.datas.add(AlarmItem(format))
                    adapter.notifyDataSetChanged()
                    alarmInfo = adapter.datas
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
        adapter = AlarmListAdapter(this, ArrayList(alarmInfo), onDel = {
            createCommonDialog(msg = getString(R.string.sure_to_del), onPositive = {
                adapter.datas.remove(it)
                adapter.notifyDataSetChanged()
                alarmInfo = adapter.datas
            })
        }, onEdit = { item ->
            CardDatePickerDialog.builder(this)
                .setTitle(getString(R.string.change_alarm))
                .showBackNow(false)
                .showFocusDateInfo(false)
                .showDateLabel(false)
                .setWrapSelectorWheel(false)
                .setDefaultTime(SimpleDateFormat(hhmmPattern, Locale.ENGLISH).parse(item.timeFormat)?.time ?: System.currentTimeMillis())
                .setDisplayType(DateTimeConfig.HOUR, DateTimeConfig.MIN)
                .setOnChoose(text = getString(R.string.sure)) { milliseconds ->
                    val format = milliseconds.formatTime(hhmmPattern)
                    if (adapter.datas.any { it.timeFormat == format }) {
                        adapter.datas.removeIf { it.timeFormat == format }
                    }
                    item.timeFormat = format
                    adapter.notifyDataSetChanged()
                    alarmInfo = adapter.datas
                }
                .setOnCancel(text = getString(R.string.cancel))
                .build().show()
        }, onChangeOpen = {
            alarmInfo = adapter.datas
        })
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

}