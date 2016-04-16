package smasini.it.traxer.utils;

import android.content.Context;
import android.os.Environment;

import smasini.it.thetvdb.utils.DownloadManager;

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
    }

    public static Context getStaticApplicationContext() {
        return applicationContext;
    }

}
