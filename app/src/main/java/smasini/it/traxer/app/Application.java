package smasini.it.traxer.app;

import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;

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

}
