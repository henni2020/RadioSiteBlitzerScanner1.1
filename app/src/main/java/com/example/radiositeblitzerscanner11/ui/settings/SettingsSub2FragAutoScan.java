package com.example.radiositeblitzerscanner11.ui.settings;

import static com.example.radiositeblitzerscanner11.Constants.ALARM2_UNIQUE_ID;
import static com.example.radiositeblitzerscanner11.Constants.ALARM1_UNIQUE_ID;

import com.example.radiositeblitzerscanner11.AlarmReceiver;
import com.example.radiositeblitzerscanner11.MyBroadcastReceiver;
import com.example.radiositeblitzerscanner11.NotificationHelper;
import com.example.radiositeblitzerscanner11.tools.AppTools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.radiositeblitzerscanner11.R;
import com.example.radiositeblitzerscanner11.tools.NextAppointmentTime;

import java.util.HashMap;

/**
 * Config-Fragment for time autostart Scan
 */
public class SettingsSub2FragAutoScan extends Fragment {
    private AlarmManager alarmManager;
    private View thisContextView;
    private String toastMessage = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_sub2_frag_auto_scan, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thisContextView = view;
//        Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                +"\n--- SettingsSub1Fragment onViewCreated"+"\n" );

        // Click Event Save Button ----------
        // Click Button Save
        view.findViewById(R.id.buttonSave).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        Log.d(LOG_TAG, "OnClickListener   " + "\n" );

                        // 1. Save Data
                        //onSave( thisContextView );
                        HashMap<String, String> configs = readConfigsFromFormular( thisContextView );
                        AppTools.saveTimerConfigs( configs, requireContext() );

                        // 2. set alarms
                        setAlarms12(configs);

                        // 3. Toast Notification
                        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "Dies ist eine Toast-Nachricht", Toast.LENGTH_SHORT).show();
                        //int toastMassage = 123;
                        //Toast.makeText(MainActivity.this, toastMassage, Toast.LENGTH_SHORT).show();

                    }
                });

        view.findViewById(R.id.switchAutoTime1).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        timePickersVisible(view);
                    }
                });

        view.findViewById(R.id.switchAutoTime2).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        timePickersVisible(view);
                    }
                });


        // Init TimePickers
