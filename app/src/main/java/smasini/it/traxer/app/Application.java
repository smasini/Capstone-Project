package smasini.it.traxer.app;

import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.utils.DownloadManager;
import smasini.it.traxer.R;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.utils
 * Created by Simone Masini on 07/02/2016 at 11.43.
 */
public class Application extends android.app.Application {

    private static Context applicationContext;
    public static String sdDirectory;
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this.getApplicationContext();
        sdDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Traxer";
        boolean ok = DownloadManager.makeDirectory(sdDirectory);
        //add .nomedia file
        if(ok){
            DownloadManager.nomedia(sdDirectory);
        }
        String language = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(getString(R.string.language_key), getString(R.string.default_language));
        TheTVDB.init(applicationContext, applicationContext.getString(R.string.the_tvdb_api_key), language);
    }

    public static Context getStaticApplicationContext() {
        return applicationContext;
    }


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
