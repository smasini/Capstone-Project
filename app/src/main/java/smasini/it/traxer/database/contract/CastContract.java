package smasini.it.traxer.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

import smasini.it.traxer.enums.UriQueryType;

/**
 * Created by Utente on 31/03/2016.
 */
public class CastContract {

    public static final String TABLE_NAME = "cast";
    public static final String COL_ACTOR_ID = "actorID";
    public static final String COL_SERIEID = "serieID";
    public static final String COL_ROLE = "role";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
            COL_ACTOR_ID + " text, " +
            COL_SERIEID + " text, " +
            COL_ROLE + " text, " +
            "primary key (" + COL_ACTOR_ID +"," + COL_SERIEID +  "), " +
            "foreign key (" + COL_ACTOR_ID + ") references " + ActorContract.TABLE_NAME + "(" + ActorContract.COL_ID + ") on delete cascade ," +
            "foreign key (" + COL_SERIEID + ") references " + SerieContract.TABLE_NAME + "(" + SerieContract.COL_ID + ") on delete cascade " +
            ");";

    public static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public static final String[] COLUMNS = new String[]{
            COL_ACTOR_ID,
            COL_SERIEID,
            COL_ROLE
    };

    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_CAST).build();

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildUri(UriQueryType type){
        return buildUri(type, null);
    }

    public static Uri buildUri(UriQueryType type, String[] params){
        Uri.Builder builder = CONTENT_URI.buildUpon();
        switch (type){
            case CAST_WITH_SERIEID:
                builder.appendPath("serieid").appendPath(params[0]);
                break;
        }
        return builder.build();
    }

    public static final String sSerieIdSelection =  "" + COL_SERIEID + " = ? ";

    public static String getIdSerieFromUri(Uri uri) {
        return uri.getPathSegments().get(2);
    }

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_CAST;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + BaseContract.CONTENT_AUTHORITY + "/" + BaseContract.PATH_CAST;


}
