package smasini.it.traxer.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import smasini.it.traxer.R;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.database.DBOperation;

/**
 * Created by Simone Masini on 16/04/2016.
 */
public class NotificationReceiver extends BroadcastReceiver{

    private final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int count = DBOperation.getCountEpisodeToday();
        if(count>0){
            Notification notification = NotificationHelper.createNotification(Application.getStaticApplicationContext().getString(R.string.notification_title), String.format(Application.getStaticApplicationContext().getString(R.string.notification_message), count));
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
