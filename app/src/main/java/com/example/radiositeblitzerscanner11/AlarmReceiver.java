package com.example.radiositeblitzerscanner11;

import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.radiositeblitzerscanner11.tools.AppTools;
import com.example.radiositeblitzerscanner11.tools.BlitzerParserFilter;
import com.example.radiositeblitzerscanner11.tools.NextAppointmentTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "alarm_channel";
    private static final int NOTIFICATION_ID = 1;
    public static Context context = null;

    private void setNextAlarm( String alarmMessage ) {
        //Log.d(LOG_TAG, alarmMessage != null ? alarmMessage : "No message");

        if( alarmMessage.matches("[0-9\\s][0-9]:[0-9\\s][0-9]:[0-9]*" ) ) {  // if alarmmessage in format "12:45"
            String[] splitString = (alarmMessage.split(":"));   // extract hour ans minute
            int hour = Integer.valueOf(splitString[0].trim());
            int minute = Integer.valueOf(splitString[1].trim());
            int alarmId = Integer.valueOf(splitString[2].trim());

            long nextAlarmMilli = NextAppointmentTime.getTomorrowUnixTime(hour, minute)*1000;

            //Date date = new Date();
            //date.setTime(nextAlarmMilli);
            //Log.d(LOG_TAG, "AlarmReceiver: new Alarm at "+nextAlarmMilli+" ms   "+date+"\n");

            this.context = context;
            Intent intent2 = new Intent(context, AlarmReceiver.class);
//            Log.d(LOG_TAG, "AlarmReceiver: hour: "+hour+" \n");
//            Log.d(LOG_TAG, "AlarmReceiver: minute: "+minute+" \n");
//            Log.d(LOG_TAG, "AlarmReceiver: alarmId: "+alarmId+" \n");
            intent2.putExtra("ALARM_MSG", String.format("%2d:%2d:%d", hour, minute, alarmId ) );
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(
                    context,
                    alarmId, // ALARM1_UNIQUE_ID,
                    intent2,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
            );

            /*
            // Test with repeating
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis()+1000, // now + 1 sek
                    //AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    60*1000,  // 60 Sekunden
                    pendingIntent);
             */

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmManager.setExact(                          // nächsten Alarm setzen
                    AlarmManager.RTC_WAKEUP,
                    nextAlarmMilli,
                    pendingIntent2
            );
        }

    }

    private void scanForBlitzer() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                BlitzerParserFilter bp = new BlitzerParserFilter( AppTools.getServiceURLBlitzer() );

                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm00" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm01" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm02" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm03" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm04" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm05" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm06" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm07" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm08" ) );
                bp.addSearchTerm( AppTools.readConfigurationString( context,"searchTerm09" ) );

                bp.parseCode();
                if( bp.getFoundCounter()>0 ) {  // notification only if found
                    String notificationText = bp.getResultTextShort();

                    String txtFormat = context.getResources().getString(R.string.txtNotificationTitleFormat); // "%d Blitzer gefunden %s"
                    String notificationTitle = String.format(txtFormat, bp.getFoundCounter(), AppTools.actualTime() );

                    //Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"+"\n" );
                    //Log.d(LOG_TAG, "found: "+foundCounter+"\n" );
                    NotificationHelper.createNotificationChannel(context);
                    NotificationSender.sendNotification(context, notificationTitle, notificationText);
                }
            }
        });
        t.start();


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d(LOG_TAG, "AlarmReceiver.onReceiver() \n");

        // --- bof nächsten Alarm setzen
        String alarmMessage = intent != null ? intent.getStringExtra("ALARM_MSG") : null;
        setNextAlarm(alarmMessage);
        // --- eof nächsten Alarm setzen

        // --------- bof : Blitzerabruf mit Filter
        scanForBlitzer();
        // --------- eof : Blitzerabruf mit Filter


        // ---- bof Notification
        //NotificationHelper.createNotificationChannel(context);
        //NotificationSender.sendNotification(context);
        // ---- eof Notification





    }







}
