package com.example.bch_ojt.bch;

import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by BCH_OJT on 7/4/2016.
 */
public class PreferenceFragment extends Fragment {

    public static final PreferenceFragment newInstance(String fullName){

        PreferenceFragment f = new PreferenceFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("fullName", fullName);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.preferencefragmentlayout, container, false);

        TextView positionTV = (TextView)v.findViewById(R.id.positionTV);
        TextView employmentTypeTV = (TextView)v.findViewById(R.id.employmentTypeTV);
        TextView locationTV = (TextView)v.findViewById(R.id.locationTV);
        TextView industryTV = (TextView)v.findViewById(R.id.industryTV);
        TextView availabilityTV = (TextView)v.findViewById(R.id.availabilityTV);
        TextView salaryTV = (TextView)v.findViewById(R.id.salaryTV);

        positionTV.setText("CEO");
        employmentTypeTV.setText("Primary: Permanent\nSecondary: Project-Based");
        locationTV.setText("Manila");
        industryTV.setText("Primary: Law/Legal\nSecondary: Journalism");
        availabilityTV.setText("Available immediately");
        salaryTV.setText("71,000 - 80,000");

        return v;

    }

}