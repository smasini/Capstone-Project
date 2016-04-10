package smasini.it.thetvdb;

import android.content.Context;

import java.util.List;

import smasini.it.thetvdb.support.Actor;
import smasini.it.thetvdb.support.Banner;
import smasini.it.thetvdb.support.Serie;
import smasini.it.thetvdb.task.TaskActor;
import smasini.it.thetvdb.task.TaskBanner;
import smasini.it.thetvdb.task.TaskDownloadZip;
import smasini.it.thetvdb.task.TaskLanguage;
import smasini.it.thetvdb.task.TaskMirror;
import smasini.it.thetvdb.task.TaskSerie;
import smasini.it.thetvdb.task.TaskServerTime;
import smasini.it.thetvdb.task.callbacks.CallbackActor;
import smasini.it.thetvdb.task.callbacks.CallbackBanner;
import smasini.it.thetvdb.task.callbacks.CallbackLanguage;
import smasini.it.thetvdb.task.callbacks.CallbackMirror;
import smasini.it.thetvdb.task.callbacks.CallbackSerie;
import smasini.it.thetvdb.task.callbacks.CallbackSerieData;
import smasini.it.thetvdb.task.callbacks.CallbackTask;
import smasini.it.thetvdb.task.callbacks.CallbackZip;
import smasini.it.thetvdb.utils.DownloadManager;
import smasini.it.thetvdb.utils.EncodeString;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb
 * Created by Simone Masini on 03/02/2016 at 19.41.
 */
public class TheTVDB {
    public static final String baseUrlImage = "http://thetvdb.com/banners/";
    private String apiKey;
    private String mirrorPath = "http://thetvdb.com";
    private String language;
    private Context context;
    private CallbackTask callbackTask;

    public TheTVDB(Context context, String apiKey){
        this(context, apiKey, "en");
    }

    public TheTVDB(Context context, String apiKey, String language){
        this.apiKey = apiKey;
        this.language = language;
        this.context = context;
    }

    public void setCallbackTask(CallbackTask callbackTask) {
        this.callbackTask = callbackTask;
    }

    public void getMirrors(CallbackMirror callbackMirror){
        TaskMirror taskMirror = new TaskMirror(apiKey, callbackMirror);
        taskMirror.execute();
    }

    public void getLanguages(CallbackLanguage callbackLanguage){
        TaskLanguage taskLanguage = new TaskLanguage(mirrorPath, apiKey, callbackLanguage);
        taskLanguage.execute();
    }

    public void findSeriesByName(String serieName, CallbackSerie callbackSerie){
        serieName = EncodeString.encodeString(serieName);
        TaskSerie taskSerie = new TaskSerie(mirrorPath, serieName, language);
        taskSerie.setCallback(callbackSerie);
        taskSerie.execute();
    }

    private void saveServerTime(){
        TaskServerTime taskServerTime = new TaskServerTime(context);
        taskServerTime.execute();
    }

    private void downloadZip(String serieID, String rootPathDirectory, CallbackZip callbackZip){
        String zipPath = String.format("%s/api/%s/series/%s/all/%s.zip", mirrorPath, apiKey, serieID, language);
        String destPath = String.format("%s/images", rootPathDirectory);
        DownloadManager.makeDirectory(destPath);
        destPath = String.format("%s/images/%s/", rootPathDirectory, serieID);
        DownloadManager.makeDirectory(destPath);

        TaskDownloadZip taskDownloadZip = new TaskDownloadZip(zipPath, destPath);
        taskDownloadZip.setCallback(callbackZip);
        taskDownloadZip.execute();
    }

    public void getSerieData(String serieID, String rootPathDirectory, final CallbackSerieData callback){
        downloadZip(serieID, rootPathDirectory, new CallbackZip() {
            @Override
            public void doAfterTask(final String destinatioPath) {
                TaskSerie taskSerie = new TaskSerie(destinatioPath, language);
                taskSerie.setCallback(new CallbackSerie() {
                    @Override
                    public void doAfterTask(List<Serie> series) {
                        if(series.size()>0){
                            final Serie serie = series.get(0);
                            TaskActor taskActor = new TaskActor(destinatioPath, new CallbackActor() {
                                @Override
                                public void doAfterTask(final List<Actor> actors) {
                                    TaskBanner taskBanner = new TaskBanner(destinatioPath, new CallbackBanner() {
                                        @Override
                                        public void doAfterTask(List<Banner> banners) {
                                            DownloadManager.delete(destinatioPath + language + ".xml");
                                            DownloadManager.delete(destinatioPath + "banners.xml");
                                            DownloadManager.delete(destinatioPath + "actors.xml");

                                            callback.onSerieDataLoad(serie, actors, banners);
                                        }
                                    });
                                    taskBanner.execute();
                                }
                            });
                            taskActor.execute();
                        }
                    }
                });
                taskSerie.execute();
            }
        });
    }

}
