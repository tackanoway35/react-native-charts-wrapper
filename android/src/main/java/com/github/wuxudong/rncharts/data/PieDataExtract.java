package com.github.wuxudong.rncharts.data;

import android.graphics.Color;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.wuxudong.rncharts.utils.BridgeUtils;
import com.github.wuxudong.rncharts.utils.ChartDataSetConfigUtils;
import com.github.wuxudong.rncharts.utils.ConversionUtil;

import java.util.ArrayList;

/**
 * Created by xudong on 02/03/2017.
 */

public class PieDataExtract extends DataExtract<PieData, PieEntry> {
    @Override
    PieData createData() {
        return new PieData();
    }

    @Override
    IDataSet<PieEntry> createDataSet(ArrayList<PieEntry> entries, String label) {
        return new PieDataSet(entries, label);
    }

    @Override
    void dataSetConfig(IDataSet<PieEntry> dataSet, ReadableMap config) {
        PieDataSet pieDataSet = (PieDataSet) dataSet;
        ChartDataSetConfigUtils.commonConfig(pieDataSet, config);

        if (BridgeUtils.validate(config, ReadableType.Boolean, "xValuePosition")) {
            Boolean isDrawOutSide = config.getBoolean("xValuePosition");
            if (isDrawOutSide == true) {
                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            } else {
                pieDataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
            }
        }
        if (BridgeUtils.validate(config, ReadableType.String, "valueLineColor")) {
            String hex = config.getString("valueLineColor");
            pieDataSet.setValueLineColor(Color.parseColor(hex));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "valueLineWidth")) {
            pieDataSet.setValueLineWidth((float) config.getDouble("valueLineWidth"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "valueLinePart1Length")) {
            pieDataSet.setValueLinePart1Length((float) config.getDouble("valueLinePart1Length"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "valueLinePart2Length")) {
            pieDataSet.setValueLinePart2Length((float) config.getDouble("valueLinePart2Length"));
        }
        // PieDataSet only config
        if (BridgeUtils.validate(config, ReadableType.Number, "sliceSpace")) {
            pieDataSet.setSliceSpace((float) config.getDouble("sliceSpace"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "selectionShift")) {
            pieDataSet.setSelectionShift((float) config.getDouble("selectionShift"));
        }
    }

    @Override
    PieEntry createEntry(ReadableArray values, int index) {
        PieEntry entry;

        if (ReadableType.Map.equals(values.getType(index))) {
            ReadableMap map = values.getMap(index);

            float value = (float) map.getDouble("value");
            if (BridgeUtils.validate(map, ReadableType.String, "label")) {
                entry = new PieEntry(value, map.getString("label"), ConversionUtil.toMap(map));
            } else {
                entry = new PieEntry(value, ConversionUtil.toMap(map));
            }
        } else if (ReadableType.Number.equals(values.getType(index))) {
            entry = new PieEntry((float) values.getDouble(index));
        } else {
            throw new IllegalArgumentException("Unexpected entry type: " + values.getType(index));
        }

        return entry;
    }

}
