package com.example.fbifitness;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView tvDisplayName;
    TextView tvWorkoutCount;
    TextView tvFollowers;
    TextView tvBadges;
    TextView tvBio;

    TextView tvQuickStat1;
    TextView tvQuickStat2;
    TextView tvQuickStat3;
    TextView tvQuickStat4;
    TextView tvQuickStat5;
    TextView tvQuickStat6;

    ImageView profileImage;

    TextView logOutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvDisplayName = view.findViewById(R.id.usernameTextProfile);
        tvDisplayName.setText(MainActivity.sessionController.currentUser.getDisplayName());

        tvWorkoutCount = view.findViewById(R.id.workoutTotalTextViewProfile);
        tvWorkoutCount.setText(String.valueOf(MainActivity.sessionController.getActiveUserWorkoutCount()));
        profileImage = view.findViewById(R.id.profileImage);
        if (MainActivity.sessionController.currentUser.getProfileImage() != null) {
            profileImage.setImageBitmap(MainActivity.sessionController.currentUser.getProfileImage());
        }

        ActivityResultLauncher myResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Create a bitmap from the URI
                Uri imageUri = result.getData().getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MainActivity.sessionController.currentUser.setProfileImage(bitmap);
                profileImage.setImageBitmap(bitmap);
            }
        });

        profileImage.setOnClickListener(v -> {
            // Select a new profile image from the gallery using registerForActivityResult
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            myResult.launch(intent);

        });

                tvFollowers = view.findViewById(R.id.followerTotalTextViewProfile);
        if (SessionController.currentUser != null) {
            int followerCount = SessionController.currentUser.getFollowersCount();
            tvFollowers.setText(String.valueOf(followerCount));
        } else {
            tvFollowers.setText("0");
        }

        tvBadges = view.findViewById(R.id.badgeTotalTextViewProfile);
        if (SessionController.currentUser != null) {
            int badgeCount = Badge.getBadgeUnlockCount();
            tvBadges.setText(String.valueOf(badgeCount));
        } else {
            tvBadges.setText("0");
        }

        tvBio = view.findViewById(R.id.bioTextProfile);
        if (SessionController.currentUser != null) {
            String bio = SessionController.currentUser.getBio();
            tvBio.setText(bio);
        } else {
            tvBio.setText("No bio");
        }


        tvQuickStat1 = view.findViewById(R.id.userQuickStat1);
        tvQuickStat2 = view.findViewById(R.id.userQuickStat2);
        tvQuickStat3 = view.findViewById(R.id.userQuickStat3);
        tvQuickStat4 = view.findViewById(R.id.userQuickStat4);
        tvQuickStat5 = view.findViewById(R.id.userQuickStat5);
        tvQuickStat6 = view.findViewById(R.id.userQuickStat6);

        if (SessionController.currentUser != null) { // TODO user customize these labels?
            tvQuickStat1.setText(String.valueOf(SessionController.currentUser.getUserWeight()));
            tvQuickStat2.setText(String.valueOf(SessionController.currentUser.getUserFat()));
            tvQuickStat3.setText(String.valueOf(SessionController.currentUser.getLongestRun()));
            tvQuickStat4.setText(String.valueOf(SessionController.currentUser.getBenchPB()));
            tvQuickStat5.setText(String.valueOf(SessionController.currentUser.getDeadliftPB()));
            tvQuickStat6.setText(String.valueOf(SessionController.currentUser.getSquatPB()));
        } else {
            tvQuickStat1.setText("0");
            tvQuickStat2.setText("0");
            tvQuickStat3.setText("0");
            tvQuickStat4.setText("0");
            tvQuickStat5.setText("0");
            tvQuickStat6.setText("0");
        }
        User user = SessionController.currentUser;

        // Weight box
        tvQuickStat1.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your weight in kg"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("80.5");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update weight", (dialog, which) -> {
                String inWeight = input.getText().toString();

                inWeight = numberParser(inWeight, "kg", 999, 1, tvQuickStat1.getText().toString());
                user.setUserWeight(inWeight);
                tvQuickStat1.setText(inWeight);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvQuickStat2.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your body fat percentage"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("20.5");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update body fat", (dialog, which) -> {
                String inBodyFat = input.getText().toString();
                inBodyFat = numberParser(inBodyFat, "%", 100, 1, tvQuickStat2.getText().toString());
                user.setUserFat(inBodyFat);
                tvQuickStat2.setText(inBodyFat);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvQuickStat3.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your longest run in km"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("20.5");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update longest run", (dialog, which) -> {
                String inLongestRun = input.getText().toString();
                inLongestRun = numberParser(inLongestRun, "km", 250, 1, tvQuickStat3.getText().toString());
                user.setLongestRun(inLongestRun);
                tvQuickStat3.setText(inLongestRun);
                tvBadges.setText(String.valueOf(Badge.getBadgeUnlockCount()));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvQuickStat4.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your bench press PB in kg"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("100");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update bench press PB", (dialog, which) -> {
                String inBenchPB = input.getText().toString();
                inBenchPB = numberParser(inBenchPB, "kg", 999, 0, tvQuickStat4.getText().toString());
                user.setBenchPB(inBenchPB);
                tvQuickStat4.setText(inBenchPB);
                tvBadges.setText(String.valueOf(Badge.getBadgeUnlockCount()));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvQuickStat5.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your deadlift PB in kg"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("100");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update deadlift PB", (dialog, which) -> {
                String inDeadliftPB = input.getText().toString();
                inDeadliftPB = numberParser(inDeadliftPB, "kg", 999, 0, tvQuickStat5.getText().toString());
                user.setDeadliftPB(inDeadliftPB);
                tvQuickStat5.setText(inDeadliftPB);
                tvBadges.setText(String.valueOf(Badge.getBadgeUnlockCount()));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvQuickStat6.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your squat PB in kg"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("100");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update squat PB", (dialog, which) -> {
                String inSquatPB = input.getText().toString();
                inSquatPB = numberParser(inSquatPB, "kg", 999, 0, tvQuickStat6.getText().toString());
                user.setSquatPB(inSquatPB);
                tvQuickStat6.setText(inSquatPB);
                tvBadges.setText(String.valueOf(Badge.getBadgeUnlockCount()));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvBio.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your bio"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("I'm a cool guy");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update bio", (dialog, which) -> {
                String inBio = input.getText().toString();
                if (inBio.length() == 0) {
                    inBio = "No bio";
                }
                if (inBio.length() > 250) {
                    inBio = inBio.substring(0, 250);
                    Toast.makeText(view.getContext(), "Bio too long, truncated to 250 characters.", Toast.LENGTH_SHORT).show();
                }
                user.setBio(inBio);
                tvBio.setText(inBio);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        tvDisplayName.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Enter your display name"); // TODO change prompt if custom

            // Set up the input
            final EditText input = new EditText(view.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Cool Guy");
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Update display name", (dialog, which) -> {
                String inDisplayName = input.getText().toString();
                // Limit display name to 20 characters
                if (inDisplayName.length() > 20) {
                    inDisplayName = inDisplayName.substring(0, 20);
                    Toast.makeText(view.getContext(), "Display name cannot exceed 20 characters.", Toast.LENGTH_LONG).show();
                }
                if (inDisplayName.length() == 0) {
                    inDisplayName = user.getUsername();
                    Toast.makeText(view.getContext(), "Display name set to username.", Toast.LENGTH_LONG).show();

                }
                user.setDisplayName(inDisplayName);
                tvDisplayName.setText(inDisplayName);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            builder.show();
        });

        logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(v -> {
            SessionController.getInstance().logout();
        });


        return  view;
    }

    @Override
    public void onClick(View v) {
//        switch ( (v.getId())) {
//            case R.id.btnInsert:
//                DataManager.insertUser(etUserID.getText().toString(), etUsername.getText().toString(), etSecret.getText().toString());
//                break;
//            case R.id.btnFetch:
//                DataManager.deleteUser(etUserID.getText().toString());
//                break;
//
//        }
    }

    private String numberParser(String input, String suffix, double maxVal, int decimalPlaces, String defVal) {
        if (input.equals("")) {
            String output = defVal;
            Log.e("numberParser", "Default value used: " + defVal);
            output = numberParser(output, suffix, maxVal, decimalPlaces, defVal);
            return output;
        }
        String output = input.replaceAll("[^\\d.]", "");
        // if greater than max, set to max

        if (Double.parseDouble(output) > maxVal) {
            output = String.valueOf(maxVal);
        }
        //limit to x decimal place
        output = String.format("%." + decimalPlaces + "f", Double.parseDouble(output));
        output += suffix;
        return output;
    }

//    public void btnInsert(View v) {
//        Log.d("SHIT", "BEFORE: ");
//
//        MainActivity.btnInsert(v);
//        Log.d("SHIT", "AFTER: ");
//
//
//    }

//    public void btnUpdate(View v) {
//        MainActivity.btnUpdate(v);
//    }
//
//    public void btnFetch(View v) {
//        MainActivity.btnFetch(v);
//    }
//
//    public void btnInsert(View view) {
//    }
}