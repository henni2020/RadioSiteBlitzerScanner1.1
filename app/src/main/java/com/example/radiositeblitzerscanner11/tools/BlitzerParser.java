package com.example.radiositeblitzerscanner11.tools;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Kann eine HTML-Seite herunterladen und nach Meldungen (Blitzer oder Verkehr)
 * parsen.
 */
public class BlitzerParser extends HttpReader {
    public BlitzerParser(String reqURL) {
        super(reqURL);
    }

    /**
     * BlitzerMessageContainerFinder parseHtmlBlitzerToMessageContainer()
     *
     * Finde Blitzermeldungen
     * @return BlitzerMessageContainerFinder mit allen gefundenen Meldungen
     */
    protected BlitzerMessageContainerFinder parseHtmlBlitzerToMessageContainer() {
        BlitzerMessageContainerFinder resultContainer = new BlitzerMessageContainerFinder();

        String seq;
        // String result="";
        // Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")  this.htmlText="+this.htmlText+"\n" );

        /*
        <td>
            <h5>In Hattersheim</h5>
            <p>auf der Voltastraße, kurz vor dem Kreisel.</p>
        </td>
         */
        Matcher matcher = Pattern.compile("<td>.*?<h5>(.*?)</h5>.*?</td>").matcher(this.htmlText);
        while (matcher.find()) {
            BlitzerMessage fm = new BlitzerMessage();
            fm.addSubline(matcher.group(1));  //  <h5>In Hattersheim</h5>

            seq = matcher.group(0);

            Matcher matcher2 = Pattern.compile("<p>(.*?)</p>").matcher(seq);
            while (matcher2.find()) {
                // <p>auf der Voltastraße, kurz vor dem Kreisel.</p>
                fm.addSubline(matcher2.group(1));
            }
            resultContainer.addFlashMessage(fm);
        }
        return resultContainer;
    }


    /**
     * BlitzerMessageContainerFinder parseHtmlTrafficMessagesToMessageContainer()
     *
     * Finde Verkehrsmeldungen
     * @return BlitzerMessageContainerFinder mit allen gefundenen Meldungen
     */
    protected BlitzerMessageContainerFinder parseHtmlTrafficMessagesToMessageContainer() {

        BlitzerMessageContainerFinder resultContainer = new BlitzerMessageContainerFinder();
        String seq;

        /*
        <td class="iconCol">
            <span class="trafficIcon streetTypeA">A6</span>
        </td>
        <td>
            <h5>Mannheim  -  Heilbronn</h5>
            <p>Zwischen Kreuz Mannheim und Mannheim/Schwetzingen 5 km Stau.</p>
            <p class="legalnote">Stand: 11:56 Uhr</p>
        </td>
         */
        Matcher matcher =
                Pattern.compile("<td class=\"iconCol\">(.*?)</td>.*?<td>.*?<h5>(.*?)</h5>(.*?)</td>").matcher(this.htmlText);
        while (matcher.find()) {
//            Log.d(LOG_TAG, "matcher.group(0)="+matcher.group(0)+"\n" );
//            Log.d(LOG_TAG, "matcher.group(1)="+matcher.group(1)+"\n" );
//            Log.d(LOG_TAG, "matcher.group(2)="+matcher.group(2)+"\n" );
//            Log.d(LOG_TAG, "matcher.group(3)="+matcher.group(3)+"\n" );
            // result += "deb:"+matcher.group(1)+"\n";
            // System.out.println( "g1" + matcher.group(1) );
            BlitzerMessage fm = new BlitzerMessage();

            Matcher matcher2 = Pattern.compile("<span class=\"trafficIcon streetTypeA\">(.*?)</span>").matcher( matcher.group(0) );
            if (matcher2.find()) {
//                Log.d(LOG_TAG, "Add: matcher 2.1: matcher.group(1)="+matcher2.group(1)+"\n" );
                fm.addSubline(matcher2.group(1));
            }

//            Log.d(LOG_TAG, "Add: matcher 2.1: matcher.group(1)="+matcher.group(2)+"\n" );
            fm.addSubline(matcher.group(2)); // <h5>Mannheim  -  Heilbronn</h5>

            seq = matcher.group(3);

            matcher2 = Pattern.compile("<p>(.*?)</p>").matcher(seq);
            while (matcher2.find()) {
//                Log.d(LOG_TAG, "Add matcher 2.2: matcher.group(1)="+matcher2.group(1)+"\n" );
                fm.addSubline(matcher2.group(1));
            }
//            Log.d(LOG_TAG, "------------------------------------------\n" );
            resultContainer.addFlashMessage(fm);
        }
        // System.out.print(result);
        return resultContainer;
    }


    /**
     * Lädt Code herunter
     * parst ihn nach Meldungen
     * und gibt komplette Meldungsliste zurück als String
     * @return alle Meldungen
     */
    public String parseCode()  {
        if( this.htmlText==null || this.htmlText.isEmpty() ) {
            if( this.httpRequest()<0 ) {
                return this.errorText;
            }
        }

        return parseHtmlBlitzerToMessageContainer().getTexts();

    }
}
