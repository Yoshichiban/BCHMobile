package com.bpocareerhub.bchmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by BCH_OJT on 7/4/2016.
 */
public class DetailsFragment extends Fragment {
    View v;
    SessionManager session;
    boolean isUpdating;

    public static DetailsFragment newInstance(String fullName, String gender, String status,
                                                    String birthdate, String email, String telNo,
                                                    String cellNo, String address){
        DetailsFragment f = new DetailsFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString("fullName", fullName);
        bdl.putString("gender", gender);
        bdl.putString("status", status);
        bdl.putString("birthdate", birthdate);
        bdl.putString("email", email);
        bdl.putString("telNo", telNo);
        bdl.putString("cellNo", cellNo);
        bdl.putString("address", address);

        f.setArguments(bdl);

        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        session = new SessionManager(getContext());
        isUpdating = session.getUpdateMode();

        String fullName = getArguments().getString("fullName");
        String gender = getArguments().getString("gender");
        String status = getArguments().getString("status");
        String birthdate = getArguments().getString("birthdate");
        String email = getArguments().getString("email");
        String telNo = getArguments().getString("telNo");
        String cellNo = getArguments().getString("cellNo");
        String address = getArguments().getString("address");

        if(!isUpdating) {
            v = inflater.inflate(R.layout.personalfragmentlayout, container, false);

            TextView fullNameTV = (TextView) v.findViewById(R.id.fullNameTV);
            TextView genderTV = (TextView) v.findViewById(R.id.genderTV);
            TextView statusTV = (TextView) v.findViewById(R.id.statusTV);
            TextView birthdateTV = (TextView) v.findViewById(R.id.birthdateTV);
            TextView emailTV = (TextView) v.findViewById(R.id.emailTV);
            TextView telNoTV = (TextView) v.findViewById(R.id.telNoTV);
            TextView cellNoTV = (TextView) v.findViewById(R.id.cellNoTV);
            TextView addressTV = (TextView) v.findViewById(R.id.addressTV);

            //fullNameTV.setText(fullName);
            //genderTV.setText(gender);
            //statusTV.setText(status);
            //birthdateTV.setText(birthdate);
            //emailTV.setText(email);
            //telNoTV.setText(telNo);
            //cellNoTV.setText(cellNo);
            //addressTV.setText(address);

            setTextView(fullNameTV, fullName);
            setTextView(genderTV, gender);
            setTextView(statusTV, status);
            setTextView(birthdateTV, birthdate);
            setTextView(emailTV, email);
            setTextView(telNoTV, telNo);
            setTextView(cellNoTV, cellNo);
            setTextView(addressTV, address);

            return v;
        }
        else{
            View v = inflater.inflate(R.layout.updatepersonalfragment, container, false);

            return v;

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ProfileActivity)getActivity()).updateActiveFragment(0);
        }
    }

    private void setTextView(TextView tv, String content){
        if(content.equals("null") || content.isEmpty()){
            notIndicated(tv);
        }
        else{
            tv.setText(content);
        }
    }

    public void notIndicated(TextView tv){
        tv.setText("[Not Indicated]");
    }

    public void update(){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.preferencefragmentlayout,null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(v);
    }

}