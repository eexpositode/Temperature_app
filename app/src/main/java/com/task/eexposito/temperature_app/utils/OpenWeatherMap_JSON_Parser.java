package com.task.eexposito.temperature_app.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by EExposito on 07/02/2015.
 */
public class OpenWeatherMap_JSON_Parser {

    private String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String CTE_MAIN_ARR = "main";
    private String CTE_TEMP = "temp";

    public String getJSONFromUrl(String location) {

        // Parse String to JSON object
        try {
            JSONObject jobject = new JSONObject(getWeatherData(location));
            JSONObject main = jobject.getJSONObject(CTE_MAIN_ARR);
            return main.getString(CTE_TEMP);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return "";

    }

    private String getWeatherData(String location) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) (new URL(BASE_URL + location)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");
            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }
}
