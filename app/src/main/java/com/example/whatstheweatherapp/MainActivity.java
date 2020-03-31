package com.example.whatstheweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    EditText editText;
    ConstraintLayout constraintLayout;
    DownloadWeatherData downloadWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.weatherTextView);
        editText = findViewById(R.id.cityEditText);
        constraintLayout = findViewById(R.id.contraintLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWeatherData = new DownloadWeatherData(MainActivity.this);
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                String cityName = editText.getText().toString();
                String api = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=66565c8ca16a171bf077dfbd58a00e9d";
                try {
                    String result = downloadWeatherData.execute(api).get();
                    Log.i("From MAIN", result);
                    //constraintLayout.setBackgroundResource(R.drawable.cloudy2);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


    }


}
