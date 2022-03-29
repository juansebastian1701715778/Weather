package com.sebastianmejia.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sebastianmejia.weather.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "REPLACE WITH YOUR API KEY...";
    private WeatherService service = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new WeatherService(API_KEY);
    }

    public void btnGetWeatherInfoOnClick() {

        service.requestWeatherData("Madrid", "es", (isNetworkError, statusCode, root)  -> {

            switch (statusCode) {
                case -1:
                    Log.d("Weather", "Network error");
                    break;
                case 404:
                    Log.d("Weather", "City not found");
                    break;
                case 200: // OK
                    Log.d("Weather", "Current:" + root.getMain().getTemp());
                    break;
            }
        });
    }
}
