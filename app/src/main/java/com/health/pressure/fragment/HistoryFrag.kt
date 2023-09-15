package com.health.pressure.fragment

import DataManager
import android.content.Intent
import android.graphics.Color
import com.github.mikephil.charting.components.YAxis
import com.health.pressure.R
import com.health.pressure.activity.RecordActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.widget.data.PressureData
import com.health.pressure.basic.widget.data.PressureDataSet
import com.health.pressure.basic.widget.data.PressureEntry
import com.health.pressure.databinding.FragHistoryBinding
import com.health.pressure.datas
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
        refreshChart()
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

    private fun refreshChart() {
        DataManager.getAllPressures().observe(this) { list ->
            datas.clear()
            datas.addAll(list)
            datas.sortBy { it.record_time }
            val chartData = mutableListOf<PressureEntry>()
            datas.forEachIndexed { index, pressure ->
                chartData.add(PressureEntry(index.toFloat(), pressure.sys, pressure.dia, pressure))
            }
            if (chartData.isEmpty()) {
                binding.chart.clear()
                return@observe
            }
            val dataSet = PressureDataSet(chartData, "Pressure").apply {
                axisDependency = YAxis.AxisDependency.LEFT
                setDrawIcons(false)
                setDrawValues(true)
                isHighlightEnabled = true
                highLightColor = Color.TRANSPARENT
                setBarSpace(0.2f)
            }
            binding.chart.isDoubleTapToZoomEnabled = false
            binding.chart.isScaleYEnabled = false
            binding.chart.data = PressureData(dataSet)
            binding.chart.invalidate()
            binding.chart.setVisibleXRange(0f, 6f)
            binding.chart.moveViewToX(chartData.lastOrNull()?.x ?: 0f)
        }
    }

}