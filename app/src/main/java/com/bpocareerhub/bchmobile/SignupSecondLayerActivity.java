package com.bpocareerhub.bchmobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SignupSecondLayerActivity extends AppCompatActivity {
    Context _context;
    StringBuilder strbuilder;

    String firstName;
    String lastName;
    String email;
    String password;
    String mobileNumber;

    EditText cityText;
    EditText schoolText;
    EditText academicsText;
    EditText fieldOfStudyText;
    EditText positionLevelText;
    EditText companyNameSpinner;
    EditText industrySpinner;
    EditText specializationSpinner;

    String city;
    String school;
    String academics;
    String fieldOfStudy;
    String positionLevel;
    String companyName;
    String industry;
    String specialization;

    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second_layer);
        _context = this;
        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        mobileNumber = intent.getStringExtra("mobileNumber");

        cityText = (EditText)findViewById(R.id.city);
        city = cityText.getText().toString();

        schoolText = (EditText)findViewById(R.id.school);
        school = schoolText.getText().toString();

        academicsText = (EditText)findViewById(R.id.academics);
        academics = academicsText.getText().toString();

        fieldOfStudyText = (EditText)findViewById(R.id.fieldOfStudy);
        fieldOfStudy = fieldOfStudyText.getText().toString();

        positionLevelText = (EditText)findViewById(R.id.positionLevel);
        positionLevel = positionLevelText.getText().toString();

        companyNameSpinner = (EditText)findViewById(R.id.companyName);
        companyName = companyNameSpinner.getText().toString();

        industrySpinner = (EditText)findViewById(R.id.industry);
        industry = industrySpinner.getText().toString();

        specializationSpinner = (EditText)findViewById(R.id.specialization);
        specialization = specializationSpinner.getText().toString();

        Button signupButton = (Button)findViewById(R.id.signup);
        signupButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                String firstLayerURL = "http://stagingalpha.bpocareerhub.com/APIRegistration/processJobSeekerRegistration/EDDRO2ZBTN7TM3KYFV604/c019edda9ce8a7b4532344c7928b6786";
                new firstLayerSignupTask().execute(firstLayerURL);
            }
        });
    }

    private class firstLayerSignupTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
            /*
                http://www.wikihow.com/Execute-HTTP-POST-Requests-in-Android
                http://stackoverflow.com/questions/9767952/how-to-add-parameters-to-httpurlconnection-using-post
             */

                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Key","Value");
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

               Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("txtEmail",email)
                        .appendQueryParameter("txtPassword",password)
                        .appendQueryParameter("txtFirstname",firstName)
                        .appendQueryParameter("txtLastname",lastName)
                        .appendQueryParameter("txtMobileNumber",mobileNumber);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                os.write(query.getBytes());
                os.flush();
                os.close();

                //conn.connect();
                strbuilder = new StringBuilder();
                strbuilder.append(conn.getResponseCode())
                        .append(" ")
                        .append(conn.getResponseMessage())
                        .append("\n");


            }
            catch(Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            String accountDetailURL = "http://stagingalpha.bpocareerhub.com/APILogin/processLogin/c019edda9ce8a7b4532344c7928b6786/" + email + "/" + password;
            new getUserIDTask().execute(accountDetailURL);
        }
    }

    private class getUserIDTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
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

                return jsonObj.optString("user_id");

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String userID) {
            String secondLayerURL = "stagingalpha.bpocareerhub.com/APIRegistration/processLayerTwoRegistration/EDDRO2ZBTN7TM3KYFV604/c019edda9ce8a7b4532344c7928b6786/" + userID;
            new secondLayerSignupTask().execute(secondLayerURL);
        }
    }

    private class secondLayerSignupTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Key","Value");
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("ddCity",city)
                        .appendQueryParameter("ddAcademics",academics)
                        .appendQueryParameter("ddSchool",school)
                        .appendQueryParameter("ddFieldOfStudy",fieldOfStudy)
                        .appendQueryParameter("ddPositionLevels",positionLevel)
                        .appendQueryParameter("txtCompanyName",companyName)
                        .appendQueryParameter("ddIndustries",industry)
                        .appendQueryParameter("ddSpecializations",specialization);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                os.write(query.getBytes());
                os.flush();
                os.close();

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Toast.makeText(_context, "DONE!", Toast.LENGTH_SHORT).show();
        }
    }
}
