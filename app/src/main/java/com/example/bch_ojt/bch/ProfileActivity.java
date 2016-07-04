package com.example.bch_ojt.bch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private Context _context;//application context
    private SessionManager session;

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

    private TextView fullNameTV;
    private TextView genderTV;
    private TextView statusTV;
    private TextView birthdateTV;
    private TextView emailTV;
    private TextView telNoTV;
    private TextView cellNoTV;
    private TextView addressTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullNameTV = (TextView) findViewById(R.id.fullNameTV);
        genderTV = (TextView) findViewById(R.id.genderTV);
        statusTV = (TextView) findViewById(R.id.statusTV);
        birthdateTV = (TextView) findViewById(R.id.birthdateTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        telNoTV = (TextView) findViewById(R.id.telNoTV);
        cellNoTV = (TextView) findViewById(R.id.cellNoTV);
        addressTV = (TextView) findViewById(R.id.addressTV);

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
                //System.out.println(total);
                //return String.valueOf(conn.getResponseMessage());

                JSONObject jsonResponse = new JSONObject(String.valueOf(total));
                JSONObject jsonObj = jsonResponse.optJSONObject("user");

                fullName = jsonObj.getString("fullname");
                gender = "Male";
                status = "It's Complicated";
                birthdate = DateFormat.getDateInstance(DateFormat.LONG).format(jsonObj.getLong("date_created"));
                telNo = jsonObj.getString("date_activated");//TO EDIT
                cellNo = jsonObj.getString("mobile_number");
                address = jsonObj.getString("address_details");

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
            fullNameTV.setText(fullName);
            genderTV.setText(gender);
            statusTV.setText(status);
            birthdateTV.setText(birthdate);
            emailTV.setText(email);
            telNoTV.setText(telNo);
            cellNoTV.setText(cellNo);
            addressTV.setText(address);
        }
    }

}
