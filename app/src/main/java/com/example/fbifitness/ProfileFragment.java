package com.example.fbifitness;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button insertButton;
    private Button fetchButton;
    private String mParam2;

    private EditText etUserID;
    private EditText etUsername;
    private EditText etSecret;

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
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
//        insertButton = (Button) view.findViewById(R.id.btnInsert);
//        fetchButton = (Button) view.findViewById(R.id.btnFetch);
//        etUserID = (EditText) view.findViewById(R.id.eTUniqueID);
//        etUsername = (EditText) view.findViewById(R.id.eTName);
//        etSecret = (EditText) view.findViewById(R.id.eTSecret);
//
//        insertButton.setOnClickListener(this);
//        fetchButton.setOnClickListener(this);


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