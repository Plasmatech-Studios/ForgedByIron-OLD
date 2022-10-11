package com.example.fbifitness;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {
    PieChart pieChart;


    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        pieChart = view.findViewById(R.id.pieChartTest);
        loadPieChartData(pieChart);
        // Inflate the layout for this fragment
        return view;
    }

    private PieChart setupPieChart(PieChart chart, String centreText) {
        chart.setDrawHoleEnabled(true);

        chart.setUsePercentValues(true);
        chart.setHoleColor(Color.BLACK);
        chart.setTransparentCircleAlpha(0);

        chart.setEntryLabelTextSize(14f);
        chart.setEntryLabelColor(Color.BLACK);

        chart.setCenterText(centreText); // Centre Text
        chart.setCenterTextSize(20f);
        chart.setCenterTextColor(Color.WHITE);

        chart.getDescription().setEnabled(false);
        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(14f);
        legend.setDrawInside(false);
        legend.setEnabled(true);
        return chart;
    }

    private PieChart loadPieChartData(PieChart pieChart) {
        setupPieChart(pieChart, "Title of Pie Chart");
        ArrayList<PieEntry> entries = new ArrayList<>();


        entries.add(new PieEntry(18.5f, "Bench Press"));
        entries.add(new PieEntry(24.9f, "Squat"));
        entries.add(new PieEntry(30f, "Deadlift"));
        entries.add(new PieEntry(34.5f, "Overhead Press"));
        entries.add(new PieEntry(40f, "Barbell Row"));


        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }

        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);

        PieData data = new PieData(set);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        return pieChart;

    }
}