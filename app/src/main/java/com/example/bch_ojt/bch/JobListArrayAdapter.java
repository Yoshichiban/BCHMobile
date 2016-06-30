package com.example.bch_ojt.bch;

/**
 * Created by BCH_OJT on 6/29/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JobListArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> jobAd;
    private final ArrayList<String> company;
    private final ArrayList<String> type;
    private final ArrayList<String> location;

    static class ViewHolder {
        public TextView jobAd;
        public TextView company;
        public TextView type;
        public TextView location;
        public ImageView logo;
    }

    public JobListArrayAdapter(Activity context, ArrayList<String> j, ArrayList<String> c, ArrayList<String> t, ArrayList<String> l) {
        super(context, R.layout.rowlayout, j);

        this.context = context;

        this.jobAd = j;
        this.company = c;
        this.type = t;
        this.location = l;

        /*
        String jobAdString = this.jobAd.get(0);
        String companyString = this.company.get(1);
        String typeString = this.type.get(3);
        String locationString = this.location.get(4);

        Toast.makeText(context,jobAdString,Toast.LENGTH_LONG).show();

        Toast.makeText(context,companyString,Toast.LENGTH_LONG).show();
        Toast.makeText(context,typeString,Toast.LENGTH_LONG).show();
        Toast.makeText(context,locationString,Toast.LENGTH_LONG).show();*/
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.jobAd = (TextView) rowView.findViewById(R.id.jobAdTV);
            viewHolder.company = (TextView) rowView.findViewById(R.id.companyTV);
            viewHolder.type = (TextView) rowView.findViewById(R.id.typeTV);
            viewHolder.location = (TextView) rowView.findViewById(R.id.locationTV);
            viewHolder.logo = (ImageView) rowView.findViewById(R.id.logo);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String jobAdString = jobAd.get(position);
        String companyString = company.get(position);
        String typeString = type.get(position);
        String locationString = location.get(position);

        holder.jobAd.setText(jobAdString);
        holder.company.setText(companyString);
        holder.type.setText(typeString);
        holder.location.setText(locationString);


        return rowView;
    }
}