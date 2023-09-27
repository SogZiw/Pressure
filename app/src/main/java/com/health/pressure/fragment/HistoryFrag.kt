package com.health.pressure.fragment

import android.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.YAxis
import com.health.pressure.R
import com.health.pressure.activity.HistoryActivity
import com.health.pressure.activity.RecordActivity
import com.health.pressure.basic.BaseFrag
import com.health.pressure.basic.widget.data.PressureData
import com.health.pressure.basic.widget.data.PressureDataSet
import com.health.pressure.basic.widget.data.PressureEntry
import com.health.pressure.dao.DataManager
import com.health.pressure.databinding.FragHistoryBinding
import com.health.pressure.datas
import com.health.pressure.ext.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class HistoryFrag : BaseFrag<FragHistoryBinding>() {

    private var defaultRangeId = R.id.range1
    override val layoutId: Int get() = R.layout.frag_history
    private val charData = DataManager.getAllPressures()

    override fun initView() {
        binding.timeRange.setOnClickListener {
            showBottomMenu { title, id ->
                defaultRangeId = id
                binding.timeRange.text = title
                initRangeData(id)
            }
        }
        binding.btnAdd.setOnClickListener {
            activity.goNextPage<RecordActivity>()
        }
        binding.btnGoHis.setOnClickListener {
            activity.goNextPage<HistoryActivity>()
        }
        refreshChart()
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        initRangeData()
    }

    private fun initRangeData(id: Int = defaultRangeId) {

        fun calculate(start: Long, end: Long) {
            lifecycleScope.launch(Dispatchers.IO) {
                DataManager.getPressures(start, end).let { datas ->
                    val sys = datas.map { it.sys }.average()
                    val dia = datas.map { it.dia }.average()
                    withContext(Dispatchers.Main) {
                        binding.sys.text = if (sys.isNaN()) "0" else "${sys.roundToInt()}"
                        binding.dia.text = if (dia.isNaN()) "0" else "${dia.roundToInt()}"
                    }
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

    private fun refreshChart() {
        charData.observe(this) { list ->
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
                setDrawValues(true)
                valueTextSize = 10f
                isHighlightEnabled = true
                valueTextColor = R.color.color_34405a.colorValue
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