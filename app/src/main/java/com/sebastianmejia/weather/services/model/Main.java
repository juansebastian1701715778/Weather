package com.sebastianmejia.weather.services.model;

public class Main {
    private float temp;
    private float tempMin;
    private float tempMax;
    private int pressure;
    private int humidity;

    public float getTemp() { return temp; }

    public void setTemp(float temp) { this.temp = temp; }

    public float getTempMin() { return tempMin; }

    public void setTempMin(float tempMin) { this.tempMin = tempMin; }

    public float getTempMax() { return tempMax; }

    public void setTempMax(float tempMax) { this.tempMax = tempMax; }

    public int getPressure(){return pressure;}

    public void setPressure(int pressure){this.pressure = pressure; }

    public int getHumidity(){return humidity; }

    public void setHumidity(int humidity){this.humidity = humidity; }
}
