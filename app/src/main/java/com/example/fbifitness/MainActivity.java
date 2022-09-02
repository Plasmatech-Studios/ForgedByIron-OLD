package com.example.fbifitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fbifitness.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public Fragment communityFragment = new CommunityFragment();
    public Fragment reportsFragment = new ReportsFragment();
    public Fragment newWorkoutFragment = new NewWorkoutFragment();
    public Fragment badgeFragment = new BadgeFragment();
    public Fragment profileFragment = new ProfileFragment();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Fragment communityFragment = new CommunityFragment();
//        Fragment reportsFragment = new ReportsFragment();
//        Fragment newWorkoutFragment = new NewWorkoutFragment();
//        Fragment badgeFragment = new BadgeFragment();
//        Fragment profileFragment = new ProfileFragment();

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        replaceFragment(profileFragment); // Replace this with login screen later

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.community:
                    replaceFragment(communityFragment);
                    break;
                case R.id.reports:
                    replaceFragment(reportsFragment);
                    break;
                case R.id.newWorkout:
                    replaceFragment(newWorkoutFragment);
                    break;
                case R.id.badges:
                    replaceFragment(badgeFragment);
                    break;
                case R.id.profile:
                    replaceFragment(profileFragment);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}