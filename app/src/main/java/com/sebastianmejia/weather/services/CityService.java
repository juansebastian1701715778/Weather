package com.sebastianmejia.weather.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sebastianmejia.weather.services.model.Root;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

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
            int statusCode = -1;
            Cities cities = null;

            if (error == null && resp.getStatusCode() == 200) {
                String text = data.toText();
                cities = new Cities(text);
                cities.convertIntoList();
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
        private String citiesString;
        private List<String> citiesList;

        public Cities(String citiesString) {
            this.citiesString = citiesString;
        }

        public void convertIntoList(){
            citiesString = citiesString.replace("[", "");
            citiesString = citiesString.replace("]", "");
            citiesString = citiesString.replace("\"", "");
            citiesList = Arrays.asList(citiesString.split(","));
        }

        public List<String> getCitiesList() {
            return citiesList;
        }
    }
}

