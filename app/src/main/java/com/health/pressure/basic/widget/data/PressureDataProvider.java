package com.health.pressure.basic.widget.data;

import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;

public interface PressureDataProvider extends BarLineScatterCandleBubbleDataProvider {
    PressureData getPressureData();
}
