package com.bpocareerhub.bchmobile;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
        session.checkSessionInLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signup = (Button)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signup(view);
            }
        });
    }

    public void login(View view) {

        EditText emailText = (EditText)findViewById(R.id.email);
        EditText passText = (EditText)findViewById(R.id.password);
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        String urlText = "http://stagingalpha.bpocareerhub.com/APILogin/processLogin/c019edda9ce8a7b4532344c7928b6786/" + email + "/" + pass;
        if(checkInternetConnection()) {
            new loginTask().execute(urlText,email,pass);
        }
    }

    public void signup(View view){
        Intent i = new Intent(this, SignupFirstLayerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean checkInternetConnection() {
        //This module can be improved(e.g., establish a real connection to a webpage and check the result/response)
        ConnectivityManager conn =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conn.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED){
            return true;
        }
        else if(activeNetwork == null || activeNetwork.getState() == NetworkInfo.State.DISCONNECTED){
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private class loginTask extends AsyncTask<String, Void, Boolean> {
        boolean validAccount = true;
        String email,password;

        @Override
        protected Boolean doInBackground(String... urls) {
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

                email = urls[1];
                password = urls[2];
                String encryptedPassword = encrypt(password);
                //group_id: 2 = Job Seeker; accept only "Job Seeker" accounts
                if( email.equals(jsonObj.optString("email")) && encryptedPassword.equals(jsonObj.optString("password")) ) {
                    if("2".equals(jsonObj.optString("group_id"))) {
                        return true;
                    }
                    else{
                        validAccount=false;
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            catch(MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                Toast.makeText(getApplicationContext(), "Valid", Toast.LENGTH_LONG).show();
                session.createLoginSession(email,password);
                //update token
                switchIntent();
            }
            else if(validAccount == false) {
                Toast.makeText(getApplicationContext(), "Not a jobseeker account", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Account Details", Toast.LENGTH_LONG).show();
            }

        }
    }
    private void switchIntent(){
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private String encrypt(String passwordToHash){
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
       return generatedPassword;
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        session.checkSessionInLogin();
    }
}
