package com.hivegarden.assistant.helpers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private static String BaseURL = "http://api.openweathermap.org/data/2.5/weather?";
    private static String ImgURL = "http://openweathermap.org/img/w/";

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
           StringBuffer buffer = new StringBuffer();
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
       finally {
           try { is.close(); } catch(Throwable t) {}
           try { con.disconnect(); } catch(Throwable t) {}
       }
       return null;
    }

    public byte[] getImage(String code) {
       HttpURLConnection con = null ;
       InputStream is = null;
       try {
           con = (HttpURLConnection) ( new URL(ImgURL + code)).openConnection();
           con.setRequestMethod("GET");
           con.setDoInput(true);
           con.setDoOutput(true);
           con.connect();
           is = con.getInputStream();
           byte[] buffer = new byte[1024];
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           while ( is.read(buffer) != -1)
               baos.write(buffer);
           return baos.toByteArray();
       }
       catch(Throwable t) {
           t.printStackTrace();
       }
       finally {
           try { is.close(); } catch(Throwable t) {}
           try { con.disconnect(); } catch(Throwable t) {}
       }
       return null;
    }

}
