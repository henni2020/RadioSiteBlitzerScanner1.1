package com.example.radiositeblitzerscanner11.tools;

import static androidx.core.app.PendingIntentCompat.getActivity;
import static com.example.radiositeblitzerscanner11.Constants.CONFIG_BLITZER;
import static com.example.radiositeblitzerscanner11.Constants.DEVELOPMENT_MODE;
import static com.example.radiositeblitzerscanner11.Constants.FFH_BLITZER_URL;
import static com.example.radiositeblitzerscanner11.Constants.FFH_BLITZER_URL_DEV;
import static com.example.radiositeblitzerscanner11.Constants.FFH_TRAFFIC_URL;
import static com.example.radiositeblitzerscanner11.Constants.FFH_TRAFFIC_URL_DEV;
import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;
import static com.example.radiositeblitzerscanner11.Constants.SERACHTERMS_COUNT;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.radiositeblitzerscanner11.AlarmReceiver;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class AppTools  {

    public static void saveConfigurationString( Context thisContext,  String key, String value) {
        // SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = thisContext.getSharedPreferences(CONFIG_BLITZER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Log.d(LOG_TAG, "saveConfigurationString(): key:"+key+"  value:"+value+" \n" );
        editor.putString(key, value);
        editor.apply();
    }


    public static String readConfigurationString(Context thisContext, String key) {

        SharedPreferences sharedPref = thisContext.getSharedPreferences(CONFIG_BLITZER, Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        // Log.d(LOG_TAG, "readConfigurationString(): sharedPref:"+sharedPref+" \n" );

        String ret = sharedPref.getString(key, "");
        //Log.d(LOG_TAG, "AppCompatActivityTools.readConfigurationString(): key:"+key+" value:"+ret+"\n" );
        return ret;
    }

    /**
     * HashMap<String, String> readConfigs()
     * Read configs into HashMap
     *
     * @return Configs
     */
    public static HashMap<String, String> readTimerConfigs(Context context) {
        // Read Configurations
        String autoTimeActive1  = AppTools.readConfigurationString(context, "autoTimeActive1" );
        String autoTimeActive2  = AppTools.readConfigurationString(context, "autoTimeActive2" );
        String autoTimeHour1    = AppTools.readConfigurationString(context, "autoTimeHour1"  );
        String autoTimeMinute1  = AppTools.readConfigurationString(context, "autoTimeMinute1" );
        String autoTimeHour2    = AppTools.readConfigurationString(context, "autoTimeHour2"  );
        String autoTimeMinute2  = AppTools.readConfigurationString(context, "autoTimeMinute2" );

        HashMap<String, String> ret = new HashMap<String, String>();

        ret.put("autoTimeActive1", autoTimeActive1.equals("true")?"true":"false" );
        ret.put("autoTimeActive2", autoTimeActive2.equals("true")?"true":"false" );

        ret.put("autoTimeHour1", autoTimeHour1.equals("")?"7":autoTimeHour1);           // default 07:00 h
        ret.put("autoTimeMinute1", autoTimeMinute1.equals("")?"0":autoTimeMinute1);

        ret.put("autoTimeHour2", autoTimeHour2.equals("")?"16":autoTimeHour2);           // default 16:00 h
        ret.put("autoTimeMinute2", autoTimeMinute2.equals("")?"0":autoTimeMinute2);

        return ret;
    }


    /**
     * void saveConfigs( HashMap<String, String> configs )
     *
     * Save Configdata in SharedPreferences
     *
     * @param configs ConfigData
     */
    public static void saveTimerConfigs(HashMap<String, String> configs, Context context ) {
        AppTools.saveConfigurationString(context, "autoTimeActive1", configs.get("autoTimeActive1") );
        AppTools.saveConfigurationString(context, "autoTimeActive2", configs.get("autoTimeActive2") );
        AppTools.saveConfigurationString(context, "autoTimeHour1",   configs.get("autoTimeHour1")   );
        AppTools.saveConfigurationString(context, "autoTimeMinute1", configs.get("autoTimeMinute1") );
        AppTools.saveConfigurationString(context, "autoTimeHour2",   configs.get("autoTimeHour2")   );
        AppTools.saveConfigurationString(context, "autoTimeMinute2", configs.get("autoTimeMinute2") );
    }

    /**
     * Lese Suchworte aus Datenbereich in Hashmap
     * @param context
     * @return configs-HashMap
     */
    public static HashMap<String, String> readSearchTermsConfigs(Context context) {
        String key, value;
        HashMap<String,String> configs = new HashMap<String,String>();

        for(int i=0; i<SERACHTERMS_COUNT; i++ ) {
            key = String.format("searchTerm%02d", i);
            value = AppTools.readConfigurationString(context, key );

            configs.put(key, value);
        }
        return configs;
    }


    /**
     * Schreibe Suchworte in interne Datenspeicher
     * @param configs - HashMap mit Suchworten
     * @param context
     */
    public static void saveSearchTermsConfigs(HashMap<String, String> configs, Context context) {
        String key, value;

        for(int i=0; i<SERACHTERMS_COUNT; i++ ) {
            key = String.format("searchTerm%02d", i);
            value = configs.get(key);

            AppTools.saveConfigurationString(context, key, value );
        }
    }


    public static String getServiceURLBlitzer() {
        // in Constants.java
        // String FFH_BLITZER_URL = "https://modul.shop/blitzer10meldungen.html";
        // String FFH_BLITZER_URL_DEV = "https://www.ffh.de/verkehr/blitzer.html";
        String ret;

        if( DEVELOPMENT_MODE==true ) {
            ret = FFH_BLITZER_URL_DEV;
        } else {
            ret = FFH_BLITZER_URL;
        }
        return ret;

    }
    public static String getServiceURLTraffic() {
        // in Constants.java
//        String FFH_TRAFFIC_URL = "https://modul.shop/ffh_verkehr.html";
//        String FFH_TRAFFIC_URL_DEV = "https://www.ffh.de/verkehr/staupilot.html";
        String ret;

        if( DEVELOPMENT_MODE==true ) {
            ret = FFH_TRAFFIC_URL_DEV;
        } else {
            ret = FFH_TRAFFIC_URL;
        }
        return ret;

    }


    public static String actualTime() {
        // Aktuelle Zeit abrufen
        LocalTime now = LocalTime.now();

        // Formatieren als HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);

        return formattedTime;
    }
}
