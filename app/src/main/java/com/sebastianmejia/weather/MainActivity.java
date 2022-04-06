package com.sebastianmejia.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sebastianmejia.weather.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "8c4add482647e0b84c8ff128415ca746";
    private WeatherService service = null;

    private EditText txtCountryISOCode = null;
    private EditText txtCityName = null;

    private TextView currentResult = null;
    private TextView minimalResult = null;
    private TextView maximunResult = null;

    private Button btnGetWeather = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new WeatherService(API_KEY);
        initViews();
        initEvents();


    }

    public void initViews(){
        txtCountryISOCode = findViewById(R.id.txtCountryISOCode);
        txtCityName = findViewById(R.id.txtCityName);

        btnGetWeather = findViewById(R.id.GetInfo);

        currentResult = findViewById(R.id.currentResult);
        minimalResult = findViewById(R.id.minimalResult);
        maximunResult = findViewById(R.id.maximunResult);

    }

    public void initEvents(){

        btnGetWeather.setOnClickListener(view -> getWeatherInfoOnClick());

    }

    public void getWeatherInfoOnClick(){
        service.requestWeatherData(txtCityName.getText().toString(), txtCountryISOCode.getText().toString(), ((isNetworkError, statusCode, root) -> {
            if(!isNetworkError){
                switch (statusCode){
                    case 200:
                        runOnUiThread(() -> {
                            maximunResult.setText(String.valueOf(root.getMain().getTempMax()));
                        });
                        runOnUiThread(() -> {
                            minimalResult.setText(String.valueOf(root.getMain().getTempMin()));
                        });
                        runOnUiThread(() -> {
                            currentResult.setText(String.valueOf(root.getMain().getTemp()));
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
}
