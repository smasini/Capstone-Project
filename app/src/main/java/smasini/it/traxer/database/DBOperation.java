package smasini.it.traxer.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import smasini.it.thetvdb.support.Actor;
import smasini.it.thetvdb.support.Banner;
import smasini.it.thetvdb.support.Episode;
import smasini.it.thetvdb.support.Serie;
import smasini.it.traxer.database.contract.ActorContract;
import smasini.it.traxer.database.contract.BannerContract;
import smasini.it.traxer.database.contract.CastContract;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.database.contract.SerieContract;
import smasini.it.traxer.utils.Application;
import smasini.it.traxer.utils.Utility;
import smasini.it.traxer.viewmodels.ActorDetailViewModel;
import smasini.it.traxer.viewmodels.CastViewModel;
import smasini.it.traxer.viewmodels.DetailSerieViewModel;
import smasini.it.traxer.viewmodels.EpisodeDetailViewModel;
import smasini.it.traxer.viewmodels.EpisodeItemViewModel;
import smasini.it.traxer.viewmodels.InfoSerieViewModel;
import smasini.it.traxer.viewmodels.SeasonViewModel;
import smasini.it.traxer.viewmodels.SerieCollectionViewModel;
import smasini.it.traxer.viewmodels.StatisticViewModel;
import smasini.it.traxer.viewmodels.TimeViewModel;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.database
 * Created by Simone Masini on 13/02/2016 at 16.12.
 */
public class DBOperation {


    public static void insertSerie(Serie serie){
        ContentValues cv = new ContentValues();

        cv.put(SerieContract.COL_ID, serie.getId());
        cv.put(SerieContract.COL_AIRSDAYWEEK, serie.getAirs_DayOfWeek());
        cv.put(SerieContract.COL_AIRSTIME, serie.getAirs_Time());
        cv.put(SerieContract.COL_BANNER, serie.getBanner());
        cv.put(SerieContract.COL_FIRSTAIRED, serie.getFirstAired());
        cv.put(SerieContract.COL_GENRE, serie.getGenre());
        cv.put(SerieContract.COL_IMDBID, serie.getImdb_ID());
        cv.put(SerieContract.COL_LANG, serie.getLanguage());
        cv.put(SerieContract.COL_NAME, serie.getSeriesName());
        cv.put(SerieContract.COL_NETWORK, serie.getNetwork());
        cv.put(SerieContract.COL_OVERVIEW, serie.getOverview());
        cv.put(SerieContract.COL_POSTER, serie.getPoster());
        cv.put(SerieContract.COL_RATE, serie.getRate());
        cv.put(SerieContract.COL_RATING, serie.getRating());
        cv.put(SerieContract.COL_RUNTIME, serie.getRuntime());
        cv.put(SerieContract.COL_STATUS, serie.getStatus());
        cv.put(SerieContract.COL_ZAP2ITID, serie.getZap2it_id());

        Application.getStaticApplicationContext().getContentResolver().insert(SerieContract.CONTENT_URI, cv);
    }

    public static void insertEpisoes(List<Episode> episodes){
        List<ContentValues> cvs = new ArrayList<>();
        for (Episode episode : episodes)
        {
            ContentValues cv = new ContentValues();

            cv.put(EpisodeContract.COL_ID, episode.getId());
            cv.put(EpisodeContract.COL_SERIE_ID, episode.getSeriesid());
            cv.put(EpisodeContract.COL_NAME, episode.getEpisodeName());
            cv.put(EpisodeContract.COL_NUMBER, episode.getEpisodeNumberInt());
            cv.put(EpisodeContract.COL_FIRST_AIRED, episode.getFirstAired());
            cv.put(EpisodeContract.COL_OVERVIEW, episode.getOverview());
            cv.put(EpisodeContract.COL_SEASON_NUMBER, episode.getSeasonNumberInt());
            cv.put(EpisodeContract.COL_FILENAME, episode.getFilename());
            cv.put(EpisodeContract.COL_RATING, episode.getRating());
            cv.put(EpisodeContract.COL_RATE, episode.getRate());
            cv.put(EpisodeContract.COL_WATCH, episode.isWatch() ? 1 : 0);

            cvs.add(cv);
        }
        Application.getStaticApplicationContext().getContentResolver().bulkInsert(EpisodeContract.CONTENT_URI, cvs.toArray(new ContentValues[cvs.size()]));
    }

