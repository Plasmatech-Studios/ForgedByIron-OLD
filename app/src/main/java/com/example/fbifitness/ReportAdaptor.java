package com.example.fbifitness;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportAdaptor extends RecyclerView.Adapter<ReportAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Report> reports;

    public ReportAdaptor(Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
    }


    @NonNull
    @Override
    public ReportAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_entry, parent, false);
        return new ReportAdaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdaptor.MyViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.reportTitle.setText(report.getReportTitle());
        holder.pieChart = loadPieChartData(holder.pieChart, report);

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    private PieChart setupPieChart(PieChart chart, String centreText) {
        chart.setDrawHoleEnabled(true);

        //chart.setUsePercentValues(true);
        chart.setHoleColor(Color.BLACK);
        chart.setTransparentCircleAlpha(0);

        chart.setEntryLabelTextSize(0f);
        //Don't show the label

        chart.getDescription().setEnabled(true);
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
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(14f);
        legend.setDrawInside(false);
        legend.setEnabled(true);
        return chart;
    }

    private PieChart loadPieChartData(PieChart pieChart, Report report) {
        setupPieChart(pieChart, report.getTotalWeight() + "kg");
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < report.getExerciseCount(); i++) {
            try {
                float weight = Float.valueOf(String.valueOf((report.getExerciseWeightTotal(i))));
                if (weight == 0) {
                    continue;
                }
                String exerciseName = report.getExerciseName(i);
                entries.add(new PieEntry(weight, exerciseName));
            } catch (Exception e) {
                Log.e("ReportAdaptor", "ERROR while adding entry in Report Adaptor -> loadPieChartData: " + e.getMessage());
            }
        }
        if (entries.size() == 0) {
            entries.add(new PieEntry(1, "No Data"));
        }


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

        data.setValueTextSize(16f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        return pieChart;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        PieChart pieChart;
        TextView reportTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pieChart = itemView.findViewById(R.id.pieChart);
            reportTitle = itemView.findViewById(R.id.reportTitleText);

        }
    }
}
