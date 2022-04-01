package com.sebastianmejia.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.sebastianmejia.weather.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "8c4add482647e0b84c8ff128415ca746";
    private WeatherService service = null;

    private TextView MaximunResult = null;

    private Button btnGetWeather = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new WeatherService(API_KEY);
        initEvents();
        initViews();
        btnGetWeatherInfoOnClick();
    }

    public void initViews(){
        btnGetWeather = findViewById(R.id.GetInfo);
        MaximunResult = findViewById(R.id.MaximunResult);
    }

    public void initEvents(){

        btnGetWeather.setOnClickListener(view -> getWeatherInfoOnClick());
    }

    public void getWeatherInfoOnClick(){
        service.requestWeatherData("Madrid", "es", ((isNetworkError, statusCode, root) -> {
            if(!isNetworkError){
                switch (statusCode){
                    case 200:
                        runOnUiThread(() -> {
                            MaximunResult.setText(String.valueOf(root.getMain().getTempMax()));
                        });
                        break;
                    case 404:
                        Log.d("Weather", "City not found");
                        break;
                }
            }else{
                Log.d("Weather", "Network error");
            }
        }));
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
