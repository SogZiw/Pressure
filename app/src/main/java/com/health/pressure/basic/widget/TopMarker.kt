package com.health.pressure.basic.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.health.pressure.R

class TopMarker(context: Context) : MarkerView(context, R.layout.item_marker_view) {

    private val textView by lazy { findViewById<AppCompatTextView>(R.id.itemMarker) }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight) {
        textView.text = "This is test"
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -2 * height - Utils.convertDpToPixel(5F))
    }
}