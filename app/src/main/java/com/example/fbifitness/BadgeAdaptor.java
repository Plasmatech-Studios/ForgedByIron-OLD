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

public class BadgeAdaptor extends RecyclerView.Adapter<BadgeAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Badge> badges;

    public BadgeAdaptor(Context context, ArrayList<Badge> badges) {
        this.context = context;
        this.badges = badges;
    }


    @NonNull
    @Override
    public BadgeAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.badge_entry, parent, false);
        return new BadgeAdaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeAdaptor.MyViewHolder holder, int position) {
        Badge badge = badges.get(position);
        holder.badgeTitle.setText(badge.getBadgeName());
        holder.badgeDesc.setText(badge.getBadgeDescription());
        //holder.badgeCode.setText(badge.getBadgeCode());
        //holder.badgeStatusText.setText(badge.isUnlocked() ? "Unlocked" : "Locked");

    }

    @Override
    public int getItemCount() {
        return badges.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView badgeTitle, badgeDesc;// badgeCode; // badgeStatusText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeTitle = itemView.findViewById(R.id.badgeTitleText);
            badgeDesc = itemView.findViewById(R.id.taskDescText);
            //badgeCode = itemView.findViewById(R.id.BadgeIconText);
            //badgeStatusText = itemView.findViewById(R.id.statusText);

        }
    }
}
