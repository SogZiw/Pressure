package com.health.pressure.fragment

import DataManager
import androidx.lifecycle.lifecycleScope
import com.health.pressure.R
import com.health.pressure.basic.BaseFrag
import com.health.pressure.databinding.FragHistoryBinding
import com.health.pressure.ext.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFrag : BaseFrag<FragHistoryBinding>() {

    override val layoutId: Int get() = R.layout.frag_history

    override fun initView() {
        binding.timeRange.setOnClickListener {
            showBottomMenu { title, id ->
                binding.timeRange.text = title
                initRangeData(id)
            }
        }
    }

    override fun initData() {

    }

    private fun initRangeData(id: Int) {

        fun calculate(start: Long, end: Long) {
            lifecycleScope.launch(Dispatchers.IO) {
                val datas = DataManager.getPressures(start, end)
                val sys = datas.map { it.sys }.average()
                val dia = datas.map { it.sys }.average()
                launch(Dispatchers.Main) {

                }
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