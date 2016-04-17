package smasini.it.traxer.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

import smasini.it.traxer.enums.UriQueryType;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.database.contract
 * Created by Simone Masini on 12/02/2016 at 20.01.
 */
public class SerieContract {

    public static final String TABLE_NAME = "serie";

    public static final String COL_ID = "idSerie";
    public static final String COL_NAME = "nameSerie";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_LANG = "language";
    public static final String COL_BANNER = "banner";
    public static final String COL_POSTER = "poster";
    public static final String COL_FIRSTAIRED="firstAired";
    public static final String COL_NETWORK ="network";
    public static final String COL_IMDBID="imdb_ID";
    public static final String COL_ZAP2ITID ="zap2it_id";
    public static final String COL_AIRSDAYWEEK="airs_DayOfWeek";
    public static final String COL_AIRSTIME="airs_Time";
    public static final String COL_GENRE="genre";
    public static final String COL_RATING="rating";
    public static final String COL_RATE = "rate";
    public static final String COL_RUNTIME="runtime";
    public static final String COL_STATUS="status";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
                                                    COL_ID + " text primary key, " +
                                                    COL_NAME + " text, " +
                                                    COL_OVERVIEW + " text, " +
                                                    COL_LANG + " text, " +
                                                    COL_BANNER + " text, " +
                                                    COL_POSTER + " text, " +
                                                    COL_FIRSTAIRED + " text, " +
                                                    COL_NETWORK + " text, " +
                                                    COL_IMDBID + " text, " +
                                                    COL_ZAP2ITID + " text, " +
                                                    COL_AIRSDAYWEEK + " text, " +
                                                    COL_AIRSTIME + " text, " +
                                                    COL_GENRE + " text, " +
                                                    COL_RATING + " float, " +
                                                    COL_RATE + " text, " +
                                                    COL_RUNTIME + " integer, " +
                                                    COL_STATUS + " text );";

    public static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public static final String[] COLUMNS = new String[]{
            COL_ID,
            COL_NAME,
            COL_OVERVIEW,
            COL_LANG,
            COL_BANNER,
            COL_POSTER,
            COL_FIRSTAIRED,
            COL_NETWORK,
            COL_IMDBID,
            COL_ZAP2ITID,
            COL_AIRSDAYWEEK,
            COL_AIRSTIME,
            COL_GENRE,
            COL_RATING,
            COL_RATE,
            COL_RUNTIME,
            COL_STATUS
    };

    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_SERIE).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_SERIE;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_SERIE;

    public static final String sSerieIdSelection = TABLE_NAME + "." + COL_ID + " = ? ";

    public static Uri buildUri(UriQueryType type){
        return buildUri(type, null);
    }

    public static Uri buildUri(UriQueryType type, String[] params){
        Uri.Builder builder = CONTENT_URI.buildUpon();
        switch (type){
            case COUNT_ALL_SERIE:
                builder.appendPath("count").appendPath("all");
                break;
            case SERIE_WITH_ID:
                builder.appendPath(params[0]);
        }
        return builder.build();
    }

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static String getIdSerieFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

}
