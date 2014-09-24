package com.hivegarden.assistant.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public JSONObject getWeatherData(String BaseURL) {
       HttpURLConnection con;
       InputStream is;
       JSONObject jObj;
       try {
           con = (HttpURLConnection) ( new URL(BaseURL)).openConnection();
           con.setRequestMethod("GET");
           con.setDoInput(true);
           con.setDoOutput(true);
           con.connect();
           StringBuilder buffer = new StringBuilder();
           is = con.getInputStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String line;
           while ( (line = br.readLine()) != null )
               buffer.append(line).append("\r\n");
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

    public Bitmap getImage(String ImgURL) {
       InputStream in;
       Bitmap img;
       try {
           in = new java.net.URL(ImgURL).openStream();
           img = BitmapFactory.decodeStream(in);
           return img;
       }
       catch(Throwable t) {
           t.printStackTrace();
       }
       return null;
    }
}
