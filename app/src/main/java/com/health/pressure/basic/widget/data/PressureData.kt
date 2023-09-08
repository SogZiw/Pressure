package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData

class PressureData : BarLineScatterCandleBubbleData<IPressureDataSet?> {
    constructor() : super()
    constructor(dataSets: List<IPressureDataSet?>?) : super(dataSets)
    constructor(vararg dataSets: IPressureDataSet?) : super(*dataSets)
}