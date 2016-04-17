package smasini.it.traxer.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import smasini.it.traxer.R;
import smasini.it.traxer.app.Application;
import smasini.it.traxer.app.activities.MainActivity;

/**
 * Created by Simone Masini on 16/04/2016.
 */
public class NotificationHelper {

    public static void enableNotification(Context context){
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int delay = 1000 * 60 * 60 * 24; //after the first check, check every 24 hours
        long futureInMillis = SystemClock.elapsedRealtime() + 1000*60*60*2; //first check in 2 hours
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, delay, pendingIntent);
    }

    public static void disableNotification(Context context){
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    public static Notification createNotification(String title, String message) {
        Intent intent = new Intent(Application.getStaticApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(Application.getStaticApplicationContext(), 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Bitmap icon = BitmapFactory.decodeResource(Application.getStaticApplicationContext().getResources(), R.mipmap.ic_launcher);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Application.getStaticApplicationContext());
        notificationBuilder
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(icon)
                .setLights(Color.BLUE, 1000, 1000)
                //.setStyle(new NotificationCompat.BigTextStyle(notificationBuilder).bigText(message))
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        return notificationBuilder.build();
    }
}
