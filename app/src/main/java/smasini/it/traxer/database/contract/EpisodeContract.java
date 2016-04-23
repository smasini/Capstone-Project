package smasini.it.traxer.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

import smasini.it.traxer.enums.UriQueryType;
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
                                                    COL_WATCH + " integer default 0, " +
                                                    "foreign key (" + COL_SERIE_ID + ") references " + SerieContract.TABLE_NAME + "(" + SerieContract.COL_ID + ") on delete cascade" +
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


    /*Type*/
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_EPISODE;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_EPISODE;

    /*URI*/
    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_EPISODE).build();

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildUri(UriQueryType type){
        return buildUri(type, null);
    }
    public static Uri buildUri(UriQueryType type, String[] params){
        Uri.Builder builder = CONTENT_URI.buildUpon();
        switch (type){
            case COUNT_ALL:
                builder.appendPath("count").appendPath("all");
                break;
            case COUNT_ALL_WATCH:
                builder.appendPath("count").appendPath("all").appendPath("watch");
                break;
            case COUNT_ALL_BY_SERIE:
                builder.appendPath("count").appendPath("idserie").appendPath("total").appendPath(params[0]);
                break;
            case COUNT_ALL_TODAY:
                builder.appendPath("count").appendPath("all").appendPath("today");
                break;
            case COUNT_SEASON_NUMBER:
                builder.appendPath("count").appendPath("idserie").appendPath("season").appendPath("total").appendPath(params[0]);
                break;
            case COUNT_SEASON_BY_SERIE:
                builder.appendPath("count").appendPath("idserie").appendPath("season").appendPath(params[0]).appendPath(params[1]);
                break;
            case COUNT_SEASON_WATCH_BY_SERIE:
                builder.appendPath("count").appendPath("idserie").appendPath("season").appendPath("watch").appendPath(params[0]).appendPath(params[1]);
                break;
            case TIME_SPENT_WATCH:
                builder.appendPath("time").appendPath("watch");
                break;
            case EPISODE_MISS:
                builder.appendPath("miss");
                break;
            case EPISODE_NEXT_OUT:
                builder.appendPath("nextout");
                break;
            case EPISODE_NEXT_OUT_TODAY:
                builder.appendPath("nextout").appendPath("today");
                break;
            case SEASONS_BY_SERIE:
                builder.appendPath("seasons").appendPath(params[0]);
                break;
            case EPISODES_BY_SERIE_AND_SEASON:
                builder.appendPath(params[0]).appendPath(params[1]);
                break;
            case EPISODE_BY_ID:
                builder.appendPath(params[0]);
                break;
            case ALL_EPISODES_BY_SERIE:
                builder.appendPath("serieid").appendPath(params[0]);
                break;
            case NEXT_EPISODE_TO_WATCH:
                builder.appendPath("next").appendPath(params[0]);
                break;
        }
        return builder.build();
    }

    /*Selection */
    public static final String sSerieIdSelection = TABLE_NAME + "." + COL_SERIE_ID + " = ? ";

    public static final String sSeasonSelection = TABLE_NAME + "." + COL_SERIE_ID + " = ? AND " +
                                                TABLE_NAME + "." + COL_SEASON_NUMBER + " = ?";

    public static final String sEpisodeIdSelection = TABLE_NAME + "." + COL_ID + " = ? ";

    public static final String sNextSelection = TABLE_NAME + "." + COL_FIRST_AIRED + " > date('now') ";

    public static final String sNextTodaySelection = TABLE_NAME + "." + COL_FIRST_AIRED + " = date('now') ";

    public static final String sTodaySelection = TABLE_NAME + "." + COL_FIRST_AIRED + " = date('now') ";

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
