package com.example.bch_ojt.bch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class JobActivity extends AppCompatActivity {
    private Context _context;//application context
    private SessionManager session;
    private Intent prevIntent;
    private String careerId;
    private String careerTitle, careerDescription, regionCityCode, companyName, companyDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        prevIntent = getIntent();
        careerId = prevIntent.getStringExtra("careerId");
        _context = getApplicationContext();
        session = new SessionManager(_context);
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
                careerTitle = jsonObj.getString("career_title");
                careerDescription = jsonObj.getString("career_description");
                regionCityCode = jsonObj.getString("region_city_code");
                companyName = jsonObj.getString("name");
                companyDescription = jsonObj.getString("description");
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
            TextView careerTitleTV = (TextView) findViewById(R.id.careerTitleTV);
            TextView careerDescriptionTV = (TextView) findViewById(R.id.careerDescriptionTV);
            TextView regionCityCodeTV = (TextView) findViewById(R.id.regionCityCodeTV);
            TextView companyNameTV = (TextView) findViewById(R.id.companyNameTV);
            TextView companyDescriptionTV = (TextView) findViewById(R.id.companyDescriptionTV);

            careerTitleTV.setText(careerTitle);
            careerDescriptionTV.setText(careerDescription);
            regionCityCodeTV.setText(regionCityCode);
            companyNameTV.setText(companyName);
            companyDescriptionTV.setText(companyDescription);
        }
    }
}
