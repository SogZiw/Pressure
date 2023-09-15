package com.health.pressure.activity

import android.content.Intent
import com.health.pressure.R
import com.health.pressure.adapter.PressureHistoryAdapter
import com.health.pressure.basic.BaseActivity
import com.health.pressure.databinding.ActivityHistoryBinding
import com.health.pressure.datas

class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {

    override val layoutId: Int get() = R.layout.activity_history

    private fun initAdapter() {
        val adapter = PressureHistoryAdapter(activity, datas) {
            startActivity(Intent(activity, RecordActivity::class.java).apply {
                putExtra("isAdd", false)
                putExtra("PressureData", it)
            })
        }
        binding.list.itemAnimator = null
        binding.list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

}