package com.health.pressure.basic.widget.data

import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.ViewPortHandler

class PressureTransformer(viewPortHandler: ViewPortHandler) : Transformer(viewPortHandler) {

    private var valuePointsForGenerateTransformedValuesPressure = FloatArray(1)

    fun generateTransformedValuesPressure(data: IPressureDataSet, phaseX: Float, phaseY: Float, from: Int, to: Int): FloatArray {
        val count = ((to - from) * phaseX + 1).toInt() * 2
        if (valuePointsForGenerateTransformedValuesPressure.size != count) {
            valuePointsForGenerateTransformedValuesPressure = FloatArray(count)
        }
        val valuePoints = valuePointsForGenerateTransformedValuesPressure
        var j = 0
        while (j < count) {
            val e = data.getEntryForIndex(j / 2 + from)
            if (e != null) {
                valuePoints[j] = e.x
                valuePoints[j + 1] = e.high * phaseY
            } else {
                valuePoints[j] = 0f
                valuePoints[j + 1] = 0f
            }
            j += 2
        }
        valueToPixelMatrix.mapPoints(valuePoints)
        return valuePoints
    }
}