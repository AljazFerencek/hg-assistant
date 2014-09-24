package com.hivegarden.assistant.helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WateringAlgorithm extends AsyncTask<String, String, String> {
    double temperature, wind, humidity, cloud, rain = 0;
    double dailyWaterNeed = 200;
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
                   JSONObject jAll = jArr.getJSONObject(i);
                   JSONObject mainObj = jAll.getJSONObject("main");
                   Double temp = mainObj.getDouble("temp");
                   if (temp != null) {
                       Log.d("WateringAlgorithm", "Temperatura: " + String.valueOf(temp));
                       temp = temp - 273.15;
                       temperature += temp;
                   }
                   Double humid = mainObj.getDouble("humidity");
                   if (humid != null) {
                       humidity += humid;
                   }

                   JSONObject windObj = jAll.getJSONObject("wind");
                   Double windSpeed = windObj.getDouble("speed");
                   if (windSpeed != null) {
                       wind += windSpeed;
                   }

                   JSONObject cloudsObj = jAll.getJSONObject("clouds");
                   Double cloudAll = cloudsObj.getDouble("all");
                   if (cloudAll != null) {
                       cloud += cloudAll;
                   }

                   /*JSONObject rainObj = jAll.getJSONObject("rain");
                   if (rainObj != null) {
                       Double rainMM = rainObj.getDouble("1h");
                       rain += rainMM;
                   }*/

                   count++;
               }
        } catch (JSONException e) {
               e.printStackTrace();
        }

        temperature /= count;
        Log.d("WateringAlgorithm", String.valueOf(temperature));
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
        Log.d("WateringAlgorithm", String.valueOf(wind));
        if (wind < 1){
            wind = 0.2;
        }else if (wind < 3.5){
            wind = 0.5;
        }else if (wind < 8){
            wind = 0.8;
        }

        humidity /= count*100;
        Log.d("WateringAlgorithm", String.valueOf(humidity));
        cloud /= count*100;
        Log.d("WateringAlgorithm", String.valueOf(cloud));
        Log.d("WateringAlgorithm", String.valueOf(rain));
        //TODO rain pretvorit v L/1 foot row

        //TODO Formula za izračun dodajanja vode
        double influenceWindHumidity = 0.1*(1 - humidity)*wind;
        double other = temperature * 1 - influenceWindHumidity;
        double watering = dailyWaterNeed+(dailyWaterNeed*other)-rain;
        Log.d("WateringAlgorithm", String.valueOf(watering));
        return null;
    }
    @Override
    protected void onPostExecute(String strFromDoInBg) {}

}
