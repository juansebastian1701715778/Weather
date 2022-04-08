package com.sebastianmejia.weather.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sebastianmejia.weather.services.model.Root;

import java.net.URL;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLSession;

public class CityService {
    public CityService (){
    }

    public void requestCities(String country, CityService.OnDataResponse delegate) {
        URL url = null;
        URLComponents components = new URLComponents();

        components.setScheme("https");
        components.setHost("shivammathur.com");
        components.setPath("/countrycity/cities/" + country);

        url = components.getURL();

        URLSession.getShared().dataTask(url, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            Cities cities = null;
            int statusCode = -1;

            if (error == null && resp.getStatusCode() == 200) {
                String text = data.toText();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                Gson gson = gsonBuilder.create();
                cities = gson.fromJson(text, Cities.class);
                statusCode = resp.getStatusCode();
            }

            if (delegate != null) {
                delegate.onChange(error != null, statusCode, cities);
            }
        }).resume();

    }

    public interface OnDataResponse {
        public abstract void onChange(boolean isNetworkError, int statusCode, Cities cities);
    }


    public class Cities {
        private Main main;

        public Main getMain() {
            return main;
        }
    }

    public class Main{
        private String cities;

        public String getCities() {
            return cities;
        }
    }
}

