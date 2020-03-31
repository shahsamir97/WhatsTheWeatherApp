package com.example.whatstheweatherapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadWeatherData extends AsyncTask<String, Void, String> {
    private WeakReference<MainActivity> mainActivityWeakReference;
    MainActivity mainActivity;

    public DownloadWeatherData(MainActivity activity) {
        this.mainActivityWeakReference = new WeakReference<MainActivity>( activity);
    }

    public String weather = "Not found";
    @Override
    protected String doInBackground(String... urls) {
        try {
            String webContent = "";
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();

            while (data != -1) {
                char currentCharacter = (char) data;
                webContent += currentCharacter;
                data = reader.read();
            }

            return webContent;
        } catch (IOException e) {
            e.printStackTrace();
            return "City not Found";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainActivity = mainActivityWeakReference.get();
        if (s != "City not Found") {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherinfo = jsonObject.getString("weather");
                JSONArray array = new JSONArray(weatherinfo);
                JSONObject jPart = array.getJSONObject(0);
                weather = jPart.getString("main");
                Log.i("FROM POST EXECUTE ##", weather);

                if (mainActivity != null || !mainActivity.isFinishing()) {
                    mainActivity.textView.setText(weather);
                    if (weather.matches("Clouds")) {
                        mainActivity.constraintLayout.setBackgroundResource(R.drawable.cloudy2);
                    } else if (weather.matches("Haze")) {
                        mainActivity.constraintLayout.setBackgroundResource(R.drawable.drizzel3);
                    } else if (weather.matches("Sunny")) {

                    }
                } else {
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mainActivity.textView.setText(s);
        }
        }

}
