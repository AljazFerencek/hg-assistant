package com.hivegarden.assistant.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    String BaseURL = "http://api.openweathermap.org/data/2.5/weather?";
    String ImgURL = "http://openweathermap.org/img/w/";

    public JSONObject getWeatherData(String location) {
       HttpURLConnection con = null ;
       InputStream is = null;
       JSONObject jObj;
       try {
           con = (HttpURLConnection) ( new URL(BaseURL + location)).openConnection();
           con.setRequestMethod("GET");
           con.setDoInput(true);
           con.setDoOutput(true);
           con.connect();
           StringBuilder buffer = new StringBuilder();
           is = con.getInputStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String line = null;
           while ( (line = br.readLine()) != null )
               buffer.append(line + "\r\n");
           is.close();
           con.disconnect();
           jObj = new JSONObject(buffer.toString());
           return jObj;
       }
       catch(Throwable t) {
           t.printStackTrace();
       }
       return null;
    }

    public Bitmap getImage(String code) {
       InputStream in = null;
       Bitmap img = null;
       try {
           in = new java.net.URL(ImgURL + code + ".png").openStream();
           img = BitmapFactory.decodeStream(in);
           return img;
       }
       catch(Throwable t) {
           t.printStackTrace();
       }
       return null;
    }

}
