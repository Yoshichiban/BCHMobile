package com.bpocareerhub.bchmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private Context _context;//application context
    private SessionManager session;

    private int currentFragment;
    private ImageView activeIV;

    //private String fullName, gender, status, birthdate, email, telNo, cellNo, address, pass;
    private String fullName;
    private String gender;
    private String status;
    private String birthdate;
    private String email;
    private String telNo;
    private String cellNo;
    private String address;
    private String password;
    private String experience;
    private String education;
    private String skills;
    private String languages;

    private ImageView detailsIV;
    private ImageView preferenceIV;
    private ImageView experienceIV;
    private ImageView educationIV;
    private ImageView skillsIV;

    ProfilePageAdapter pageAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _context = this;
        session = new SessionManager(_context);
        session.updateOff();

        pager = (ViewPager)findViewById(R.id.profilePager);
        detailsIV = (ImageView)findViewById(R.id.detailsIV);
        preferenceIV = (ImageView)findViewById(R.id.preferenceIV);
        experienceIV = (ImageView)findViewById(R.id.experienceIV);
        educationIV = (ImageView)findViewById(R.id.educationIV);
        skillsIV = (ImageView)findViewById(R.id.skillsIV);

        detailsIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentFragment!=0){
                    updateActiveFragment(0);
                    pager.setCurrentItem(0);
                }
            }
        });
        preferenceIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentFragment!=1){
                    updateActiveFragment(1);
                    pager.setCurrentItem(1);
                }
            }
        });
        experienceIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentFragment!=2){
                    updateActiveFragment(2);
                    pager.setCurrentItem(2);
                }
            }
        });
        educationIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentFragment!=3){
                    updateActiveFragment(3);
                    pager.setCurrentItem(3);
                }
            }
        });
        skillsIV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentFragment!=4){
                    updateActiveFragment(4);
                    pager.setCurrentItem(4);
                }
            }
        });

        Button updateProfileButton = (Button) findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.updateOn();
                pageAdapter.notifyDataSetChanged();
                ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.buttonSwitcher);
                switcher.showNext();

            }
        });

        Button saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //saveChanges();
                /*
                refresh;
                get updated info and recreate profile activity fragments
                */
                session.updateOff();
                pageAdapter.notifyDataSetChanged();
                ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.buttonSwitcher);
                switcher.showPrevious();
            }
        });
        _context = getApplicationContext();
        session = new SessionManager(_context);
        HashMap<String, String> user = session.getUserDetails();
        email = user.get("email");
        password = user.get("password");
        String urlText = "http://stagingalpha.bpocareerhub.com/APILogin/processLogin/c019edda9ce8a7b4532344c7928b6786/" + email + "/" + password;
        new getProfileInfoTask().execute(urlText);

    }
    private class getProfileInfoTask extends AsyncTask<String, Void, Void> {

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

                JSONObject jsonResponse = new JSONObject(String.valueOf(total));
                JSONObject jsonObj = jsonResponse.optJSONObject("user");

                fullName = jsonObj.getString("fullname");
                /*gender = jsonObj.getString("gender");
                status = jsonObj.getString("status")*/
                gender = "Male";
                status = "It's Complicated";
                birthdate = DateFormat.getDateInstance(DateFormat.LONG).format(jsonObj.getLong("date_created"));
                telNo = jsonObj.getString("date_activated");//TO EDIT
                cellNo = jsonObj.getString("mobile_number");
                address = jsonObj.getString("address_details");
                /*
                experience = jsonObj.getString("experience");
                education = jsonObj.getString("education");
                skills = jsonObj.getString("skills");
                languages = jsonObj.getString("languages")*/
                experience = "La Liga Filipina\nCEO (1892)\n\nLa Solidaridad\nContributory Writer (1889)\n\nFreelance Author\nLiterary Author (1884 - 1887)";
                education = "Universidad Central de Madrid\nLicentiate in Philosophy and Letters (1882 - 1885)";
                skills = "Playboy Extraordinaire\nAdvanced";
                languages = "Filipino (Tagalog)\nNative";


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

            List<Fragment> fragments = getFragments();
            pageAdapter = new ProfilePageAdapter(getSupportFragmentManager(), fragments);
            pager.setAdapter(pageAdapter);
        }
    }

    private List<Fragment> getFragments(){

        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(DetailsFragment.newInstance(fullName,gender,status,birthdate,email,telNo,cellNo,address));
        fList.add(PreferenceFragment.newInstance("Yehosaru"));
        //fList.add(ExperienceFragment.newInstance(experience));
        //fList.add(EducationFragment.newInstance(education));
        //fList.add(SkillsFragment.newInstance(skills,languages));

        return fList;

    }

    public void updateActiveFragment(int fragment){
        /*
        updates the active icon on the navigation
         */
        deactivatePreviousFragment(currentFragment);
        switch (fragment){
            case 0:
                detailsIV.setImageResource(R.drawable.personal_h);
                break;
            case 1:
                preferenceIV.setImageResource(R.drawable.preference_h);
                break;
            case 2:
                experienceIV.setImageResource(R.drawable.experience_h);
                break;
            case 3:
                educationIV.setImageResource(R.drawable.education_h);
                break;
            case 4:
                skillsIV.setImageResource(R.drawable.skills_h);
                break;
        }
        currentFragment = fragment;
    }

    private void deactivatePreviousFragment(int prevFragment){
        switch(prevFragment){
            case 0:
                detailsIV.setImageResource(R.drawable.personal);
                break;
            case 1:
                preferenceIV.setImageResource(R.drawable.preference);
                break;
            case 2:
                experienceIV.setImageResource(R.drawable.experience);
                break;
            case 3:
                educationIV.setImageResource(R.drawable.education);
                break;
            case 4:
                skillsIV.setImageResource(R.drawable.skills);
                break;
        }
    }

}
