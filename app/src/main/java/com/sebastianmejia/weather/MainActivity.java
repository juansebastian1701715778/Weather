package com.sebastianmejia.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sebastianmejia.weather.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "8c4add482647e0b84c8ff128415ca746";
    private WeatherService service = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new WeatherService(API_KEY);
        btnGetWeatherInfoOnClick();
    }

    public void btnGetWeatherInfoOnClick() {

        service.requestWeatherData("Manizales", "co", (isNetworkError, statusCode, root)  -> {

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
