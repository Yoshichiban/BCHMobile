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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupFirstLayerActivity extends AppCompatActivity {
    String firstName, lastName, email, password, mobileNumber;
    EditText firstNameText, lastNameText, emailText, passwordText, mobileNumberText;
    Context _context;
    SessionManager session;
    String query, message;
    JSONObject response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_first_layer);
        _context = getApplicationContext();
        session = new SessionManager(_context);
        session.checkSessionInLogin();

        firstNameText = (EditText)findViewById(R.id.firstName);
        lastNameText = (EditText)findViewById(R.id.lastName);
        emailText = (EditText)findViewById(R.id.email);
        passwordText = (EditText)findViewById(R.id.password);
        mobileNumberText = (EditText)findViewById(R.id.mobileNumber);

        Button signupButton = (Button)findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String signupURL = "http://stagingalpha.bpocareerhub.com/APIRegistration/processJobSeekerRegistration/EDDRO2ZBTN7TM3KYFV604/c019edda9ce8a7b4532344c7928b6786";
                getUserDetails();
                new signupTask().execute(signupURL);
            }
        });
    }

    private class signupTask extends AsyncTask<String, Void, Void> {

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

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("txtEmail",email)
                        .appendQueryParameter("txtPassword",password)
                        .appendQueryParameter("txtFirstname",firstName)
                        .appendQueryParameter("txtLastname",lastName)
                        .appendQueryParameter("txtMobileNumber",mobileNumber);
                query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                os.write(query.getBytes());
                os.flush();
                os.close();

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();

                response = new JSONObject(stringBuilder.toString());
                message = response.optString("message");
            }
            catch(Exception e) {
                Toast.makeText(_context, "EXCEPTION!", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            //no message = signup successful
            if(message.equals("")) {
                session.createLoginSession(email,password);
                switchToProfile();
            }
            else {
                Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserDetails(){
        firstName = firstNameText.getText().toString();
        lastName = lastNameText.getText().toString();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        mobileNumber = mobileNumberText.getText().toString();
    }
    private void switchToProfile(){
        Intent i = new Intent(_context, ProfileActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        session.checkSessionInLogin();
    }
}
