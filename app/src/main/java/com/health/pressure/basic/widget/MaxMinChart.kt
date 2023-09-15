package com.health.pressure.basic.widget

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.util.AttributeSet
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.utils.Utils
import com.health.pressure.R
import com.health.pressure.basic.widget.data.DateXFormatter
import com.health.pressure.basic.widget.data.PressureData
import com.health.pressure.basic.widget.data.PressureDataProvider
import com.health.pressure.ext.colorValue

class MaxMinChart : BarLineChartBase<PressureData?>, PressureDataProvider {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun init() {
        super.init()
        mRenderer = MaxMinChartRenderer(this, mAnimator, mViewPortHandler)
        val lineColor = R.color.color_e8e8f0.colorValue
        val textColor = R.color.color_34405a.colorValue
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
        isDoubleTapToZoomEnabled = false
        legend.isEnabled = false
        // xAxis
        xAxis.valueFormatter = DateXFormatter()
        xAxis.textColor = textColor
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.axisLineColor = lineColor
        xAxis.gridColor = lineColor
        // axisLeft
        axisLeft.textColor = textColor
        axisLeft.setLabelCount(5, true)
        axisLeft.setDrawGridLines(true)
        axisLeft.spaceBottom = 20f
        axisLeft.spaceTop = 35f
        axisLeft.zeroLineColor = lineColor
        axisLeft.axisLineColor = lineColor
        axisLeft.zeroLineWidth = Utils.convertDpToPixel(2F)
        axisLeft.setGridDashedLine(DashPathEffect(floatArrayOf(4F, 4F), 0F))
        axisRight.isEnabled = false
        // add marker
        marker = TopMarker(context).apply { chartView = this@MaxMinChart }
        resetTracking()
    }

    override fun getPressureData(): PressureData? {
        return mData
    }
}