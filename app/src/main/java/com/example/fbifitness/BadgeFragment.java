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
import android.widget.TextView;

import java.util.ArrayList;


public class BadgeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Badge> badges;
    TextView badgeUnlockedText;
    TextView badgeLockedText;

    public BadgeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_badge, container, false);
        recyclerView = view.findViewById(R.id.badgeViewList);

        // Create Reports
        badges = Badge.getUnlockedBadges();

        // Create Adaptor
        BadgeAdaptor badgeAdaptor = new BadgeAdaptor(getContext(), badges);

        // Set Adaptor
        recyclerView.setAdapter(badgeAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        badgeUnlockedText = view.findViewById(R.id.badgeUnlockedText);
        badgeLockedText = view.findViewById(R.id.badgeLockedText);

        badgeUnlockedText.setText(String.valueOf(Badge.getBadgeUnlockCount()));
        badgeLockedText.setText(String.valueOf(Badge.getBadgeList().size() - Badge.getBadgeUnlockCount()));

        return view;
    }
}