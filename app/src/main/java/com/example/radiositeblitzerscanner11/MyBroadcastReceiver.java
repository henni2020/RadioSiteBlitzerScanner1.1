package com.example.radiositeblitzerscanner11;


import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "MyBroadcastReceiver.onReceiver() \n");


        // Notification Channel erstellen (für Android 8.0 und höher)
        String channelId = "my_channel_id";
        CharSequence channelName = "My Channel";
        String channelDescription = "This is my notification channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
        }

        // Intent für die Benachrichtigung
        Intent notificationIntent = new Intent(context, MainActivity.class); // Ziel-Aktivität
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                //PendingIntent.FLAG_UPDATE_CURRENT
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Notification erstellen
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Icon für die Notification
                .setContentTitle("Alarm")
                .setContentText("Der Alarm wurde ausgelöst!") // Benachrichtigungstext
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Priorität
                .setContentIntent(pendingIntent) // Intent für die Benachrichtigung
                .setAutoCancel(true); // Benachrichtigung automatisch schließen beim Klick

        // Notification anzeigen
        notificationManager.notify(1, builder.build());
    }
}
