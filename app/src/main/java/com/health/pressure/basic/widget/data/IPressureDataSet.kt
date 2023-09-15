package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet

interface IPressureDataSet : ILineScatterCandleRadarDataSet<PressureEntry?> {
    val barSpace: Float
}