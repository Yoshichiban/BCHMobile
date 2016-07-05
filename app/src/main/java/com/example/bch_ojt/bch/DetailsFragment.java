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
public class DetailsFragment extends Fragment {

    public static final DetailsFragment newInstance(String fullName, String gender, String status,
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

        String fullName = getArguments().getString("fullName");
        String gender = getArguments().getString("gender");
        String status = getArguments().getString("status");
        String birthdate = getArguments().getString("birthdate");
        String email = getArguments().getString("email");
        String telNo = getArguments().getString("telNo");
        String cellNo = getArguments().getString("cellNo");
        String address = getArguments().getString("address");

        View v = inflater.inflate(R.layout.personalfragmentlayout, container, false);

        TextView fullNameTV = (TextView)v.findViewById(R.id.fullNameTV);
        TextView genderTV = (TextView)v.findViewById(R.id.genderTV);
        TextView statusTV = (TextView)v.findViewById(R.id.statusTV);
        TextView birthdateTV = (TextView)v.findViewById(R.id.birthdateTV);
        TextView emailTV = (TextView)v.findViewById(R.id.emailTV);
        TextView telNoTV = (TextView)v.findViewById(R.id.telNoTV);
        TextView cellNoTV = (TextView)v.findViewById(R.id.cellNoTV);
        TextView addressTV = (TextView)v.findViewById(R.id.addressTV);

        fullNameTV.setText(fullName);
        genderTV.setText(gender);
        statusTV.setText(status);
        birthdateTV.setText(birthdate);
        emailTV.setText(email);
        telNoTV.setText(telNo);
        cellNoTV.setText(cellNo);
        addressTV.setText(address);
        
        return v;

    }

}