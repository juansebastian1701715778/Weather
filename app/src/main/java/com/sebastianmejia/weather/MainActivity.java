package com.sebastianmejia.weather;

import static com.sebastianmejia.weather.resources.Countries.getCountryNames;
import static com.sebastianmejia.weather.resources.Countries.getISOCodeByName;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sebastianmejia.weather.services.CityService;
import com.sebastianmejia.weather.services.WeatherService;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "8c4add482647e0b84c8ff128415ca746";
    private WeatherService service = null;
    private CityService cityService;

    private AutoCompleteTextView txtCountryName = null;
    private EditText txtCityName = null;

    private TextView currentResult = null;
    private TextView minimalResult = null;
    private TextView maximumResult = null;

    private Button btnGetWeather = null;
    private final String LANGUAGE = Locale.getDefault().getLanguage();
    private String[] currentCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new WeatherService(API_KEY);
        cityService = new CityService();
        initViews();
        initEvents();


    }

    public void initViews(){
        txtCountryName = findViewById(R.id.txtCountryName);
        txtCityName = findViewById(R.id.txtCityName);

        btnGetWeather = findViewById(R.id.GetInfo);

        currentResult = findViewById(R.id.currentResult);
        minimalResult = findViewById(R.id.minimalResult);
        maximumResult = findViewById(R.id.maximumResult);

    }

    public void initEvents(){

        btnGetWeather.setOnClickListener(view -> getWeatherInfoOnClick());
        initAutoComplete();
        //getCitiesByCountry();
    }

    public void initAutoComplete(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getCountryNames(LANGUAGE));

        txtCountryName.setAdapter(adapter);
    }

    public void getCitiesByCountry(){
        cityService.requestCities("Colombia",
                ((isNetworkError, statusCode, cities) -> {
                    if(!isNetworkError){
                        switch (statusCode){
                            case 200:
                                runOnUiThread(() -> {
                                    String city = cities.getMain().getCities();
                                });
                                break;
                            case 404:
                                Log.d("Cities", "Country not found");
                                break;
                        }
                    }else{
                        Log.d("Cities", "Network error");
                    }
                }));
    }

    public void getWeatherInfoOnClick(){
        service.requestWeatherData(txtCityName.getText().toString(),
                getISOCodeByName(txtCountryName.getText().toString(), LANGUAGE),
                ((isNetworkError, statusCode, root) -> {
            if(!isNetworkError){
                switch (statusCode){
                    case 200:
                        runOnUiThread(() -> {
                            maximumResult.setText(String.valueOf(root.getMain().getTempMax()));
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
