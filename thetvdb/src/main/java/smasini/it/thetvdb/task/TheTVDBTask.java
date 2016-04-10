package smasini.it.thetvdb.task;

import android.os.AsyncTask;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 03/02/2016 at 19.46.
 */
public abstract class TheTVDBTask extends AsyncTask<Void, Void, Void> {

    protected String url = "";

    public TheTVDBTask(String mirrorPath, String apikey){
        this.url = String.format("%s/api/%s/languages.xml", mirrorPath, apikey);
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }


}
