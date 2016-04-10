package smasini.it.traxer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import smasini.it.traxer.database.contract.ActorContract;
import smasini.it.traxer.database.contract.BannerContract;
import smasini.it.traxer.database.contract.CastContract;
import smasini.it.traxer.database.contract.EpisodeContract;
import smasini.it.traxer.database.contract.SerieContract;

/**
 * Project: Traxer
 * Package: smasini.it.traxer.database
 * Created by Simone Masini on 12/02/2016 at 20.01.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "traxer.db";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SerieContract.CREATE_TABLE);
        db.execSQL(EpisodeContract.CREATE_TABLE);
        db.execSQL(ActorContract.CREATE_TABLE);
        db.execSQL(CastContract.CREATE_TABLE);
        db.execSQL(BannerContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SerieContract.DROP_TABLE);
        db.execSQL(EpisodeContract.DROP_TABLE);
        db.execSQL(CastContract.DROP_TABLE);
        db.execSQL(ActorContract.DROP_TABLE);
        db.execSQL(BannerContract.DROP_TABLE);
        onCreate(db);
    }
}
