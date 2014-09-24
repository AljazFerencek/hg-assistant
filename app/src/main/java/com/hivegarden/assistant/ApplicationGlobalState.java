package com.hivegarden.assistant;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Blaž on 12.9.2014.
 *
 * This extention of Activity class is used primarily to instantiate Google Analytics
 * and to save global variables (i.e. needed in more than one Activity)
 */
public class ApplicationGlobalState extends Application {
    //private static final String PROPERTY_ID = "UA-54759590-1";
    public enum TrackerName {
        APP_TRACKER // Tracker used only in this app.
        //GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        //ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public ApplicationGlobalState() {
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        Log.d("GA", "TrackerId: " + trackerId.toString());
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            Tracker t =  analytics.newTracker(R.xml.analythics_tracker);
            t.enableAdvertisingIdCollection(true);
            t.enableAutoActivityTracking(true);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

}
