package com.hivegarden.assistant;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hivegarden.assistant.helpers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class main extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public String weather = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if app is being run for the first time
        SharedPreferences firstRun = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        Log.d("FirstRun", String.valueOf(firstRun));

        if (firstRun.getBoolean("firstrun", true)) {
            Log.d("FirstRun", "First run activated");
            Toast.makeText(this, "Aplikacija je zagnana prvič", Toast.LENGTH_LONG).show();
            firstRun.edit().putBoolean("firstrun", false).commit();
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 1:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case 2:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            default:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.app_name);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((main) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    public void changeWeatherPlaceholder(View view) throws JSONException {

        new AsyncTaskParseJson().execute();

        /*String data = (new HttpClient()).getWeatherData("lat=46.0173164&lon=14.5107803");
        JSONObject jObj = new JSONObject(data);
        JSONArray jArr = jObj.getJSONArray("weather");
        JSONObject mainObj = jObj.getJSONObject("main");
        JSONObject JSONWeather = jArr.getJSONObject(0);

        String description = JSONWeather.getString("description");
        String icon = getString("icon", JSONWeather);
        Integer humidity = getInt("humidity", mainObj);
        Integer pressure = getInt("pressure", mainObj);
        Float temperature = getFloat("temp", mainObj);
*/
        Handler handler = new Handler();
        handler.postDelayed(new UpdateTextView(), 3000);
    }

    private class UpdateTextView implements Runnable{
        public UpdateTextView() {
        }
        @Override
        public void run() {
            TextView t = (TextView) findViewById(R.id.textViewWeatherPlaceholder);
            t.setText(weather);
        }
    }

    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {}

        protected String doInBackground(String... arg0) {
             try {

                HttpClient jParser = new HttpClient();
                JSONObject json = jParser.getWeatherData("lat=46.0173164&lon=14.5107803");
                JSONArray jArr = json.getJSONArray("weather");
                JSONObject mainObj = json.getJSONObject("main");
                JSONObject JSONWeather = jArr.getJSONObject(0);
                String description = JSONWeather.getString("description");
                String icon = JSONWeather.getString("icon");
                Integer humidity = mainObj.getInt("humidity");
                Integer pressure = mainObj.getInt("pressure");
                Double temperature = mainObj.getDouble("temp");
                temperature = temperature - 273.15;
                Integer temp = temperature.intValue();
                Log.d("Main:", description + " Temperature: " + temp);
                weather = "Weather: " + description + " Temperature: " + temp + "°C";

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String strFromDoInBg) {}
    }
}