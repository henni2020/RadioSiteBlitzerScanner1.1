package com.example.radiositeblitzerscanner11;


import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationSender {
    public static void sendNotification(Context context, String nTitle, String nText ) {
        // Intent für die Aktion beim Klicken auf die Benachrichtigung
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Erstellen der Benachrichtigung
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Symbol für die Benachrichtigung
                .setContentTitle(nTitle)                //("Neue Nachricht")
                //.setContentText(nText)                  // ("Dies ist eine einfache Benachrichtigung." )
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(nText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Wichtigkeit der Notification
                .setContentIntent(pendingIntent) // Aktion beim Klicken
                .setAutoCancel(true); // Schließt die Benachrichtigung, wenn der Benutzer darauf klickt

        // Benachrichtigung anzeigen
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
        }
        // Log.d(LOG_TAG, "NotificationSender . sendNotification(Context context)\n");
        notificationManager.notify(1, builder.build());
    }
}
