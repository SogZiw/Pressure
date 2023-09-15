
package com.health.pressure.basic.widget.data;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;

import java.util.ArrayList;
import java.util.List;

public class PressureDataSet extends LineScatterCandleRadarDataSet<PressureEntry> implements IPressureDataSet {

    private float mShadowWidth = 3f;
    private float mBarSpace = 0.1f;

    public PressureDataSet(List<PressureEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public DataSet<PressureEntry> copy() {
        List<PressureEntry> entries = new ArrayList<>();
        for (int i = 0; i < mValues.size(); i++) {
            entries.add(mValues.get(i).copy());
        }
        PressureDataSet copied = new PressureDataSet(entries, getLabel());
        copy(copied);
        return copied;
    }

    protected void copy(PressureDataSet pressureDataSet) {
        super.copy(pressureDataSet);
        pressureDataSet.mShadowWidth = mShadowWidth;
        pressureDataSet.mBarSpace = mBarSpace;
        pressureDataSet.mHighLightColor = mHighLightColor;
    }

    @Override
    protected void calcMinMax(PressureEntry e) {
        if (e.getLow() < mYMin)
            mYMin = e.getLow();

        if (e.getHigh() > mYMax)
            mYMax = e.getHigh();

        calcMinMaxX(e);
    }

    @Override
    protected void calcMinMaxY(PressureEntry e) {

        if (e.getHigh() < mYMin)
            mYMin = e.getHigh();

        if (e.getHigh() > mYMax)
            mYMax = e.getHigh();

        if (e.getLow() < mYMin)
            mYMin = e.getLow();

        if (e.getLow() > mYMax)
            mYMax = e.getLow();
    }

    /**
     * Sets the space that is left out on the left and right side of each
     * candle, default 0.1f (10%), max 0.45f, min 0f
     *
     * @param space
     */
    public void setBarSpace(float space) {

        if (space < 0f)
            space = 0f;
        if (space > 0.45f)
            space = 0.45f;

        mBarSpace = space;
    }

    @Override
    public float getBarSpace() {
        return mBarSpace;
    }

}
