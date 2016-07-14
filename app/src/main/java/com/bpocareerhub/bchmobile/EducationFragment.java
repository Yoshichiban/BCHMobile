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
public class EducationFragment extends DetailsFragment {
    View v;
    SessionManager session;
    boolean isUpdating;
    public static final EducationFragment newInstance(String education){

        EducationFragment f = new EducationFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("education", education);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new SessionManager(getContext());
        isUpdating = session.getUpdateMode();

        String education = getArguments().getString("education");

        if(!isUpdating) {
            v = inflater.inflate(R.layout.educationfragmentlayout, container, false);

            TextView educationTV = (TextView) v.findViewById(R.id.educationTV);

            //educationTV.setText(education);
            educationTV.setText("[Not Indicated]");

            return v;
        }
        else{
            v = inflater.inflate(R.layout.updateeducation, container, false);

            return v;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ProfileActivity)getActivity()).updateActiveFragment(3);
        }
    }

}