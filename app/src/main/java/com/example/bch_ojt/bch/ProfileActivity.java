package com.example.bch_ojt.bch;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        detailsIV = (ImageView)findViewById(R.id.detailsIV);
        preferenceIV = (ImageView)findViewById(R.id.preferenceIV);
        experienceIV = (ImageView)findViewById(R.id.experienceIV);
        educationIV = (ImageView)findViewById(R.id.educationIV);
        skillsIV = (ImageView)findViewById(R.id.skillsIV);

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
                gender = "Male";
                status = "It's Complicated";
                birthdate = DateFormat.getDateInstance(DateFormat.LONG).format(jsonObj.getLong("date_created"));
                telNo = jsonObj.getString("date_activated");//TO EDIT
                cellNo = jsonObj.getString("mobile_number");
                address = jsonObj.getString("address_details");
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

            ViewPager pager = (ViewPager)findViewById(R.id.profilePager);

            pager.setAdapter(pageAdapter);
        }
    }

    private List<Fragment> getFragments(){

        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(DetailsFragment.newInstance(fullName,gender,status,birthdate,email,telNo,cellNo,address));
        fList.add(PreferenceFragment.newInstance("Yehosaru"));
        fList.add(ExperienceFragment.newInstance(experience));
        fList.add(EducationFragment.newInstance(education));
        fList.add(SkillsFragment.newInstance(skills,languages));

        return fList;

    }

    public void updateActiveFragment(int fragment){
        deactivatePreviousFragment(currentFragment);
        switch (fragment){
            case 1:
                detailsIV.setImageResource(R.drawable.personal_h);
                break;
            case 2:
                preferenceIV.setImageResource(R.drawable.preference_h);
                break;
            case 3:
                experienceIV.setImageResource(R.drawable.experience_h);
                break;
            case 4:
                educationIV.setImageResource(R.drawable.education_h);
                break;
            case 5:
                skillsIV.setImageResource(R.drawable.skills_h);
                break;
        }
        currentFragment = fragment;
    }

    private void deactivatePreviousFragment(int prevFragment){
        switch(prevFragment){
            case 1:
                detailsIV.setImageResource(R.drawable.personal);
                break;
            case 2:
                preferenceIV.setImageResource(R.drawable.preference);
                break;
            case 3:
                experienceIV.setImageResource(R.drawable.experience);
                break;
            case 4:
                educationIV.setImageResource(R.drawable.education);
                break;
            case 5:
                skillsIV.setImageResource(R.drawable.skills);
                break;
        }
    }
}
