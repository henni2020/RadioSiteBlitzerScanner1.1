package com.example.radiositeblitzerscanner11.tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTML Seitentext einer URL einlesen
 * und intern speichern
 * HTML Text kann mit Getter abgerufen werden oder durch
 * Kindklassen weiterverarbeitet
 */
public class HttpReader  {

    protected String reqURL = null;
    protected String htmlText = null;
    protected String errorText = "";

    /**
     * Konstruktor
     * @param reqURL - URL die einzulesen ist
     */
    public HttpReader(String reqURL) {
        this.reqURL = reqURL;
    }

    /**
     * Getter f. URL
     * @return URL
     */
    public String getReqURL() {
        return reqURL;
    }

    /**
     * Getter f., aus URL, eingelesenen HTML Text
     * @return
     */
    public String getHtmlText() {
        return htmlText;
    }

    /**
     * Setter f. URL
     * @param reqURL
     */
    public void setReqURL(String reqURL) {
        this.reqURL = reqURL;
    }

    /**
     * Liest die konfigurierte URL ein. Der herunter geladene HTML-Text
     * kann mit getHtmlText() abgerufen werden.
     *
     * @return 0 - ok
     * @return -1 - Fehler
     */
    public int httpRequest() {
        if( this.reqURL==null || this.reqURL.isEmpty() ) {
            this.errorText = "No valid URL";
            return -1;
        }
//        Log.d(LOG_TAG, new Exception().getStackTrace()[0].getLineNumber() +" HttpReader.makeHttpRequest() \n" );

        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(this.reqURL);
            conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                //return result.toString().replace("/N/", System.getProperty("line.separator")); // /N/ on php makes a new line at the textview
                //return result.toString().replace("\n", System.getProperty("line.separator")); // /N/ on php makes a new line at the textview
                this.htmlText = result.toString();
                return 0; // ok
            } else {
                this.htmlText = this.errorText = "Fail (" + responseCode + ")";
                return -1;
            }
        } catch (IOException e) {
            this.htmlText = this.errorText = "Unable to connect";
            return -1;
        }

    }



}

