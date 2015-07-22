package es.jpv.android.examples.mpandroidchartexamples;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        BarChart barChart = (BarChart) view.findViewById(R.id.barChart);
        attachBarChartData(barChart);
        configureBarChartAppearance(barChart, "€");
        //Disable any touch interactions with the chart
        barChart.setTouchEnabled(false);
        barChart.invalidate();
        return view;
    }

    private void attachBarChartData(BarChart barChart) {
        //Build X-axis strings
        List<String> xVals = new ArrayList<String>();
        for (int i=0; i<8; i++) {
            xVals.add("Bar " + i);
        }
        //Build Bar entries
        List<BarEntry> barEntryList = new ArrayList<BarEntry>();
        for (int i=0; i<8; i++) {
            barEntryList.add(new BarEntry(10.5f + 10*i, i));
        }
        //Create a couple of datasets (one bar per dataset and X-axis entry)
        BarDataSet barDataSet1 = new BarDataSet(barEntryList, "Dataentry 1");
        BarDataSet barDataSet2 = new BarDataSet(barEntryList, "Dataentry 2");
        //Create BarData object and associate both datasets. Then set that in the BarChart
        BarData barData = new BarData(xVals);
        barData.addDataSet(barDataSet1);
        barData.addDataSet(barDataSet2);
        barChart.setData(barData);
    }

    private void configureBarChartAppearance(BarChart barChart, final String currencySymbol) {
        ValueFormatter valueFormatterAxis = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value)) + " " + currencySymbol;
            }
        };
        ValueFormatter valueFormatterDataset = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("###,###,##0.00");
                return mFormat.format(value) + " " + currencySymbol;
            }
        };

        LimitLine ll = new LimitLine(60f, "Budged exceeded");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(2f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(6f);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setValueFormatter(valueFormatterAxis);
        yAxisLeft.setDrawAxisLine(true);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.addLimitLine(ll);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        for (BarDataSet barDataSet : barChart.getData().getDataSets()) {
            barDataSet.setValueFormatter(valueFormatterDataset);
        }
    }
}