//        onInit(view);
        HashMap<String, String> configs = AppTools.readTimerConfigs( requireContext() );    // read config data
        writeConfigsToForm(configs, view);                      // init formular with data

        NotificationHelper.createNotificationChannel(requireContext());

        if(true) return;

        /*
        Context context = requireContext();

        // Read Configurations
        String autoTimeActive1  = AppTools.readConfigurationString(context, "autoTimeActive1" );
        String autoTimeActive2  = AppTools.readConfigurationString(context, "autoTimeActive2" );
        String autoTimeHour1    = AppTools.readConfigurationString(context, "autoTimeHour1"  );
        String autoTimeMinute1  = AppTools.readConfigurationString(context, "autoTimeMinute1" );
        String autoTimeHour2    = AppTools.readConfigurationString(context, "autoTimeHour2"  );
        String autoTimeMinute2  = AppTools.readConfigurationString(context, "autoTimeMinute2" );

//        Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                +"\n--- onInit"+"\n" );

        Switch switchAutoTime1 = (Switch) view.findViewById(R.id.switchAutoTime1);
        Switch switchAutoTime2 = (Switch) view.findViewById(R.id.switchAutoTime2);

        // Init the switches
        switchAutoTime1.setChecked( autoTimeActive1.equals("true")?true:false );
        switchAutoTime2.setChecked( autoTimeActive2.equals("true")?true:false );

        // Init the Time Pickers -----------
        TimePicker timepickerAutotime1 = (TimePicker) view.findViewById(R.id.timepickerAutotime1); // initiate a time picker+
        timepickerAutotime1.setIs24HourView(true); // set 24 hours mode for the time picker
        timepickerAutotime1.setHour( Integer.valueOf(autoTimeHour1) ); // from api level 23
        timepickerAutotime1.setMinute( Integer.valueOf(autoTimeMinute1)); // from api level 23

        TimePicker timepickerAutotime2 = (TimePicker) view.findViewById(R.id.timepickerAutotime2); // initiate a time picker+
        timepickerAutotime2.setIs24HourView(true); // set 24 hours mode for the time picker
        timepickerAutotime2.setHour( Integer.valueOf(autoTimeHour2) ); // from api level 23
        timepickerAutotime2.setMinute( Integer.valueOf(autoTimeMinute2)); // from api level 23

        // If switches are off, time pickers invisible
        if (autoTimeActive1.equals("true")) {
            timepickerAutotime1.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime1.setVisibility(View.INVISIBLE);
        }

        if (autoTimeActive2.equals("true")) {
            timepickerAutotime2.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime2.setVisibility(View.INVISIBLE);
        }
        */


    }



    /**
     *  void setAlarm( Boolean switchOnOff, int hour, int minute, int alarmId )
     *  Set Alarm Time
     * @param switchOnOff       Timer active, set or cancel it
     * @param hour              for next hour and minute
     * @param minute
     * @param alarmId           alarm id
     */
    void setAlarm( Boolean switchOnOff, int hour, int minute, int alarmId ) {
//        Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                +"\n--- setAlarm()"+"\n" );
//        Log.d(LOG_TAG,"switchOnOff"+switchOnOff+"\n" );
//        Log.d(LOG_TAG,"hour"+hour+"\n" );
//        Log.d(LOG_TAG,"minute"+minute+"\n" );

        Context thisContext = requireContext();

        if( switchOnOff ) {
            // switch==On : active, install alarm
            //String inputMessage = "Alarm aufstehen";
//            Log.d(LOG_TAG,"Install Alarm"+"\n" );

            AlarmReceiver.context = thisContext;

            Intent intent = new Intent(thisContext, AlarmReceiver.class);
            // Intent intent = new Intent(thisContext, MyBroadcastReceiver.class);
            intent.putExtra("ALARM_MSG", String.format("%2d:%2d:%d", hour, minute, alarmId ) );

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    thisContext,
                    alarmId, // ALARM1_UNIQUE_ID,
                    intent,
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

            long nextAlarmMilli = NextAppointmentTime.getNextUnixTime(hour, minute)*1000;
//            Log.d(LOG_TAG,"setAlarm()  hour="+hour+"\n" );
//            Log.d(LOG_TAG,"setAlarm()  minute="+minute+"\n" );
//            Log.d(LOG_TAG,"setAlarm()  nextAlarmMilli="+nextAlarmMilli+"\n" );

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    nextAlarmMilli,
                    pendingIntent
            );


        } else {
            // switch==Off : inactive, cancel alarm
//            Log.d(LOG_TAG,"Cancel Alarm"+"\n" );
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    thisContext,
                    alarmId, // use same id that is used to schedule the alarm to cancel it
                    new Intent(thisContext, AlarmReceiver.class),
                    //new Intent(thisContext, MyBroadcastReceiver.class),
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
            );
            alarmManager.cancel(pendingIntent);
        }

    }


    /**
     * Setzt oder stoppt den Autostart Timer gemäß der Werte in den Konfigurationen.
     * Setzt den Attributsting toastMessage.
     *
     * @param configs
     */
    void setAlarms12( HashMap<String, String> configs ) {
        boolean activeSwitch;
        int hour, minute;

        toastMessage = "";
//        Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                +"\n--- setAlarms12()"+"\n" );
//        Log.d(LOG_TAG,"configs="+configs.toString()+"\n" );

        // Alarm 1
        activeSwitch = configs.get("autoTimeActive1").equals("true");
        hour = Integer.valueOf( configs.get("autoTimeHour1") );
        minute = Integer.valueOf( configs.get("autoTimeMinute1") );
        setAlarm(activeSwitch, hour, minute, ALARM1_UNIQUE_ID);
        if( activeSwitch ) {
            toastMessage = toastMessage + String.format("Set Alarm 1: %2d:%2d h ", hour, minute);
        } else {
            toastMessage = toastMessage + "Alarm 1: Off ";
        }

        // Alarm 2
        activeSwitch = configs.get("autoTimeActive2").equals("true");
        hour = Integer.valueOf( configs.get("autoTimeHour2") );
        minute = Integer.valueOf( configs.get("autoTimeMinute2") );
        setAlarm(activeSwitch, hour, minute, ALARM2_UNIQUE_ID);
        if( activeSwitch ) {
            toastMessage = toastMessage + String.format("Set Alarm 2: %2d:%2d h ", hour, minute);
        } else {
            toastMessage = toastMessage + "Alarm 2: Off ";
        }

    }

    /**
     * Set the time picher visible or invisible
     * depends of the sctive switches
     * @param view
     */
    void timePickersVisible(View view) {
        Switch switchAutoTime1 = (Switch) view.findViewById(R.id.switchAutoTime1);
        Switch switchAutoTime2 = (Switch) view.findViewById(R.id.switchAutoTime2);

        Boolean chk1 = switchAutoTime1.isChecked();
        Boolean chk2 = switchAutoTime2.isChecked();

        TimePicker timepickerAutotime1 = (TimePicker) view.findViewById(R.id.timepickerAutotime1); // initiate a time picker+
        TimePicker timepickerAutotime2 = (TimePicker) view.findViewById(R.id.timepickerAutotime2); // initiate a time picker+

        if( chk1 ) {
            timepickerAutotime1.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime1.setVisibility(View.INVISIBLE);
        }

        if( chk2 ) {
            timepickerAutotime2.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime2.setVisibility(View.INVISIBLE);
        }




    }


    /**
     * HashMap<String, String> readConfigsFromFormular( View view )
     * @param view of formular
     * @return ConfigData read from formular
     */
    HashMap<String, String> readConfigsFromFormular( View view ) {
        HashMap<String, String> ret = new HashMap<String, String>();

        // For Values
        String autoTimeActive1 = "";
        String autoTimeActive2 = "";

        String autoTimeHour1    = "";
        String autoTimeMinute1  = "";
        String autoTimeHour2    = "";
        String autoTimeMinute2  = "";

        // Active Times, morning, afternoon
        Switch switchAutoTime1 = (Switch) view.findViewById(R.id.switchAutoTime1);
        Switch switchAutoTime2 = (Switch) view.findViewById(R.id.switchAutoTime2);

        autoTimeActive1 = switchAutoTime1.isChecked()?"true":"false";
        autoTimeActive2 = switchAutoTime2.isChecked()?"true":"false";

        // Hour & Minute    morning & afternoon
        TimePicker timepickerAutotime1 = (TimePicker) view.findViewById(R.id.timepickerAutotime1);
        TimePicker timepickerAutotime2 = (TimePicker) view.findViewById(R.id.timepickerAutotime2);

        autoTimeHour1   = String.valueOf( timepickerAutotime1.getHour() );
        autoTimeMinute1 = String.valueOf( timepickerAutotime1.getMinute() );

        autoTimeHour2   = String.valueOf( timepickerAutotime2.getHour() );
        autoTimeMinute2 = String.valueOf( timepickerAutotime2.getMinute() );

        ret.put("autoTimeActive1", autoTimeActive1 );
        ret.put("autoTimeActive2", autoTimeActive2 );

        ret.put("autoTimeHour1", autoTimeHour1);
        ret.put("autoTimeMinute1", autoTimeMinute1);

        ret.put("autoTimeHour2", autoTimeHour2);
        ret.put("autoTimeMinute2", autoTimeMinute2);

        return ret;
    }

    /**
     * void writeConfigsToForm(HashMap<String, String> configs, View view )
     * Write contig data into form
     *
     * @param configs
     * @param view - form view
     */
    void writeConfigsToForm(HashMap<String, String> configs, View view ) {
        Context context = requireContext();
        // Read Configurations
        String autoTimeActive1  = configs.get("autoTimeActive1");
        String autoTimeActive2  = configs.get("autoTimeActive2");
        String autoTimeHour1    = configs.get("autoTimeHour1");
        String autoTimeMinute1  = configs.get("autoTimeMinute1");
        String autoTimeHour2    = configs.get("autoTimeHour2");
        String autoTimeMinute2  = configs.get("autoTimeMinute2");

        autoTimeActive1 = autoTimeActive1==null?"false":autoTimeActive1;
        autoTimeActive2 = autoTimeActive2==null?"false":autoTimeActive2;

        autoTimeHour1 = autoTimeHour1==null?"false":autoTimeHour1;
        autoTimeMinute1 = autoTimeMinute1==null?"false":autoTimeMinute1;

        autoTimeHour2 = autoTimeHour2==null?"false":autoTimeHour2;
        autoTimeMinute2 = autoTimeMinute2==null?"false":autoTimeMinute2;

//        Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                +"\n--- onInit"+"\n" );

        Switch switchAutoTime1 = (Switch) view.findViewById(R.id.switchAutoTime1);
        Switch switchAutoTime2 = (Switch) view.findViewById(R.id.switchAutoTime2);

        // Init the switches
        switchAutoTime1.setChecked( autoTimeActive1.equals("true")?true:false );
        switchAutoTime2.setChecked( autoTimeActive2.equals("true")?true:false );

        // Init the Time Pickers -----------
        TimePicker timepickerAutotime1 = (TimePicker) view.findViewById(R.id.timepickerAutotime1); // initiate a time picker+
        timepickerAutotime1.setIs24HourView(true); // set 24 hours mode for the time picker
        timepickerAutotime1.setHour( Integer.valueOf(autoTimeHour1) ); // from api level 23
        timepickerAutotime1.setMinute( Integer.valueOf(autoTimeMinute1)); // from api level 23

        TimePicker timepickerAutotime2 = (TimePicker) view.findViewById(R.id.timepickerAutotime2); // initiate a time picker+
        timepickerAutotime2.setIs24HourView(true); // set 24 hours mode for the time picker
        timepickerAutotime2.setHour( Integer.valueOf(autoTimeHour2) ); // from api level 23
        timepickerAutotime2.setMinute( Integer.valueOf(autoTimeMinute2)); // from api level 23

        // If switches are off, time pickers invisible
        if (autoTimeActive1.equals("true")) {
            timepickerAutotime1.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime1.setVisibility(View.INVISIBLE);
        }

        if (autoTimeActive2.equals("true")) {
            timepickerAutotime2.setVisibility(View.VISIBLE);
        } else {
            timepickerAutotime2.setVisibility(View.INVISIBLE);
        }
    }





}

