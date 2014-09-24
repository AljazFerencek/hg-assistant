package com.hivegarden.assistant;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class AddNewPlant extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_plant, menu);

        // Get tracker.
        Tracker t = ((ApplicationGlobalState) getApplication()).getTracker(
                ApplicationGlobalState.TrackerName.APP_TRACKER);

        //String path = getComponentName().getClassName();
        // Set screen name.
        // Where path is a String representing the screen name.
        //t.setScreenName(path);
        t.setAppId("12efgh45");
        t.enableAutoActivityTracking(true);
        t.setAppName("krneki");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());

        return true;
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_new_plant, container, false);
            return rootView;
        }

        //TODO Zdefiniraj elemente v spinnerju (najbolj pogoste rastline)
    }
}
