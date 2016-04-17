package smasini.it.traxer.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import smasini.it.traxer.database.contract.ActorContract;
import smasini.it.traxer.database.contract.BannerContract;
import smasini.it.traxer.database.contract.BaseContract;
import smasini.it.traxer.database.contract.CastContract;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.database.contract.SerieContract;
import smasini.it.traxer.utils.Utility;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.database
 * Created by Simone Masini on 13/02/2016 at 12.19.
 */
public class TraxerProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SQLiteDatabase db;

    static final int SERIE = 100;
    static final int SERIE_WITH_ID = 101;
    static final int EPISODE = 102;
    static final int ACTOR = 103;
    static final int CAST = 104;
    static final int EPISODE_WITH_ID = 105;
    static final int EPISODE_SEASONS = 106;
    static final int ACTOR_WITH_ID = 107;
    static final int ACTOR_WITH_SERIEID = 108;
    static final int BANNER = 109;
    static final int EPISODE_WITH_SERIEID_AND_SEASON = 110;
    static final int COUNT_SERIE = 111;
    static final int COUNT_EPISODE_WATCH = 112;
    static final int COUNT_EPISODE = 113;
    static final int TIME_WATCH = 114;
    static final int EPISODE_NEXT_OUT = 115;
    static final int EPISODE_MISS = 116;
    static final int EPISODE_WITH_SERIEID = 117;
    static final int COUNT_EPISODE_TOTAL_BY_SEASON = 118;
    static final int COUNT_EPISODE_WATCH_BY_SEASON = 119;
    static final int COUNT_EPISODE_TOTAL_BY_SERIE = 120;
    static final int COUNT_SEASON_TOTAL_BY_SERIE = 121;
    static final int COUNT_EPISODE_OUT_TODAY = 122;
    static final int NEXT_EPISODE_TO_WATCH = 123;

    private static final SQLiteQueryBuilder sTimeQueryBuilder, sSerieQueryBuilder, sEpisodeQueryBuilder, sSeasonQueryBuilder, sCastQueryBuilder, sBannerQueryBuilder;

    static {
        sSerieQueryBuilder = new SQLiteQueryBuilder();
        sSerieQueryBuilder.setTables(SerieContract.TABLE_NAME);

        sEpisodeQueryBuilder = new SQLiteQueryBuilder();
        sEpisodeQueryBuilder.setTables(EpisodeContract.TABLE_NAME);

        sCastQueryBuilder = new SQLiteQueryBuilder();
        sCastQueryBuilder.setTables(CastContract.TABLE_NAME + " INNER JOIN " + ActorContract.TABLE_NAME +
                " ON " + CastContract.COL_ACTOR_ID + " = " + ActorContract.COL_ID
        );

        sSeasonQueryBuilder = new SQLiteQueryBuilder();
        sSeasonQueryBuilder.setTables(
                EpisodeContract.TABLE_NAME + " , " + BannerContract.TABLE_NAME
        );

        sBannerQueryBuilder = new SQLiteQueryBuilder();
        sBannerQueryBuilder.setTables(BannerContract.TABLE_NAME);

        sTimeQueryBuilder = new SQLiteQueryBuilder();
        sTimeQueryBuilder.setTables(EpisodeContract.TABLE_NAME + " INNER JOIN " + SerieContract.TABLE_NAME +
                                    " ON ( " + EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_SERIE_ID + " = " + SerieContract.TABLE_NAME + "." + SerieContract.COL_ID + " )"
        );
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/all/watch", COUNT_EPISODE_WATCH);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/all/today", COUNT_EPISODE_OUT_TODAY);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/all", COUNT_EPISODE);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/idserie/season/total/*", COUNT_SEASON_TOTAL_BY_SERIE);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/idserie/season/watch/*/*", COUNT_EPISODE_WATCH_BY_SEASON);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/idserie/season/*/*", COUNT_EPISODE_TOTAL_BY_SEASON);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/count/idserie/total/*", COUNT_EPISODE_TOTAL_BY_SERIE);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/time/watch", TIME_WATCH);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/nextout", EPISODE_NEXT_OUT);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/miss", EPISODE_MISS);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/seasons/*", EPISODE_SEASONS);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/serieid/*", EPISODE_WITH_SERIEID);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/next/*", NEXT_EPISODE_TO_WATCH);
        matcher.addURI(authority, BaseContract.PATH_EPISODE + "/*/*", EPISODE_WITH_SERIEID_AND_SEASON);
        matcher.addURI(authority, BaseContract.PATH_EPISODE+ "/*", EPISODE_WITH_ID);
        matcher.addURI(authority, BaseContract.PATH_EPISODE, EPISODE);

        matcher.addURI(authority, BaseContract.PATH_SERIE + "/count/all", COUNT_SERIE);
        matcher.addURI(authority, BaseContract.PATH_SERIE, SERIE);
        matcher.addURI(authority, BaseContract.PATH_SERIE+ "/*", SERIE_WITH_ID);

        matcher.addURI(authority, BaseContract.PATH_ACTOR, ACTOR);
        matcher.addURI(authority, BaseContract.PATH_ACTOR + "/*", ACTOR_WITH_ID);
        matcher.addURI(authority, BaseContract.PATH_CAST, CAST);
        matcher.addURI(authority, BaseContract.PATH_CAST + "/serieid/*", ACTOR_WITH_SERIEID);
        matcher.addURI(authority, BaseContract.PATH_BANNER, BANNER);
        return matcher;
    }

    private Cursor getSerie(Uri uri, String[] projection, String sortOrder){
        return sSerieQueryBuilder.query(db,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSerieById(Uri uri, String[] projection, String sortOrder) {
        String id = SerieContract.getIdSerieFromUri(uri);

        String[] selectionArgs = new String[]{id};
        String selection = SerieContract.sSerieIdSelection;

        return sSerieQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getEpisodesBySerieIDAndSeason(Uri uri, String[] projection, String sortOrder){
        String id = EpisodeContract.getIdEpisodeFromUri(uri);
        String season = EpisodeContract.getSeasonNumberFromUri(uri);
        String[] selectionArgs = new String[]{id, season};
        String selection = EpisodeContract.sSeasonSelection;
        return sTimeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor countEpisode(boolean watch){
        String[] projection = new String[]{ "count(*) as count" };
        String selection = null;
        if(watch){
            selection = EpisodeContract.COL_WATCH + " = 1";
        }
        if(!Utility.includeExtra()){
            if(selection == null){
                selection = EpisodeContract.COL_SEASON_NUMBER + " != 0";
            }else {
                selection += " and " + EpisodeContract.COL_SEASON_NUMBER + " != 0";
            }
        }
        return sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                null,
                null,
                null,
                null
        );
    }

    private Cursor countEpisodeBySerie(String idSerie, boolean season){
        String[] projection = new String[]{ "count(*) as count" };
        String selection = EpisodeContract.sSerieIdSelection;
        String[] selectionArgs = new String[]{ idSerie };
        String group = null;
        if(season){
            group = EpisodeContract.COL_SEASON_NUMBER;
        }
        if(!Utility.includeExtra()){
            selection += " and " + EpisodeContract.COL_SEASON_NUMBER + " != 0";
        }
        return sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                group,
                null,
                null
        );
    }

    private Cursor countEpisodeBySerieAndSeason(String idSerie, String idSeason, boolean watch){
        String[] projection = new String[]{ "count(*) as count" };
        String selection = EpisodeContract.sSeasonSelection;
        String[] selectionArgs = new String[]{ idSerie, idSeason };
        if(watch){
            selection += " and " + EpisodeContract.COL_WATCH + " = 1 ";
        }
        if(!Utility.includeExtra()){
            selection += " and " + EpisodeContract.COL_SEASON_NUMBER + " != 0";
        }
        return sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor countSerie(){
        String[] projection = new String[]{ "count(*) as count" };
        return sSerieQueryBuilder.query(db,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getEpisodesBySerieID(Uri uri, String[] projection, String sortOrder){
        String id = EpisodeContract.getIdEpisodeFromUri(uri);
        String[] selectionArgs = new String[]{id};
        String selection = EpisodeContract.sEpisodeIdSelection;
        return sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getSeasons(Uri uri, String[] projection, String sortOrder){
        String id = EpisodeContract.getIdSerieFromUri(uri);
        String[] selectionArgs = new String[]{id};
        String selection = EpisodeContract.sSerieIdSelection;
        Cursor c = sSeasonQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                EpisodeContract.COL_SEASON_NUMBER,
                null,
                sortOrder
        );

        return c;
    }

    private Cursor getEpisodeBySerie(Uri uri, String[] projection, String sortOrder){
        String id = EpisodeContract.getIdSerieFromUri(uri);
        String[] selectionArgs = new String[]{id};
        String selection = EpisodeContract.sSerieIdSelection;
        Cursor c = sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return c;
    }

    private Cursor getEpisodes(String[] projection, String sortOrder, boolean next){
        String selection;
        if(next) {
            selection = EpisodeContract.sNextSelection;
        }else{
            selection = EpisodeContract.sMissingSelection;
        }

        Cursor c = sTimeQueryBuilder.query(db,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder
        );

        return c;
    }

    private Cursor getEpisodesCountToday(){
        String selection = EpisodeContract.sTodaySelection;
        Cursor c = sEpisodeQueryBuilder.query(db,
                new String[]{"count(*) as count"},
                selection,
                null,
                null,
                null,
                null
        );

        return c;
    }

    private Cursor getActor(Uri uri, String[] projection, String sortOrder){
        String id = ActorContract.getIdActorFromUri(uri);
        String[] selectionArgs = new String[]{id};
        String selection = ActorContract.sActorIdSelection;
        return sCastQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCast(Uri uri, String[] projection, String sortOrder){
        String id = CastContract.getIdSerieFromUri(uri);
        String[] selectionArgs = new String[]{id};
        String selection = CastContract.sSerieIdSelection;

        return sCastQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getTimeWatch(){
        String[] projection = new String[]{
                "sum(" + SerieContract.TABLE_NAME + "." + SerieContract.COL_RUNTIME + ") as sum"
        };
        String selection = EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_WATCH + " = 1 ";
        if(!Utility.includeExtra()){
            selection += " and " + EpisodeContract.COL_SEASON_NUMBER + " != 0";
        }
        return sTimeQueryBuilder.query(db,
                projection,
                selection,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getNextEpisodes(Uri uri, String[] projection){
        String id = uri.getPathSegments().get(2);
        String[] selectionArgs = new String[]{id};
        String selection = EpisodeContract.sEpisodeIdSelection + " and " + EpisodeContract.TABLE_NAME + "." + EpisodeContract.COL_WATCH + " = 0 ";
        return sEpisodeQueryBuilder.query(db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                EpisodeContract.COL_FIRST_AIRED
        );
    }
    @Override
    public boolean onCreate() {
        DBHelper mOpenHelper = new DBHelper(getContext());
        db = mOpenHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)){
            case SERIE:
                retCursor = getSerie(uri, projection, selection);
                break;
            case SERIE_WITH_ID:
                retCursor = getSerieById(uri, projection, sortOrder);
                break;
            case EPISODE_WITH_ID:
                retCursor = getEpisodesBySerieID(uri, projection, sortOrder);
                break;
            case EPISODE_WITH_SERIEID:
                retCursor = getEpisodeBySerie(uri, projection, sortOrder);
                break;
            case EPISODE_SEASONS:
                retCursor = getSeasons(uri, projection, sortOrder);
                break;
            case ACTOR_WITH_ID:
                retCursor = getActor(uri, projection, sortOrder);
                break;
            case ACTOR_WITH_SERIEID:
                retCursor = getCast(uri, projection, sortOrder);
                break;
            case EPISODE_WITH_SERIEID_AND_SEASON:
                retCursor = getEpisodesBySerieIDAndSeason(uri, projection, sortOrder);
                break;
            case COUNT_SERIE:
                retCursor = countSerie();
                break;
            case COUNT_EPISODE:
                retCursor = countEpisode(false);
                break;
            case COUNT_EPISODE_WATCH:
                retCursor = countEpisode(true);
                break;
            case TIME_WATCH:
                retCursor = getTimeWatch();
                break;
            case EPISODE_NEXT_OUT:
                retCursor = getEpisodes(projection, sortOrder, true);
                break;
            case EPISODE_MISS:
                retCursor = getEpisodes(projection, sortOrder, false);
                break;
            case COUNT_EPISODE_TOTAL_BY_SERIE:
                String id = uri.getPathSegments().get(4);
                retCursor = countEpisodeBySerie(id, false);
                break;
            case COUNT_EPISODE_TOTAL_BY_SEASON:
                id = uri.getPathSegments().get(4);
                String season = uri.getPathSegments().get(5);
                retCursor = countEpisodeBySerieAndSeason(id, season, false);
                break;
            case COUNT_EPISODE_WATCH_BY_SEASON:
                id = uri.getPathSegments().get(5);
                season = uri.getPathSegments().get(6);
                retCursor = countEpisodeBySerieAndSeason(id, season, true);
                break;
            case COUNT_SEASON_TOTAL_BY_SERIE:
                id = uri.getPathSegments().get(5);
                retCursor = countEpisodeBySerie(id, true);
                break;
            case COUNT_EPISODE_OUT_TODAY:
                retCursor = getEpisodesCountToday();
                break;
            case NEXT_EPISODE_TO_WATCH:
                retCursor = getNextEpisodes(uri, projection);
                break;
            default:
                retCursor = null;
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case SERIE:
                return SerieContract.CONTENT_TYPE;
            case SERIE_WITH_ID:
            case COUNT_SERIE:
                return SerieContract.CONTENT_ITEM_TYPE;
            case EPISODE_WITH_ID:
            case COUNT_EPISODE:
            case COUNT_EPISODE_WATCH:
                return EpisodeContract.CONTENT_ITEM_TYPE;
            case EPISODE_WITH_SERIEID:
            case EPISODE_WITH_SERIEID_AND_SEASON:
                return EpisodeContract.CONTENT_TYPE;
            case ACTOR_WITH_ID:
                return CastContract.CONTENT_ITEM_TYPE;
            case ACTOR_WITH_SERIEID:
                return CastContract.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case SERIE: {
                long _id = db.insert(SerieContract.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SerieContract.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EPISODE: {
                long _id = db.insert(EpisodeContract.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = EpisodeContract.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case ACTOR: {
                long _id = db.insert(ActorContract.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ActorContract.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CAST: {
                long _id = db.insert(CastContract.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = CastContract.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case BANNER: {
                long _id = db.insert(BannerContract.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BannerContract.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case SERIE:
                rowsDeleted = db.delete(SerieContract.TABLE_NAME, selection, selectionArgs);
                break;
            case EPISODE:
                rowsDeleted = db.delete(EpisodeContract.TABLE_NAME, selection, selectionArgs);
                break;
            case ACTOR:
                rowsDeleted = db.delete(ActorContract.TABLE_NAME, selection, selectionArgs);
                break;
            case CAST:
                rowsDeleted = db.delete(CastContract.TABLE_NAME, selection, selectionArgs);
                break;
            case BANNER:
                rowsDeleted = db.delete(BannerContract.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case SERIE:
                rowsUpdated = db.update(SerieContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case EPISODE:
                rowsUpdated = db.update(EpisodeContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ACTOR:
                rowsUpdated = db.update(ActorContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CAST:
                rowsUpdated = db.update(CastContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case BANNER:
                rowsUpdated = db.update(BannerContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SERIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(SerieContract.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }else{
                            String id = (String)value.get(SerieContract.COL_ID);
                            db.update(SerieContract.TABLE_NAME, value, SerieContract.sSerieIdSelection, new String[]{id});
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case EPISODE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(EpisodeContract.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }else{
                            String id = (String)value.get(EpisodeContract.COL_ID);
                            db.update(EpisodeContract.TABLE_NAME, value, EpisodeContract.sEpisodeIdSelection, new String[]{id});
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case ACTOR:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ActorContract.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case CAST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(CastContract.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case BANNER:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(BannerContract.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
