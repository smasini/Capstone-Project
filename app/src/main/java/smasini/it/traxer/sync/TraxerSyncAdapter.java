package smasini.it.traxer.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import java.util.List;

import smasini.it.thetvdb.TheTVDB;
import smasini.it.thetvdb.support.Serie;
import smasini.it.thetvdb.task.callbacks.CallbackSerie;
import smasini.it.traxer.R;
import smasini.it.traxer.database.DBOperation;

/**
 * Created by Simone Masini on 16/04/2016.
 */
public class TraxerSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 60 * 60 * 24;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/4;
    private ContentResolver mContentResolver;
    /**
     * Set up the sync adapter
     */
    public TraxerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }
    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public TraxerSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        TheTVDB.getInstance().updateData(DBOperation.getIdSeries(), new CallbackSerie() {
            @Override
            public void doAfterTask(List<Serie> series) {
                DBOperation.insertSeries(series);
            }
        });
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static void stopSyncPeriodicaly(Context context){
        Account account = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        String authority = context.getString(R.string.content_authority);
        ContentResolver.cancelSync(account, authority);
    }

    public static void startSyncPeriodically(Context context){
        ContentResolver.setSyncAutomatically(getSyncAccount(context), context.getString(R.string.content_authority), true);
    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            String authority = context.getString(R.string.content_authority);
            configurePeriodicSync(newAccount, authority, SYNC_INTERVAL, SYNC_FLEXTIME);
        }
        return newAccount;
    }

    public static void configurePeriodicSync(Account account, String authority, int syncInterval, int flexTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }
}
