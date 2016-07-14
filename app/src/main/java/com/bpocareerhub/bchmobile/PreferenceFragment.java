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
public class PreferenceFragment extends DetailsFragment {
    View v;
    SessionManager session;
    boolean isUpdating;
    public static final PreferenceFragment newInstance(String fullName){

        PreferenceFragment f = new PreferenceFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("fullName", fullName);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new SessionManager(getContext());
        isUpdating = session.getUpdateMode();



        if(!isUpdating) {
            v = inflater.inflate(R.layout.preferencefragmentlayout, container, false);

            TextView positionTV = (TextView)v.findViewById(R.id.positionTV);
            TextView employmentTypeTV = (TextView)v.findViewById(R.id.employmentTypeTV);
            TextView locationTV = (TextView)v.findViewById(R.id.locationTV);
            TextView industryTV = (TextView)v.findViewById(R.id.industryTV);
            TextView availabilityTV = (TextView)v.findViewById(R.id.availabilityTV);
            TextView salaryTV = (TextView)v.findViewById(R.id.salaryTV);

            notIndicated(positionTV);
            notIndicated(employmentTypeTV);
            notIndicated(locationTV);
            notIndicated(industryTV);
            notIndicated(availabilityTV);
            notIndicated(salaryTV);

            return v;
        }
        else{
            v = inflater.inflate(R.layout.updatepreference, container, false);

            return v;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ProfileActivity)getActivity()).updateActiveFragment(1);
        }
    }
/*
    private void setTextView(TextView tv, String content){
        if(content.equals("null") || content.isEmpty()){
            notIndicated(tv);
        }
        else{
            tv.setText(content);
        }
    }

    private void notIndicated(TextView tv){
        tv.setText("[Not Indicated]");
    }
*/
}