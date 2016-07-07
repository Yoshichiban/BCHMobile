package com.bpocareerhub.bchmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private Context _context;//application context
    private Activity _activity;

    private JobListArrayAdapter listAdapter;
    private ListView jobListing;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    /*
    final String[] jobAd = new String[] { "Graphical User Interface (GUI) Programmer ", "Computer Programmer", "Job3", "Job4", "Job5", "Job6", "Job7"};
    String[] company = new String[] { "Company1", "Company2", "Company3", "Company4", "Company5", "Company6", "Company7"};
    String[] type = new String[] { "Type1", "Type2", "Type3", "Type4", "Type5", "Type6", "Type7"};
    String[] location = new String[] { "NCR", "NCR", "NCR", "NCR", "NCR", "NCR", "NCR"};
    final String[] career_id = new String[] {"9150", "9151", "9152", "9153", "9154", "9155", "9156"};
    */
    private ArrayList<String> jobAd = new ArrayList<String>();
    private ArrayList<String> company = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> location = new ArrayList<>();
    private ArrayList<String> careerId = new ArrayList<>();
    private ArrayList<Date> dateList = new ArrayList<>();
    private ArrayList<Drawable> drawable = new ArrayList<>();

    private InputStream is;
    private Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = getApplicationContext();
        _activity = this;

        /* Session */
        session = new SessionManager(_context);
        session.checkSessionInApp();
        HashMap<String, String> user = session.getUserDetails();
        String urlText = "http://stagingalpha.bpocareerhub.com/APIJobSearch/getSearchJobs/EDDRO2ZBTN7TM3KYFV604";
        new getJobInfoTask().execute(urlText);

        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationDrawer nav = new NavigationDrawer(this, mDrawerList, session);

        /* Job Listing*/
        jobListing = (ListView) findViewById(R.id.jobListing);
        jobListing.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                Intent i = new Intent(view.getContext(), JobActivity.class);
                i.putExtra("careerId",careerId.get(position));
                startActivity(i);
            }
        });





        /* Navigation Drawer */
        setupDrawer();
        nav.addNav();


    }
    private class getJobInfoTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                //TO DO: Optimization; convert string response directly to JSONArray?
                JSONObject jsonResponse = new JSONObject(String.valueOf(total));
                Iterator fields = jsonResponse.keys();

                JSONArray jsonArray = new JSONArray();
                while (fields.hasNext()){
                    String key = (String) fields.next();
                    jsonArray.put(jsonResponse.get(key));
                }

                List<JSONObject> jsons = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsons.add(jsonArray.getJSONObject(i));
                }

                Collections.sort(jsons, new Comparator<JSONObject>() {
                    Long ld, rd;
                    @Override
                    public int compare(JSONObject lhs, JSONObject rhs) {
                        try {
                            ld = lhs.getLong("date_post");
                            rd = rhs.getLong("date_post");
                        } catch (JSONException e) {
                            Log.v("TAGSA", "JSON: " + e.getMessage());
                        }
                        // Here you could parse string id to integer and then compare.
                        return ld.compareTo(rd);
                    }
                });

                jsonArray = new JSONArray(jsons);
                JSONObject jsonObj;
                for(int i=0; i<jsonArray.length(); i++){
                    jsonObj = jsonArray.getJSONObject(i);
                    jobAd.add(jsonObj.getString("career_title"));
                    company.add(jsonObj.getString("name"));
                    type.add(jsonObj.getString("industry_id"));
                    location.add(jsonObj.getString("region_city_code"));
                    careerId.add(jsonObj.getString("career_id"));
                    dateList.add(new Date(jsonObj.getLong("date_post")));
                    is = (InputStream) new URL(jsonObj.getString("employerLogo")).getContent();
                    d = Drawable.createFromStream(is, null);
                    drawable.add(d);
                }
            }
            catch(MalformedURLException e) {
                Log.v("TAGSA", "MalformedURL: " + e.getMessage());

            }
            catch (IOException e) {
                Log.v("TAGSA", "IO: " + e.getMessage());
            } catch (JSONException e) {
                Log.v("TAGSA", "JSON: " + e.getMessage());
            }
            catch(NullPointerException e){
                Log.v("TAGSA", "Null Pointer: " + e.getMessage());
            }
            catch(Exception e){
                Log.v("TAGSA","Exception: " + e.getMessage());
            }
            return null;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void v) {
            //test.setImageDrawable(d);
            //test.setImageBitmap(b);
            listAdapter = new JobListArrayAdapter(_activity, jobAd, company, type, location, dateList, drawable);
            jobListing.setAdapter(listAdapter);
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        session.checkSessionInApp();
    }

    /* Reference for Navigation Drawer
    * http://blog.teamtreehouse.com/add-navigation-drawer-android
    **/
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
