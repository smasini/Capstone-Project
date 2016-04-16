package smasini.it.traxer.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

import smasini.it.traxer.utils.Utility;

/**
 * Created by smasini on 30/03/2016.
 */
public class EpisodeContract {

    public static final String TABLE_NAME = "episode";

    public static final String COL_ID = "idEpisode";
    public static final String COL_SERIE_ID = "id_serie";
    public static final String COL_NAME = "episodeName";
    public static final String COL_NUMBER = "episodeNumber";
    public static final String COL_FIRST_AIRED = "firstAired";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_SEASON_NUMBER = "seasonNumber";
    public static final String COL_FILENAME = "filename";
    public static final String COL_RATING = "rating";
    public static final String COL_RATE = "rate";
    public static final String COL_WATCH = "watch";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
                                                    COL_ID + " text primary key, " +
                                                    COL_SERIE_ID + " text, " +
                                                    COL_NAME + " text, " +
                                                    COL_NUMBER + " int, " +
                                                    COL_FIRST_AIRED + " date, " +
                                                    COL_OVERVIEW + " text, " +
                                                    COL_SEASON_NUMBER + " int, " +
                                                    COL_FILENAME + " text, " +
                                                    COL_RATING + " float, " +
                                                    COL_RATE + " text, " +
                                                    COL_WATCH + " integer, " +
                                                    "foreign key (" + COL_SERIE_ID + ") references " + SerieContract.TABLE_NAME + "(" + SerieContract.COL_ID + ")" +
                                            ");";

    public static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public static final String[] COLUMNS = new String[]{
            COL_ID,
            COL_SERIE_ID,
            COL_NAME,
            COL_NUMBER,
            COL_FIRST_AIRED,
            COL_OVERVIEW,
            COL_SEASON_NUMBER,
            COL_FILENAME,
            COL_RATING,
            COL_RATE,
            COL_WATCH
    };

    public static final String[] COLUMNS_WHIT_SERIE = new String[]{
            TABLE_NAME + "." +COL_ID,
            TABLE_NAME + "." +COL_SERIE_ID,
            TABLE_NAME + "." +COL_NAME,
            TABLE_NAME + "." +COL_NUMBER,
            TABLE_NAME + "." +COL_FIRST_AIRED,
            TABLE_NAME + "." +COL_OVERVIEW,
            TABLE_NAME + "." +COL_SEASON_NUMBER,
            TABLE_NAME + "." +COL_FILENAME,
            TABLE_NAME + "." +COL_RATING,
            TABLE_NAME + "." +COL_RATE,
            TABLE_NAME + "." +COL_WATCH,
            SerieContract.COL_ID,
            SerieContract.COL_NAME,
            SerieContract.COL_LANG,
            SerieContract.COL_BANNER,
            SerieContract.COL_POSTER,
            SerieContract.COL_NETWORK,
            SerieContract.COL_IMDBID,
            SerieContract.COL_ZAP2ITID,
            SerieContract.COL_AIRSDAYWEEK,
            SerieContract.COL_AIRSTIME,
            SerieContract.COL_GENRE,
            SerieContract.COL_RUNTIME,
            SerieContract.COL_STATUS
    };

    public static String[] getSeasonProjection(){
        String[] projection;
        if(Utility.getLanguage().equals(Utility.DEFAULT_LANGUAGE)){
            projection = new String[]{
                    EpisodeContract.COL_SEASON_NUMBER,
                    TABLE_NAME + "." + EpisodeContract.COL_SERIE_ID,
                    String.format(BannerContract.SEASON_BANNER_SELECTION, Utility.getLanguage(), Utility.getLanguage())
            };
        }else{
            projection = new String[]{
                    EpisodeContract.COL_SEASON_NUMBER,
                    TABLE_NAME + "." + EpisodeContract.COL_SERIE_ID,
                    String.format(BannerContract.SEASON_BANNER_SELECTION, Utility.getLanguage(), Utility.getLanguage()),
                    String.format(BannerContract.SEASON_BANNER_SELECTION, Utility.DEFAULT_LANGUAGE, Utility.DEFAULT_LANGUAGE),
            };
        }
         return projection;
    }

    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_EPISODE).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_EPISODE;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_EPISODE;

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildCountUri(boolean watch) {
        if(watch){
            return CONTENT_URI.buildUpon().appendPath("count").appendPath("all").appendPath("watch").build();
        }
        return CONTENT_URI.buildUpon().appendPath("count").appendPath("all").build();
    }

    public static Uri buildCountUri(String idSerie) {
        return CONTENT_URI.buildUpon().appendPath("count").appendPath("idserie").appendPath("total").appendPath(idSerie).build();
    }

    public static Uri buildCountSeasonUri(String idSerie) {
        return CONTENT_URI.buildUpon().appendPath("count").appendPath("idserie").appendPath("season").appendPath("total").appendPath(idSerie).build();
    }

    public static Uri buildCountUri(String idSerie, String seasonNumber, boolean watch) {
        Uri.Builder builder = CONTENT_URI.buildUpon().appendPath("count").appendPath("idserie").appendPath("season");
        if(watch){
            builder.appendPath("watch");
        }
        return builder.appendPath(idSerie).appendPath(seasonNumber).build();
    }

    public static Uri buildTimeUri() {
        return CONTENT_URI.buildUpon().appendPath("time").appendPath("watch").build();
    }

    public static Uri buildMissUri(){
        return CONTENT_URI.buildUpon().appendPath("miss").build();
    }

    public static Uri buildNextOutUri(){
        return CONTENT_URI.buildUpon().appendPath("nextout").build();
    }

    public static Uri buildSeasonUri(String id) {
        return CONTENT_URI.buildUpon().appendPath("seasons").appendPath(id).build();
    }

    public static Uri buildSeasonSerieUri(String id, String season) {
        return CONTENT_URI.buildUpon().appendPath(id).appendPath(season).build();
    }

    public static Uri buildEpisodeUri(String id) {
        return CONTENT_URI.buildUpon().appendPath(id).build();
    }

    public static Uri buildEpisodeBySerieUri(String id){
        return CONTENT_URI.buildUpon().appendPath("serieid").appendPath(id).build();
    }

    public static final String sSerieIdSelection = TABLE_NAME + "." + COL_SERIE_ID + " = ? ";

    public static final String sSeasonSelection = TABLE_NAME + "." + COL_SERIE_ID + " = ? AND " +
                                                TABLE_NAME + "." + COL_SEASON_NUMBER + " = ?";

    public static final String sEpisodeIdSelection = TABLE_NAME + "." + COL_ID + " = ? ";

    public static final String sNextSelection = TABLE_NAME + "." + COL_FIRST_AIRED + " > date('now') ";

    public static final String sMissingSelection = TABLE_NAME + "." + COL_FIRST_AIRED + " >= date('now', '-30 days') "+
                                                    "and " + TABLE_NAME + "." + COL_FIRST_AIRED + " <= date('now') " +
                                                    "and " + TABLE_NAME + "." + COL_WATCH + " = 0";

    public static String getIdEpisodeFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }
    public static String getIdSerieFromUri(Uri uri) {
        return uri.getPathSegments().get(2);
    }
    public static String getSeasonNumberFromUri(Uri uri) {
        return uri.getPathSegments().get(2);
    }
}
