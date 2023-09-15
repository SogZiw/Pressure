package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.formatter.ValueFormatter
import com.health.pressure.datas
import com.health.pressure.ext.formatTime

class DateXFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return datas.getOrNull(value.toInt())?.record_time?.formatTime("MM/dd") ?: ""
    }
}