package com.bpocareerhub.bchmobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by BCH_OJT on 7/4/2016.
 */
public class ExperienceFragment extends Fragment {

    public static final ExperienceFragment newInstance(String experience){

        ExperienceFragment f = new ExperienceFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("experience", experience);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String experience = getArguments().getString("experience");


        View v = inflater.inflate(R.layout.experiencefragmentlayout, container, false);

        TextView experienceTV = (TextView)v.findViewById(R.id.experienceTV);

        experienceTV.setText(experience);

        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ProfileActivity)getActivity()).updateActiveFragment(3);
        }
        else {
        }
    }

}