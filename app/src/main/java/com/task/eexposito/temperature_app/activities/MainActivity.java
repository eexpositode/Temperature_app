package com.task.eexposito.temperature_app.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.task.eexposito.temperature_app.utils.OpenWeatherMap_JSON_Parser;
import com.task.eexposito.temperature_app.R;

/**
 * <b>TASK DESCRIPTION:</b>
 * <br>Please write an app activity with more detail. This activity will show the current temperature in Berlin, Paris and Los Angeles. Please uploade this code on Github
 * <br>Please send us the github link and the app as an attachment.
 * <br><br>In order to solve the task, I am using JSON to parse the answer from OpenWeatherMap website.
 */
public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String TMP_STATE = TAG + ".TMP_STATE";

    TextView berlin_weather, paris_weather, la_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        berlin_weather = (TextView) findViewById(R.id.txt_berlin_weather);
        paris_weather = (TextView) findViewById(R.id.txt_paris_weather);
        la_weather = (TextView) findViewById(R.id.txt_los_angeles_weather);
        if (savedInstanceState == null)
            actualize_Temperatures();
        else
            restorePreviousState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_reload:
                actualize_Temperatures();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(TMP_STATE, new String[]{berlin_weather.getText().toString(),
                paris_weather.getText().toString(), la_weather.getText().toString()});
    }

    private void restorePreviousState(Bundle inState) {
        String[] temperatures = inState.getStringArray(TMP_STATE);
        if (temperatures != null) {
            berlin_weather.setText(temperatures[0]);
            paris_weather.setText(temperatures[1]);
            la_weather.setText(temperatures[2]);
        }
    }

    private void actualize_Temperatures() {
        if (checkNetworkConn()) {
            new AsyncGetWeather(getString(R.string.berlin_des), berlin_weather).execute();
            new AsyncGetWeather(getString(R.string.paris_des), paris_weather).execute();
            new AsyncGetWeather(getString(R.string.la_des), la_weather).execute();
        } else {
            berlin_weather.setText(getString(R.string.no_conn));
            paris_weather.setText(getString(R.string.no_conn));
            la_weather.setText(getString(R.string.no_conn));
        }
    }

    private boolean checkNetworkConn() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    class AsyncGetWeather extends AsyncTask<Void, Integer, String> {

        private String _location_str;
        private TextView _display;

        public AsyncGetWeather(String location_str, TextView display) {
            _location_str = location_str;
            _display = display;
        }

        @Override
        protected void onPreExecute() {
            _display.setText(getString(R.string.loading_str));
        }

        @Override
        protected String doInBackground(Void... params) {
            String milli_temp = new OpenWeatherMap_JSON_Parser().getJSONFromUrl(_location_str);
            return convertTemp(milli_temp);
        }

        /**
         * Temperature is given in Kelvin
         * <br>Formula for converting ºC = [°K] - 273.15
         *
         * @param temp
         * @return
         */
        private String convertTemp(String temp) {
            float kelvin = Float.valueOf(temp);
            return String.format("%.2f", kelvin - 273.15) + "ºC";
        }

        @Override
        protected void onPostExecute(String temp) {
            _display.setText(temp);
        }
    }
}