    public static void insertActors(List<Actor> actors, String serieID){
        List<ContentValues> cvActors = new ArrayList<>();
        List<ContentValues> cvCasts = new ArrayList<>();
        for(Actor actor : actors){
            ContentValues cv = new ContentValues();

            cv.put(ActorContract.COL_ID, actor.getIdActor());
            cv.put(ActorContract.COL_IMAGE, actor.getImage());
            cv.put(ActorContract.COL_NAME, actor.getName());
            cv.put(ActorContract.COL_SORT_ORDER, actor.getSortOrder());
            cv.put(ActorContract.COL_WIKI_BIO, actor.getWikiBio());

            cvActors.add(cv);

            ContentValues cv2 = new ContentValues();

            cv2.put(CastContract.COL_ACTOR_ID, actor.getIdActor());
            cv2.put(CastContract.COL_SERIEID, serieID);
            cv2.put(CastContract.COL_ROLE, actor.getRole());

            cvCasts.add(cv2);
        }
        Application.getStaticApplicationContext().getContentResolver().bulkInsert(ActorContract.CONTENT_URI, cvActors.toArray(new ContentValues[cvActors.size()]));
        Application.getStaticApplicationContext().getContentResolver().bulkInsert(CastContract.CONTENT_URI, cvCasts.toArray(new ContentValues[cvCasts.size()]));
    }

    public static void insertBanners(List<Banner> banners, String serieid){
        List<ContentValues> cvs = new ArrayList<>();
        for (Banner banner : banners)
        {
            ContentValues cv = new ContentValues();

            cv.put(BannerContract.COL_ID, banner.getId());
            cv.put(BannerContract.COL_SERIE_ID, serieid);
            cv.put(BannerContract.COL_TYPE, banner.getBannerType());
            cv.put(BannerContract.COL_TYPE_2, banner.getBannerType2());
            cv.put(BannerContract.COL_LANGUAGE, banner.getLanguage());
            cv.put(BannerContract.COL_URL, banner.getBannerPath());
            cv.put(BannerContract.COL_RATING, banner.getRaiting());
            cv.put(BannerContract.COL_SEASON, banner.getSeason());

            cvs.add(cv);
        }
        Application.getStaticApplicationContext().getContentResolver().bulkInsert(BannerContract.CONTENT_URI, cvs.toArray(new ContentValues[cvs.size()]));
    }

    public static List<SerieCollectionViewModel> getSerieViewModel(Cursor cursor){
        List<SerieCollectionViewModel> series = new ArrayList<>();
        while(cursor.moveToNext()){
            SerieCollectionViewModel vm = new SerieCollectionViewModel();
            vm.setId(cursor.getString(cursor.getColumnIndex(SerieContract.COL_ID)));
            vm.setName(cursor.getString(cursor.getColumnIndex(SerieContract.COL_NAME)));
            vm.setImageUrl(cursor.getString(cursor.getColumnIndex(SerieContract.COL_POSTER)));
            series.add(vm);
        }
        cursor.close();
        return series;
    }

    public static boolean existSerieWithId(String id){
        Uri uri = SerieContract.buildSerieUri(id);
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(uri, SerieContract.COLUMNS, null, null, null);
        boolean ret = false;
        if(cursor != null && cursor.moveToFirst()){
            ret = true;
            cursor.close();
        }
        return ret;
    }

