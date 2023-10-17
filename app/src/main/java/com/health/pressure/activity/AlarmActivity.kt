package com.health.pressure.activity

import com.health.pressure.R
import com.health.pressure.adapter.AlarmListAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityAlarmBinding

class AlarmActivity : BaseActivity<ActivityAlarmBinding>() {

    override val layoutId: Int get() = R.layout.activity_alarm
    private lateinit var adapter: AlarmListAdapter

    override fun initView() {

    }

    override fun initData() {
        adapter = AlarmListAdapter(this, mutableListOf(), onDel = {

        }, onEdit = {

        })
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

}