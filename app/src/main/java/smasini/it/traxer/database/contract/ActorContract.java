package smasini.it.traxer.database.contract;

import android.content.ContentUris;
import android.net.Uri;

import smasini.it.traxer.enums.UriQueryType;

/**
 * Created by Utente on 31/03/2016.
 */
public class ActorContract {

    public static final String TABLE_NAME = "actor";

    public static final String COL_ID = "idActor";
    public static final String COL_NAME = "actorName";
    public static final String COL_IMAGE = "image";
    public static final String COL_SORT_ORDER = "sort_order";
    public static final String COL_WIKI_BIO = "bio";


    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
                                                        COL_ID + " text primary key, " +
                                                        COL_IMAGE + " text, " +
                                                        COL_NAME + " text, " +
                                                        COL_WIKI_BIO + " text, " +
                                                        COL_SORT_ORDER + " integer " +
                                                    ");";

    public static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public static final String[] COLUMNS = new String[]{
            COL_ID,
            COL_IMAGE,
            COL_NAME,
            COL_WIKI_BIO,
            COL_SORT_ORDER,
            CastContract.COL_ACTOR_ID,
            CastContract.COL_SERIEID,
            CastContract.COL_ROLE
    };

    public static final Uri CONTENT_URI = BaseContract.BASE_CONTENT_URI.buildUpon().appendPath(BaseContract.PATH_ACTOR).build();

    public static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildUri(UriQueryType type){
        return buildUri(type, null);
    }

    public static Uri buildUri(UriQueryType type, String[] params){
        Uri.Builder builder = CONTENT_URI.buildUpon();
        switch (type){
            case ACTOR_WITH_ID:
                builder.appendPath(params[0]);
                break;
        }
        return builder.build();
    }

    public static final String sActorIdSelection = TABLE_NAME + "." + COL_ID + " = ? ";

    public static String getIdActorFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }
}
