package smasini.it.traxer.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Simone Masini on 16/04/2016.
 */
public class NotificationReceiver extends BroadcastReceiver{

    private final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //eseguo query nel db per verificare che siano uscite puntate, e in caso mando la notifica
        Notification notification = NotificationHelper.createNotification("title", "message");
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
