package com.bpocareerhub.bchmobile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by BCH_OJT on 6/29/2016.
 */
public class NavigationDrawer{
    private ArrayAdapter<String> mAdapter;
    private Context context;
    private ListView mDrawerList;
    private SessionManager session;

    public NavigationDrawer(Context c, ListView l, SessionManager s){
        context = c;
        mDrawerList = l;
        session = s;
        addDrawerItems();
        setAction();
    }
    private void addDrawerItems(){
        String[] optionArray = {"Profile", "Settings", "Logout"};
        mAdapter= new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,optionArray);

    }
    private void setAction(){
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                if(position==0){
                    Intent i = new Intent(view.getContext(), ProfileActivity.class);
                    //Toast.makeText(view.getContext(),session.getUserDetails().get("password"),Toast.LENGTH_LONG).show();
                    context.startActivity(i);
                }
                else if(position==1){
                    Intent i = new Intent(view.getContext(), SettingsActivity.class);
                    Toast.makeText(view.getContext(),"Settings",Toast.LENGTH_LONG).show();
                    context.startActivity(i);
                }
                else if(position==2){
                    session.logoutSession();
                }
            }
        });
    }
    public void addNav(){
        mDrawerList.setAdapter(mAdapter);
    }
}
