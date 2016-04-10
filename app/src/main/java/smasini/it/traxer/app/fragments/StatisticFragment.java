package smasini.it.traxer.app.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;
import smasini.it.traxer.viewmodels.StatisticViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment {

    private PieChart mChart;
    private StatisticViewModel svm;

    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        svm = DBOperation.getStatistic();

        TextView totalSerie = (TextView) rootView.findViewById(R.id.total_series);
        TextView totalEpisode = (TextView) rootView.findViewById(R.id.total_episodes);
        TextView totalDay = (TextView) rootView.findViewById(R.id.total_days);
        TextView totalHour = (TextView) rootView.findViewById(R.id.total_hours);
        TextView totalMinute = (TextView) rootView.findViewById(R.id.total_minutes);

        totalSerie.setText(String.format("%d", svm.getTotalSerie()));
        totalEpisode.setText(String.format("%d", svm.getTotalEpisode()));
        totalDay.setText(svm.getDays());
        totalHour.setText(svm.getHours());
        totalMinute.setText(svm.getMinutes());

        mChart = (PieChart) rootView.findViewById(R.id.chart_watch);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setTextColor(getResources().getColor(R.color.text_color));
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        setData();
        return rootView;
    }

    private void setData() {


        ArrayList<Entry> yVals1 = new ArrayList<>();

        yVals1.add(new Entry(svm.getUnwatch(), 0));
        yVals1.add(new Entry(svm.getWatch(), 1));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Unwatch");
        xVals.add("Watch");

        PieDataSet dataSet = new PieDataSet(yVals1, "Episode watched");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.deep_orange_500));
        colors.add(getResources().getColor(R.color.green_500));
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }
}
