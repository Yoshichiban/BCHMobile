package com.example.bch_ojt.bch;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JobActivity extends AppCompatActivity {
    private Context _context;//application context
    private SessionManager session;
    private Intent prevIntent;
    private String careerId;
    private String jobTitle, jobDescription, regionCityCode, companyName;

    private ImageView companylogoIV;
    private TextView jobTitleTV;
    private TextView companyNameTV;
    private TextView locationTV;
    private TextView jobInfoHeadingTV;
    private TextView industryTV;
    private TextView employmentTypeTV;
    private TextView jobLevelTV;
    private TextView salaryTV;
    private TextView specializationTV;
    private TextView educationTV;
    private TextView experienceTV;
    private TextView prefAgeTV;
    private TextView prefGenderTV;

    private TextView jobDescriptionHeadingTV;
    private TextView jobDescriptionTV;

    private TextView jobRequirementsHeadingTV;
    private TextView jobRequirementsTV;

    private InputStream is;
    private Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        prevIntent = getIntent();
        careerId = prevIntent.getStringExtra("careerId");
        _context = getApplicationContext();
        session = new SessionManager(_context);

        companylogoIV = (ImageView) findViewById(R.id.companyLogo);
        jobTitleTV = (TextView) findViewById(R.id.jobTitleTV);
        companyNameTV = (TextView) findViewById(R.id.companyNameTV);
        locationTV = (TextView) findViewById(R.id.locationTV);
        jobInfoHeadingTV = (TextView) findViewById(R.id.jobInfoHeadingTV);
        industryTV = (TextView) findViewById(R.id.industryTV);
        employmentTypeTV = (TextView) findViewById(R.id.employmentTypeTV);
        jobLevelTV = (TextView) findViewById(R.id.jobLevelTV);
        salaryTV = (TextView) findViewById(R.id.salaryTV);
        specializationTV = (TextView) findViewById(R.id.specializationTV);
        educationTV = (TextView) findViewById(R.id.educationTV);
        experienceTV = (TextView) findViewById(R.id.experienceTV);
        prefAgeTV = (TextView) findViewById(R.id.prefAgeTV);
        prefGenderTV = (TextView) findViewById(R.id.prefGenderTV);
        jobDescriptionHeadingTV = (TextView) findViewById(R.id.jobDescriptionHeadingTV);
        jobDescriptionTV = (TextView) findViewById(R.id.jobDescriptionTV);
        jobRequirementsHeadingTV = (TextView) findViewById(R.id.jobRequirementsHeadingTV);
        jobRequirementsTV = (TextView) findViewById(R.id.jobRequirementsTV);

        String urlText = "http://stagingalpha.bpocareerhub.com/APIJobSearch/getSearchJobs/EDDRO2ZBTN7TM3KYFV604";
        new getJobInfoTask().execute(urlText,careerId);
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
                //System.out.println(total);
                //return String.valueOf(conn.getResponseMessage());

                JSONObject jsonResponse = new JSONObject(String.valueOf(total));
                JSONObject jsonObj = jsonResponse.optJSONObject(careerId);
                jobTitle = jsonObj.getString("career_title");
                jobDescription = jsonObj.getString("career_description");
                regionCityCode = jsonObj.getString("region_city_code");
                companyName = jsonObj.getString("name");
                is = (InputStream) new URL(jsonObj.getString("employerLogo")).getContent();
                d = Drawable.createFromStream(is, null);
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
           // Toast.makeText(_context, careerTitle, Toast.LENGTH_LONG).show();

            if(d != null){
                companylogoIV.setImageDrawable(d);
            }
            else {
                companylogoIV.setImageResource(R.drawable.bchmobilelogo);
            }

            jobTitleTV.setText(jobTitle);
            companyNameTV.setText(companyName);
            locationTV.setText(regionCityCode);

            jobInfoHeadingTV.setText("Job Information:\n");
            /*
            TO DO: get the following data from API
             */
            industryTV.setText("Industry: Call Center / IT-Enabled Services / BPO");
            employmentTypeTV.setText("Employment Type: Regular");
            jobLevelTV.setText("Fresh Graduate");
            salaryTV.setText("Monthly Salary: Not specified");
            specializationTV.setText("Specialization: Others");
            educationTV.setText("Educational Attainment: Bachelor's / College Undergraduate");
            experienceTV.setText("Work Experience: None");
            prefAgeTV.setText("Preferred Age: None");
            prefGenderTV.setText("Preferred Gender: None");

            jobDescriptionHeadingTV.setText("Job Description:\n");
            jobDescriptionTV.setText(jobDescription);

            jobRequirementsHeadingTV.setText("Job Requirements:\n");
            jobRequirementsTV.setText("College Degree");

        }
    }
}
