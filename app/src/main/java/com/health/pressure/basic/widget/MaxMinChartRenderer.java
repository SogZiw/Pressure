
package com.health.pressure.basic.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.health.pressure.basic.widget.data.IPressureDataSet;
import com.health.pressure.basic.widget.data.PressureData;
import com.health.pressure.basic.widget.data.PressureDataProvider;
import com.health.pressure.basic.widget.data.PressureEntry;
import com.health.pressure.basic.widget.data.PressureTransformer;

import java.util.List;

public class MaxMinChartRenderer extends LineScatterCandleRadarRenderer {

    protected PressureDataProvider mChart;

    private final float[] mBodyBuffers = new float[4];

    public MaxMinChartRenderer(PressureDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        mChart = chart;
    }

    @Override
    public void initBuffers() {

    }

    @Override
    public void drawData(Canvas c) {
        PressureData pressureData = mChart.getPressureData();
        for (IPressureDataSet set : pressureData.getDataSets()) {
            if (set.isVisible()) drawDataSet(c, set);
        }
    }

    protected void drawDataSet(Canvas c, IPressureDataSet dataSet) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = mAnimator.getPhaseY();
        float barSpace = dataSet.getBarSpace();
        mXBounds.set(mChart, dataSet);
        mRenderPaint.setStrokeWidth(3f);
        // draw the body
        for (int j = mXBounds.min; j <= mXBounds.range + mXBounds.min; j++) {
            // get the entry
            PressureEntry e = dataSet.getEntryForIndex(j);
            if (e == null) continue;
            final float xPos = e.getX();
            final float high = e.getHigh();
            final float low = e.getLow();
            // calculate the body
            mBodyBuffers[0] = xPos - 0.5f + barSpace;
            mBodyBuffers[1] = high * phaseY;
            mBodyBuffers[2] = (xPos + 0.5f - barSpace);
            mBodyBuffers[3] = low * phaseY;

            trans.pointValuesToPixel(mBodyBuffers);
            mRenderPaint.setColor(null == e.getPressureData() ? Color.BLACK : e.getPressureData().getState().getStateColor());
            mRenderPaint.setStyle(Paint.Style.FILL);

            float ra = Utils.convertDpToPixel(5f);
            c.drawRoundRect(
                    mBodyBuffers[0], mBodyBuffers[1],
                    mBodyBuffers[2], mBodyBuffers[3],
                    ra, ra,
                    mRenderPaint);
        }
    }

    @Override
    public void drawValue(Canvas c, String valueText, float x, float y, int color) {
        mValuePaint.setColor(color);
        c.drawText(valueText, x, y, mValuePaint);
    }

    @Override
    public void drawValues(Canvas c) {
        // if values are drawn
        if (isDrawingValuesAllowed(mChart)) {
            List<IPressureDataSet> dataSets = mChart.getPressureData().getDataSets();
            for (int i = 0; i < dataSets.size(); i++) {
                IPressureDataSet dataSet = dataSets.get(i);
                if (!shouldDrawValues(dataSet) || dataSet.getEntryCount() < 1) continue;
                // apply the text-styling defined by the DataSet
                applyValueTextStyle(dataSet);
                mXBounds.set(mChart, dataSet);
                float[] positions = new PressureTransformer(mViewPortHandler).generateTransformedValuesPressure(
                        dataSet, mAnimator.getPhaseX(), mAnimator.getPhaseY(), mXBounds.min, mXBounds.max);
                float yOffset = Utils.convertDpToPixel(5f);

                for (int j = 0; j < positions.length; j += 2) {
                    float x = positions[j];
                    float y = positions[j + 1];
                    if (!mViewPortHandler.isInBoundsRight(x)) break;
                    if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y)) continue;
                    PressureEntry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);
                }
            }
        }
    }

    @Override
    public void drawExtras(Canvas c) {
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        PressureData pressureData = mChart.getPressureData();
        for (Highlight high : indices) {
            IPressureDataSet dataSet = pressureData.getDataSetByIndex(high.getDataSetIndex());
            if (dataSet == null || !dataSet.isHighlightEnabled()) continue;
            PressureEntry e = dataSet.getEntryForXValue(high.getX(), high.getY());
            if (!isInBoundsX(e, dataSet)) continue;
            float lowValue = e.getLow() * mAnimator.getPhaseY();
            float highValue = e.getHigh() * mAnimator.getPhaseY();

            float yOffset = Utils.convertDpToPixel(5f);
            MPPointD pixHigh = mChart.getTransformer(dataSet.getAxisDependency()).getPixelForValues(e.getX(), highValue);
            high.setDraw((float) pixHigh.x, (float) pixHigh.y);
            drawValue(c,
                    String.valueOf(e.getHigh()),
                    (float) pixHigh.x,
                    (float) pixHigh.y - yOffset,
                    Color.BLACK);

            MPPointD pixLow = mChart.getTransformer(dataSet.getAxisDependency()).getPixelForValues(e.getX(), lowValue);
            drawValue(c,
                    String.valueOf(e.getLow()),
                    (float) pixLow.x,
                    (float) pixLow.y + 2 * yOffset,
                    Color.BLACK);
        }
    }
}
