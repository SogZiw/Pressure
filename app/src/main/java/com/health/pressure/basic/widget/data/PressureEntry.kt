package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.data.Entry
import com.health.pressure.dao.Pressure

class PressureEntry(x: Float, max: Int, min: Int, val pressure: Pressure) : Entry(x, (max + min) / 2f) {

    private var mHigh = max
    private var mLow = min

    override fun copy(): PressureEntry {
        return PressureEntry(x, mHigh, mLow, pressure)
    }

    val high: Int
        get() = mHigh

    val low: Int
        get() = mLow
}