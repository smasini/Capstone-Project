package smasini.it.thetvdb.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import smasini.it.thetvdb.task.callbacks.CallbackZip;
import smasini.it.thetvdb.utils.DownloadManager;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.task
 * Created by Simone Masini on 04/02/2016 at 23.13.
 */
public class TaskDownloadZip extends AsyncTask<Void, Void, Void> {

    private String zipPath;
    private String destinationPath;
    private CallbackZip callback;

    public TaskDownloadZip(String zipPath, String destinationPath){
        this.zipPath = zipPath;
        this.destinationPath = destinationPath;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(zipPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/zip");
            connection.connect();
            Integer statusCode = connection.getResponseCode();
            if(statusCode == 200){
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputStream));
                ZipEntry ze;
                byte[] buffer = new byte[1024];
                int count;
                String filename;
                //String path = FoodStoriesApplication.getImagePath();
                while ((ze = zis.getNextEntry()) != null)
                {
                    filename = ze.getName();
                    int i = filename.indexOf("/");
                    if(i>=0){
                        DownloadManager.makeDirectory(String.format("%s/%s/", destinationPath, filename.substring(0,i)));
                    }
                    FileOutputStream fout = new FileOutputStream(destinationPath + filename);
                    while ((count = zis.read(buffer)) != -1)
                    {
                        fout.write(buffer, 0, count);
                    }
                    fout.close();
                    zis.closeEntry();
                }
                zis.close();
            }
        } catch (Exception e) {
            Log.e("ERRORE", e.getMessage());
        }
        return null;
    }

    public void setCallback(CallbackZip callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(callback!=null){
            callback.doAfterTask(destinationPath);
        }
    }
}
