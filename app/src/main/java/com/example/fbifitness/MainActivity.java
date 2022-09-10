package com.example.fbifitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.fbifitness.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public Fragment communityFragment = new CommunityFragment();
    public Fragment reportsFragment = new ReportsFragment();
    public Fragment newWorkoutFragment = new NewWorkoutFragment();
    public Fragment badgeFragment = new BadgeFragment();
    public Fragment profileFragment = new ProfileFragment();
    //public Fragment loginFragment = new LoginFragment();
    public Toolbar toolbar;

    static DataManager dbManager;
    static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DataManager(this);
        try {
            dbManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setup default page
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        replaceFragment(profileFragment); // Replace this with login screen later
        toolbar.setTitle("Profile");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_qr_code_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hi", "Dad");
            }
        });



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.community:
                    toolbar.setTitle("Community");
                    replaceFragment(communityFragment);
                    break;
                case R.id.reports:
                    toolbar.setTitle("Reports");
                    replaceFragment(reportsFragment);
                    break;
                case R.id.newWorkout:
                    toolbar.setTitle("Workout");
                    replaceFragment(newWorkoutFragment);
                    break;
                case R.id.badges:
                    toolbar.setTitle("Badges");
                    replaceFragment(badgeFragment);
                    break;
                case R.id.profile:
                    toolbar.setTitle("Profile");
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

    private String getFragmentName() {
        // log to console
        Log.d("Fragment Name", getSupportFragmentManager().findFragmentById(R.id.frame_layout).getClass().getSimpleName());
        return getSupportFragmentManager().findFragmentById(R.id.frame_layout).getClass().getSimpleName();
    }

    private void setToolbarTitle() {
        String fName = getFragmentName().toLowerCase(Locale.ROOT);
        switch(fName) {
            case "communityfragment":
                toolbar.setTitle("Community");
                break;
            case "reportsfragment":
                toolbar.setTitle("Reports");
                break;
            case "newworkoutfragment":
                toolbar.setTitle("Workout");
                break;
            case "badgefragment":
                toolbar.setTitle("Badges");
                break;
            case "profilefragment":
                toolbar.setTitle("Profile");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle(); // Set toolbar title to current fragment, won't work when switching fragments as this is executed on another thread
    }
}