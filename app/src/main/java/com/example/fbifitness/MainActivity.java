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

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //public static ArrayList<ExerciseListView> exerciseList;


    public Fragment communityFragment = new CommunityFragment();
    public Fragment reportsFragment = new ReportsFragment();
    public Fragment newWorkoutFragment = new NewWorkoutFragment();
    public Fragment badgeFragment = new BadgeFragment();
    public Fragment profileFragment = new ProfileFragment();
    public Fragment currentWorkoutFragment = new CurrentWorkoutFragment();
    //public Fragment loginFragment = new LoginFragment();
    public Toolbar toolbar;

    static DataManager dbManager;
    static ActivityMainBinding binding;
    static MainActivity mainActivity;
    static SessionController sessionController;
    static BottomNavigationView bottomNavigationView;


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
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_qr_code_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hi", "Dad");
            }
        });



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("Bottom Nav", "Selected");
            if (SessionController.currentUser == null) {
                Log.d("Bottom Nav", "No user");
                return false;
            }
            switch(item.getItemId()) {
                case R.id.community:
                    replaceFragment(communityFragment);
                    break;
                case R.id.reports:
                    replaceFragment(reportsFragment);
                    break;
                case R.id.newWorkout:
                    if (sessionController.currentUser != null) {
                        if (sessionController.currentUser.getActiveWorkout() == null) {
                            Log.e("MainActivity", "Error: No active workout");
                            replaceFragment(newWorkoutFragment);
                        } else {
                            Log.e("MainActivity", "Error: Active workout already exists");
                            replaceFragment(currentWorkoutFragment);
                        }
                    } else {
                        Log.e("MainActivity", "Error: No user logged in");
                        // TODO LOGIN
                    }

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

        mainActivity = this;

        sessionController = SessionController.getInstance();
        sessionController.startSession();

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        //Log the fragment name
        String fragmentName = getFragmentName(fragment);
        setToolbarTitle(fragmentName);

    }

    private String getFragmentName(Fragment fragment) {
        // log to console
        Log.d("Fragment Name", fragment.getClass().getSimpleName());
        return fragment.getClass().getSimpleName();
    }
//
    private void setToolbarTitle(String fragmentName) {
        switch(fragmentName) {
            case "CommunityFragment":
                toolbar.setTitle("Community");
                break;
            case "ReportsFragment":
                toolbar.setTitle("Reports");
                break;
            case "NewWorkoutFragment":
                toolbar.setTitle("Workout");
                break;
            case "BadgeFragment":
                toolbar.setTitle("Badges");
                break;
            case "ProfileFragment":
                toolbar.setTitle("Profile");
                break;
            case "CurrentWorkoutFragment":
                Workout workout = Workout.workouts.get(SessionController.currentUser.getActiveWorkout().toString());
                toolbar.setTitle(workout.getName());
                break;
            default:
                toolbar.setTitle("Fragment Title");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setToolbarTitle(); // Set toolbar title to current fragment, won't work when switching fragments as this is executed on another thread
    }
}