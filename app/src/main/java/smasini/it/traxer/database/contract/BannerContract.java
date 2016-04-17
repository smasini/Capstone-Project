package smasini.it.traxer.database.contract;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by Simone Masini on 04/04/2016.
 */
public class BannerContract {

    public static final String TABLE_NAME = "banner";

    public static final String COL_ID = "id_banner";
    public static final String COL_SERIE_ID = "id_serie";
    public static final String COL_TYPE = "banner_type";
    public static final String COL_TYPE_2 = "banner_type_2";
    public static final String COL_LANGUAGE = "language";
    public static final String COL_URL = "banner_path";
    public static final String COL_RATING = "rating";
    public static final String COL_SEASON = "season";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
            COL_ID + " text primary key, " +
            COL_SERIE_ID + " text, " +
            COL_TYPE + " text, " +
            COL_TYPE_2 + " text, " +
            COL_LANGUAGE + " date, " +
            COL_URL + " text, " +
            COL_RATING + " float, " +
            COL_SEASON + " integer, " +
            "foreign key (" + COL_SERIE_ID + ") references " + SerieContract.TABLE_NAME + "(" + SerieContract.COL_ID + ") on delete cascade" +
            ");";

    public static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public static final String[] COLUMNS = new String[]{
            COL_ID,
            COL_SERIE_ID,
            COL_TYPE,
            COL_TYPE_2,
            COL_LANGUAGE,
            COL_URL,
            COL_RATING,
            COL_SEASON
    };

    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_BANNER).build();

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static final String sSerieIdSelection = TABLE_NAME + "." + COL_SERIE_ID + " = ? ";

    public static final String SEASON_BANNER_SELECTION = "( " +
                                                                "select " + COL_URL +
                                                                " from " + TABLE_NAME +
                                                                " where " + TABLE_NAME + "." + COL_TYPE + " = 'season' AND " +
                                                                            TABLE_NAME + "." + COL_TYPE_2 + " = 'season' AND " +
                                                                            EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_SEASON_NUMBER + " = " + TABLE_NAME + "." + COL_SEASON + " and " +
                                                                            EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_SERIE_ID + " = " + TABLE_NAME + "." + COL_SERIE_ID +
                                                                            " and " + TABLE_NAME + "." + COL_LANGUAGE + " = '%s' " +
                                                                " order by " + TABLE_NAME + "." + COL_RATING + " is not null desc limit 1 " +
                                                         ") as " + COL_URL + "_%s";
}
