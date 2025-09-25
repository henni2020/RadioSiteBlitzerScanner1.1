package com.example.radiositeblitzerscanner11.tools;

import static com.example.radiositeblitzerscanner11.Constants.DEVELOPMENT_MODE;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class NextAppointmentTime {

    /**
     * Rechnet Millisek-Unixtime [hour]:[minute] Uhr
     * für nächstes Vorkommen. Wenn heute vor [hour]:[minute] Uhr dann Return
     * heute [hour]:[minute] Uhr. Falls 4nach [hour]:[minute] Uhr dann Return
     * morgen [hour]:[minute] Uhr
     *
     * @param hour
     * @param minute
     * @return Unixtime in ms
     */
    public static long getNextUnixTime(int hour, int minute) {
        // Aktuelles Datum und Zeit abrufen
        LocalDate today = LocalDate.now();
        LocalTime targetTime = LocalTime.of(hour, minute);
        LocalDateTime targetDateTime = LocalDateTime.of(today, targetTime);

        // Falls die aktuelle Zeit bereits später ist, Termin auf morgen setzen
        if (LocalDateTime.now().isAfter(targetDateTime)) {
            targetDateTime = targetDateTime.plusDays(1);
        }

        // Unix-Zeit (Sekunden seit 1970) berechnen
        return targetDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }


    /**
     * Rechnet Millisek-Unixtime für morgen um [hour]:[minute] Uhr
     *
     * Bei DEVELOPMENT_MODE = true rechnet er die Zeit in 2 Minuten
     *
     * @param hour
     * @param minute
     * @return Unixtime in ms
     */
    public static long getTomorrowUnixTime(int hour, int minute) {
        LocalDateTime targetDateTime;
        LocalDate today = LocalDate.now();

        if( DEVELOPMENT_MODE ) {
            // Aktuelles Datum und Zeit abrufen
            LocalTime targetTime = LocalTime.now(); // dev prov

            targetDateTime = LocalDateTime.of(today, targetTime);

            targetDateTime = targetDateTime.plusMinutes(2); // dev prov
        } else { // productive mode
            // Aktuelles Datum und Zeit abrufen
            LocalTime targetTime = LocalTime.of(hour, minute);

            targetDateTime = LocalDateTime.of(today, targetTime);

            targetDateTime = targetDateTime.plusDays(1);
        }

        // Unix-Zeit (Sekunden seit 1970) berechnen
        return targetDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /*
    public static void main(String[] args) {
        int hour = 8;
        int minute = 30;
        long unixTime = getNextUnixTime(hour, minute);

        System.out.println("Nächster Termin um " + hour + ":" + minute + " hat Unix-Zeit: " + unixTime);
    }

     */
}
