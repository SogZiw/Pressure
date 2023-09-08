package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.data.Entry
import kotlin.math.abs

class PressureEntry : Entry {

    private var mHigh = 0
    private var mLow = 0

    /**
     * @param x The value on the x-axis
     */
    constructor(x: Float, max: Int, min: Int) : super(x, (max + min) / 2f) {
        mHigh = max
        mLow = min
    }

    /**
     * Constructor.
     *
     * @param x    The value on the x-axis
     * @param data Spot for additional data this Entry represents
     */
    constructor(x: Float, max: Int, min: Int, data: Any?) : super(x, (max + min) / 2f, data) {
        mHigh = max
        mLow = min
    }

    val range: Float get() = abs(mHigh - mLow).toFloat()

    override fun copy(): PressureEntry {
        return PressureEntry(x, mHigh, mLow, data)
    }

    /**
     * Returns the upper shadows highest value.
     */
    val high: Float
        get() = mHigh.toFloat()

    fun setHigh(max: Int) {
        mHigh = max
    }

    /**
     * Returns the lower shadows lowest value.
     */
    val low: Float
        get() = mLow.toFloat()

    fun setLow(min: Int) {
        mLow = min
    }
}