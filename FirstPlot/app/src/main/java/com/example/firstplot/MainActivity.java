package com.example.firstplot;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Plot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10,
                13, 14};
        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16,
                64};
        Number[] series2Numbers = {5, 2, 10, 5, 20, 10, 40, 20,
                80, 40};

        // turn the above arrays into XYSeries’: // (Y_VALS_ONLY
        //means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // create formatters to use for drawing a series using LineAndPointFormatter
        LineAndPointFormatter series1Format
                = new LineAndPointFormatter(Color.RED, Color.GREEN,
                Color.BLUE, null);
        LineAndPointFormatter series2Format = new
                LineAndPointFormatter(Color.BLUE, Color.GREEN, null,
                null);

        // add a new series’ to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);

    }
}
