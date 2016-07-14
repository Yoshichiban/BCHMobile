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
public class SkillsFragment extends Fragment {
    View v;
    SessionManager session;
    boolean isUpdating;

    TextView skillsTV;
    TextView languagesTV;
    public static final SkillsFragment newInstance(String skills, String languages){

        SkillsFragment f = new SkillsFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("skills", skills);
        bdl.putString("languages", languages);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new SessionManager(getContext());
        isUpdating = session.getUpdateMode();

        String skills = getArguments().getString("skills");
        String languages = getArguments().getString("languages");

        if(!isUpdating) {
            v = inflater.inflate(R.layout.skillsfragmentlayout, container, false);

            TextView skillsTV = (TextView) v.findViewById(R.id.skillsTV);
            TextView languagesTV = (TextView) v.findViewById(R.id.languagesTV);

            //skillsTV.setText(skills);
            //languagesTV.setText(languages);
            skillsTV.setText("[Not Indicated]");
            languagesTV.setText("[Not Indicated]");
            return v;
        }
        else{
            v = inflater.inflate(R.layout.updateskills, container, false);

            return v;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ProfileActivity)getActivity()).updateActiveFragment(4);
        }
        else{

        }
    }

}