package com.hivegarden.assistant.helpers;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WateringAlgorithm extends AsyncTask<String, String, String> {
    double temperature, wind, humidity, cloud, rain;
    int count = 0;

    @Override
    protected void onPreExecute() {}

    protected String doInBackground(String... arg0) {
        try {
               temperature = 0.0;
               wind = 0.0;
               humidity = 0.0;
               cloud = 0.0;
               rain = 0.0;
               count = 0;
               HttpClient jParser = new HttpClient();
               JSONObject json = jParser.getWeatherData("http://api.openweathermap.org/data/2.5/history/city?lat=46.0173164&lon=14.5107803");
               JSONArray jArr = json.getJSONArray("list");
               for(int i=0;i<jArr.length();i++) {
                   JSONObject mainObj = json.getJSONObject("main");
                   Double temp = mainObj.getDouble("temp");
                   if (temp != null) {
                       temp = temp - 273.15;
                       temperature += temp;
                   }
                   Double humid = mainObj.getDouble("humidity");
                   if (humid != null) {
                       humidity += humid;
                   }

                   JSONObject windObj = json.getJSONObject("wind");
                   Double windSpeed = windObj.getDouble("speed");
                   if (windSpeed != null) {
                       wind += windSpeed;
                   }

                   JSONObject cloudsObj = json.getJSONObject("clouds");
                   Double cloudAll = cloudsObj.getDouble("all");
                   if (cloudAll != null) {
                       cloud += cloudAll;
                   }

                   JSONObject rainObj = json.getJSONObject("rain");
                   Double rainMM = rainObj.getDouble("1h");
                   if (rainMM != null) {
                       rain += rainMM;
                   }

                   count++;
               }
        } catch (JSONException e) {
               e.printStackTrace();
        }

        temperature /= count;
        double percents = 0;
        int temp = 5;
        for(int i=0; i < 9; i++){
            if (temperature < temp){
                temperature = percents/100;
                break;
            }
            temp +=5;
            percents +=3;
        }

        wind /= count;
        if (wind < 1){
            wind = 0.2;
        }else if (wind < 3.5){
            wind = 0.5;
        }else if (wind < 8){
            wind = 0.8;
        }

        humidity /= count;
        cloud /= count;
        rain /= count;
        //TODO rain pretvorit v L/1 foot row

        //TODO Formula za izračun dodajanja vode
        // double decrease = 0.1*(1 - humidity)*wind;
        // double other =
        // double watering = dailyWaterNeed+(dailyWaterNeed*other)-rain;
        // Log.d("WateringAlgorithm", watering.intValue());
        return null;
    }
    @Override
    protected void onPostExecute(String strFromDoInBg) {}

}