    private static int getCount(Uri uri){
        int ret = 0;
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){
            ret = cursor.getInt(cursor.getColumnIndex("count"));
            cursor.close();
        }
        return ret;
    }

    public static StatisticViewModel getStatistic(){
        StatisticViewModel svm = new StatisticViewModel();

        Cursor c = Application.getStaticApplicationContext().getContentResolver().query(EpisodeContract.buildTimeUri(), null, null, null, null);
        if(c!=null && c.moveToFirst()){
            int time = c.getInt(c.getColumnIndex("sum"));
            TimeViewModel tvm = Utility.formatTime(time);
            svm.setDays(tvm.getDays());
            svm.setHours(tvm.getHours());
            svm.setMinutes(tvm.getMinutes());
        }

        svm.setTotalSerie(getCount(SerieContract.buildCountUri()));
        svm.setTotalEpisode(getCount(EpisodeContract.buildCountUri(false)));
        svm.setWatch(getCount(EpisodeContract.buildCountUri(true)));
        svm.setUnwatch(svm.getTotalEpisode() - svm.getWatch());

        return svm;
    }

    public static DetailSerieViewModel getSerieDetails(String id){
        Uri uri = SerieContract.buildSerieUri(id);
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(uri, SerieContract.COLUMNS, null, null, null);
        DetailSerieViewModel dsvm = new DetailSerieViewModel();
        if(cursor != null && cursor.moveToFirst()){
            dsvm.setSerieName(cursor.getString(cursor.getColumnIndex(SerieContract.COL_NAME)));
            dsvm.setBannerUrl(cursor.getString(cursor.getColumnIndex(SerieContract.COL_BANNER)));
            cursor.close();
        }
        return dsvm;
    }

    public static List<SeasonViewModel> getSeasonViewModel(Cursor cursor){
        List<SeasonViewModel> seasons = new ArrayList<>();
        while(cursor.moveToNext()){
            SeasonViewModel svm = new SeasonViewModel();
            int seasonNumber = cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_SEASON_NUMBER));
            svm.setNumber(seasonNumber);
            String urlLocalized = cursor.getString(cursor.getColumnIndex(BannerContract.COL_URL + "_" + Utility.getLanguage()));
            if(urlLocalized != null && !urlLocalized.equals(""))
                    svm.setImageUrl(urlLocalized);
            else
                svm.setImageUrl(cursor.getString(cursor.getColumnIndex(BannerContract.COL_URL + "_" + Utility.DEFAULT_LANGUAGE)));
            seasons.add(svm);
        }
        cursor.close();
        if(seasons.size()> 0 && seasons.get(0).getNumber() == 0){
            SeasonViewModel tmp = seasons.remove(0);
            seasons.add(tmp);
        }
        return seasons;
    }

    public static List<CastViewModel> getCastViewModel(Cursor cursor){
        List<CastViewModel> cast = new ArrayList<>();
        while(cursor.moveToNext()){
            CastViewModel cvm = new CastViewModel();
            cvm.setRole(cursor.getString(cursor.getColumnIndex(CastContract.COL_ROLE)));
            cvm.setName(cursor.getString(cursor.getColumnIndex(ActorContract.COL_NAME)));
            cvm.setImageUrl(cursor.getString(cursor.getColumnIndex(ActorContract.COL_IMAGE)));
            cvm.setId(cursor.getString(cursor.getColumnIndex(CastContract.COL_ACTOR_ID)));
            cast.add(cvm);
        }
        cursor.close();
        return cast;
    }

    public static InfoSerieViewModel getInfoSerie(String id){
        Uri uri = SerieContract.buildSerieUri(id);
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(uri, SerieContract.COLUMNS, null, null, null);
        InfoSerieViewModel isvm = new InfoSerieViewModel();
        if(cursor != null && cursor.moveToFirst()){
            isvm.setOverview(cursor.getString(cursor.getColumnIndex(SerieContract.COL_OVERVIEW)));
            isvm.setRate(cursor.getDouble(cursor.getColumnIndex(SerieContract.COL_RATING)));
            isvm.setStatus(cursor.getString(cursor.getColumnIndex(SerieContract.COL_STATUS)));
            isvm.setFirstAired(cursor.getString(cursor.getColumnIndex(SerieContract.COL_FIRSTAIRED)));
            isvm.setGenre(cursor.getString(cursor.getColumnIndex(SerieContract.COL_GENRE)));
            isvm.setNetwork(cursor.getString(cursor.getColumnIndex(SerieContract.COL_NETWORK)));
            isvm.setTime(cursor.getString(cursor.getColumnIndex(SerieContract.COL_RUNTIME)));
            isvm.setSeasons(5);
            isvm.setEpisodes(100);
            cursor.close();
        }
        return isvm;
    }

    public static ActorDetailViewModel getActorDetailViewModel(String idActor){
        Uri uri = ActorContract.buildActorUri(idActor);
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(uri, ActorContract.COLUMNS, null, null, null);
        ActorDetailViewModel advm = new ActorDetailViewModel();
        if(cursor !=null && cursor.moveToFirst()) {
            advm.setName(cursor.getString(cursor.getColumnIndex(ActorContract.COL_NAME)));
            advm.setBioHTML(cursor.getString(cursor.getColumnIndex(ActorContract.COL_WIKI_BIO)));
            advm.setUrlImage(cursor.getString(cursor.getColumnIndex(ActorContract.COL_IMAGE)));
            advm.setRole(cursor.getString(cursor.getColumnIndex(CastContract.COL_ROLE)));
            cursor.close();
        }
        return advm;
    }

    public static List<EpisodeItemViewModel> getEpisodes(Cursor cursor, boolean season){
        List<EpisodeItemViewModel> episodes = new ArrayList<>();
        if(cursor!=null){
            while (cursor.moveToNext()){
                EpisodeItemViewModel eivm = new EpisodeItemViewModel();
                eivm.setId(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_ID)));
                eivm.setSerieName(cursor.getString(cursor.getColumnIndex(SerieContract.COL_NAME)));
                eivm.setName(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_NAME)));
                eivm.setDate(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_FIRST_AIRED)));
                if(season){
                    eivm.setImageUrl(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_FILENAME)));
                }else {
                    eivm.setImageUrl(cursor.getString(cursor.getColumnIndex(SerieContract.COL_POSTER)));
                }
                eivm.setNumber(cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_NUMBER)));
                eivm.setSeasonNumber(cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_SEASON_NUMBER)));
                episodes.add(eivm);
            }
            cursor.close();
        }
        return episodes;
    }

    public static EpisodeDetailViewModel getDetailEpisodes(String episodeid){
        EpisodeDetailViewModel edvm = new EpisodeDetailViewModel();
        Cursor cursor = Application.getStaticApplicationContext().getContentResolver().query(EpisodeContract.buildEpisodeUri(episodeid), EpisodeContract.COLUMNS, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            edvm.setFirstAired(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_FIRST_AIRED)));
            edvm.setName(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_NAME)));
            edvm.setOverview(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_OVERVIEW)));
            edvm.setUrlImage(cursor.getString(cursor.getColumnIndex(EpisodeContract.COL_FILENAME)));
            edvm.setNumber(cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_NUMBER)));
            edvm.setSeasonNumber(cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_SEASON_NUMBER)));
            edvm.setRating(cursor.getDouble(cursor.getColumnIndex(EpisodeContract.COL_RATING)));
            edvm.setWatch(cursor.getInt(cursor.getColumnIndex(EpisodeContract.COL_WATCH)) == 1);

            cursor.close();
        }
        return edvm;
    }

    public static void updateWatch(String idEpisode, boolean isWatch){
        ContentValues cv = new ContentValues();
        cv.put(EpisodeContract.COL_WATCH, isWatch ? 1 : 0);
        Application.getStaticApplicationContext().getContentResolver().update(EpisodeContract.CONTENT_URI, cv, EpisodeContract.sEpisodeIdSelection, new String[]{idEpisode});
    }

}
