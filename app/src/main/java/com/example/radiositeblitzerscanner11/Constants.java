package com.example.radiositeblitzerscanner11;

public interface Constants
{
    Boolean DEVELOPMENT_MODE = true;


    //String FFH_BLITZER_URL = "https://modul.shop/blitzer.html";
    //String FFH_BLITZER_URL = "https://modul.shop/blitzer2.html";

    // Service URLs
    final String FFH_BLITZER_URL_DEV = "https://modul.shop/blitzer.html";
    final String FFH_TRAFFIC_URL_DEV = "https://modul.shop/ffh_verkehr.html";

    // Service URLs für Tests
    final String FFH_BLITZER_URL = "https://www.ffh.de/verkehr/blitzer.html";
    final String FFH_TRAFFIC_URL = "https://www.ffh.de/verkehr/staupilot.html";

    String LOG_TAG = "app_blitzer";
    final int ALARM1_UNIQUE_ID = 4711;
    final int ALARM2_UNIQUE_ID = 4712;
    //final int NOTIFICATION_ID = 4713;

    String CONFIG_BLITZER = "BlitzerApp";

    final int SERACHTERMS_COUNT = 10; // Anzahl Suchworte

    Boolean TEST_REPEAT_MODE = false;

}