package com.health.pressure.fragment

import DataManager
import android.content.Intent
import com.health.pressure.R
import com.health.pressure.activity.RecordActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragHistoryBinding
import com.health.pressure.ext.*
import kotlin.math.roundToInt


class HistoryFrag : BaseFrag<FragHistoryBinding>() {

    override val layoutId: Int get() = R.layout.frag_history

    override fun initView() {
        binding.timeRange.setOnClickListener {
            showBottomMenu { title, id ->
                binding.timeRange.text = title
                initRangeData(id)
            }
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        initRangeData(R.id.range1)
    }

    private fun initRangeData(id: Int) {

        fun calculate(start: Long, end: Long) {
            DataManager.getPressures(start, end).observe(viewLifecycleOwner) { datas ->
                val sys = datas.map { it.sys }.average()
                val dia = datas.map { it.dia }.average()
                binding.sys.text = if (sys.isNaN()) "0" else "${sys.roundToInt()}"
                binding.dia.text = if (dia.isNaN()) "0" else "${dia.roundToInt()}"
            }
        }

        when (id) {
            R.id.range1 -> calculate(last24HoursTime, currentTimeMills)
            R.id.range2 -> calculate(weekStartTime, currentTimeMills)
            R.id.range3 -> calculate(last7DaysTime, currentTimeMills)
            R.id.range4 -> calculate(thisMonthStartTime, currentTimeMills)
            R.id.range5 -> calculate(lastMonthStartTime, lastMonthEndTime)
            else -> Unit
        }
    }

}