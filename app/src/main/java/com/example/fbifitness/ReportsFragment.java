package com.example.fbifitness;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * Use the {@link ReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Report> reports;

    public ReportsFragment() {
        // Required empty public constructor
    }

    public static ReportsFragment newInstance(String param1, String param2) {
        ReportsFragment fragment = new ReportsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = view.findViewById(R.id.reportsRList);

        // Create Reports
        reports = new ArrayList<>();
        ArrayList<String> workoutIDs = new ArrayList<>();
        workoutIDs = DataManager.getAllWorkoutIDByUser(SessionController.currentUser.toString());
        for (int i = workoutIDs.size() - 1; i >= 0; i--) {
            reports.add(new Report(workoutIDs.get(i)));
        }
//        for (int i = 0; i < workoutIDs.size(); i++) {
//            Report report = new Report(workoutIDs.get(i));
//            reports.add(report);
//        }

        // Create Adaptor
        ReportAdaptor reportAdaptor = new ReportAdaptor(getContext(), reports);

        // Set Adaptor
        recyclerView.setAdapter(reportAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //notify the adaptor that the data has changed

        //reportAdaptor.notifyDataSetChanged();
        //pieChart = view.findViewById(R.id.pieChart);
        //loadPieChartData();

        // Inflate the layout for this fragment
        return view;
    }


}