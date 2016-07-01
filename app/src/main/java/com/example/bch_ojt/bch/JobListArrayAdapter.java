package com.example.bch_ojt.bch;

/**
 * Created by BCH_OJT on 6/29/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JobListArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> jobAd;
    private final ArrayList<String> company;
    private final ArrayList<String> type;
    private final ArrayList<String> location;
    private final ArrayList<Date> date;
    private final ArrayList<Drawable> drawable;

    static class ViewHolder {
        public TextView jobAd;
        public TextView company;
        public TextView type;
        public TextView location;
        public TextView date;
        public ImageView logo;
    }

    public JobListArrayAdapter(Activity context, ArrayList<String> j, ArrayList<String> c, ArrayList<String> t, ArrayList<String> l, ArrayList<Date> d, ArrayList<Drawable> dr) {
        super(context, R.layout.rowlayout, j);

        this.context = context;

        this.jobAd = j;
        this.company = c;
        this.type = t;
        this.location = l;
        this.date = d;
        this.drawable = dr;
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
            viewHolder.date = (TextView) rowView.findViewById(R.id.date);
            viewHolder.logo = (ImageView) rowView.findViewById(R.id.logo);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String jobAdString = jobAd.get(position);
        String companyString = company.get(position);
        String typeString = type.get(position);
        String locationString = location.get(position);
        String dateString = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT).format(date.get(position));
        Drawable logoDrawable = drawable.get(position);

        holder.jobAd.setText(jobAdString);
        holder.company.setText(companyString);
        holder.type.setText(typeString);
        holder.location.setText(locationString);
        holder.date.setText(dateString);
        if(logoDrawable != null){
            holder.logo.setImageDrawable(logoDrawable);
        }
        else {
            holder.logo.setImageResource(R.drawable.bchmobilelogo);
        }

        return rowView;
    }
}