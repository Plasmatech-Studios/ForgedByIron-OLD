package com.example.fbifitness;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.se.omapi.Session;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class LoginFragment extends Fragment {
    EditText usernameTextBox;
    EditText passwordTextBox;
    Button loginButton;
    TextView logoutText;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_alt, container, false);
        usernameTextBox = view.findViewById(R.id.usernameTextBox);
        passwordTextBox = view.findViewById(R.id.passwordTextBox);
        loginButton = view.findViewById(R.id.loginButton);

        usernameTextBox.setOnKeyListener((viewU, i, keyEvent) -> {
            if (usernameTextBox.getText().toString().length() > 0 && passwordTextBox.getText().toString().length() > 0) {
                loginButton.setEnabled(true);
            } else {
                loginButton.setEnabled(false);
            }
            return false;
        });

        passwordTextBox.setOnKeyListener((viewP, i, keyEvent) -> {
            if (usernameTextBox.getText().toString().length() > 0 && passwordTextBox.getText().toString().length() > 0) {
                loginButton.setEnabled(true);
            } else {
                loginButton.setEnabled(false);
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            attemptLogin();
        });

        logoutText = MainActivity.mainActivity.findViewById(R.id.toolbar_logout);
        logoutText.setVisibility(View.GONE);

        //Hide to toolbar
        MainActivity.mainActivity.toolbar.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }

    private void attemptLogin() {
        if (usernameTextBox.getText().toString().length() > 0 && passwordTextBox.getText().toString().length() > 0) {
            String username = usernameTextBox.getText().toString();
            String password = passwordTextBox.getText().toString();
            Log.d("LoginFragment", "Username: " + username + " Password: " + password);
            if (SessionController.getInstance().processLoginRequest(username, password)) {
                Log.d("LoginFragment", "Login successful");
                if (SessionController.isNewUser) {
                    MainActivity.mainActivity.replaceFragment(new BadgeFragment());
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.badges);
                    SessionController.isNewUser = false;
                    logoutText.setVisibility(View.VISIBLE);
                    //MainActivity.mainActivity.toolbar.setVisibility(View.VISIBLE);
                } else {
                    MainActivity.mainActivity.replaceFragment(new ProfileFragment());
                    logoutText.setVisibility(View.VISIBLE);
                    //MainActivity.mainActivity.toolbar.setVisibility(View.VISIBLE);
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.profile);



                }
            } else {
                Log.d("LoginFragment", "Login failed");
                passwordTextBox.setText("");
                passwordTextBox.setHint("Incorrect password");
                passwordTextBox.setHintTextColor(Color.RED);
                loginButton.setEnabled(false);
            }
        } else {
            loginButton.setEnabled(false);
            Log.e("LoginFragment", "Username or password is empty");
        }
    }
}