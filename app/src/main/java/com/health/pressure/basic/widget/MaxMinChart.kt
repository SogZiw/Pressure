package com.health.pressure.basic.widget

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.utils.Utils
import com.health.pressure.R
import com.health.pressure.basic.widget.data.PressureData
import com.health.pressure.basic.widget.data.PressureDataProvider

class MaxMinChart : BarLineChartBase<PressureData?>, PressureDataProvider {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun init() {
        super.init()
        mRenderer = MaxMinChartRenderer(this, mAnimator, mViewPortHandler)
        val lineColor = ContextCompat.getColor(context, R.color.color_e8e8f0)
        xAxis.spaceMin = 0.5f
        xAxis.spaceMax = 0.5f
        setBackgroundColor(Color.TRANSPARENT)
        setNoDataText("")
        description.isEnabled = false
        isDragEnabled = true
        setTouchEnabled(true)
        setScaleEnabled(false)
        setDrawGridBackground(false)
        setDrawMarkers(true)
        legend.isEnabled = false
        // xAxis
        xAxis.textColor = -16777216
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.axisLineColor = lineColor
        xAxis.gridColor = lineColor
        // axisLeft
        axisLeft.textColor = ContextCompat.getColor(context, R.color.color_34405a)
        axisLeft.setLabelCount(5, true)
        axisLeft.setDrawGridLines(true)
        axisLeft.spaceBottom = 20f
        axisLeft.spaceTop = 30f
        axisLeft.zeroLineColor = lineColor
        axisLeft.axisLineColor = lineColor
        axisLeft.zeroLineWidth = Utils.convertDpToPixel(2F)
        axisLeft.setGridDashedLine(DashPathEffect(floatArrayOf(5F, 5F), 0F))
        axisRight.isEnabled = false
    }

    override fun getPressureData(): PressureData? {
        return mData
    }
}