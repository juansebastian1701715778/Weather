package com.sebastianmejia.weather.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sebastianmejia.weather.services.model.Root;

import java.net.MalformedURLException;
import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class WeatherService {

    private final String URL_SERVICE = "https://api.openweathermap.org/data/2.5/weather";
    private String theAPIKey = "";

    public WeatherService(String newAPIKey)
    {
        theAPIKey = newAPIKey;
    }

    public void requestWeatherData(String cityName, String countryCode, OnResponse delegate){
        final String format = "%s?appid=%s&q=%s,%s&units=metric&lang=es";
        URL url = null;
        String strUrl = "";

        strUrl = String.format(format, URL_SERVICE, theAPIKey, cityName, countryCode);
        try {
            url = new URL(strUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }



        URLRequest request = new URLRequest(url);

        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            Root root = null;

            if(resp.getStatusCode() == 200){
                String text = data.toText();

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson json = gsonBuilder.create();
                root =  json.fromJson(text, Root.class);
            }

            if(delegate != null) {
                delegate.run(resp.getStatusCode(), root);
            }
        }).resume();
    }

    public interface OnResponse {
        public abstract void run(int statusCode, Root root);
    }



}
