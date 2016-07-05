package com.example.bch_ojt.bch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by BCH_OJT on 7/4/2016.
 */
public class EducationFragment extends Fragment {

    public static final EducationFragment newInstance(String education){

        EducationFragment f = new EducationFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("education", education);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String education = getArguments().getString("education");


        View v = inflater.inflate(R.layout.educationfragmentlayout, container, false);

        TextView educationTV = (TextView)v.findViewById(R.id.educationTV);

        educationTV.setText(education);

        return v;

    }

}