package smasini.it.thetvdb.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.w3c.dom.Document;

import smasini.it.thetvdb.R;
import smasini.it.thetvdb.support.XMLHelper;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 03/02/2016 at 22.17.
 */
public class TaskServerTime extends AsyncTask<Void, Void, String> {

    private Context context;

    public TaskServerTime(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Document document = XMLHelper.openXML("http://thetvdb.com/api/Updates.php?type=none", true);
        String s = document.getElementsByTagName("Time").item(0).getFirstChild().getNodeValue();
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putString(context.getString(R.string.server_time_thetvdb_prefs_key), s)
        .apply();
    }
}
