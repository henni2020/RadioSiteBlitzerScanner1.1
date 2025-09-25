package com.example.radiositeblitzerscanner11.tools;

/**
 * Kann HTML-Seiten herunter laden,
 * parsen nach Verkehrs und Blitzermeldungen
 * und filtern nach Stichworten.
 *
 * Verwaltet eine Liste Stichworte.
 */
public class BlitzerParserFilter extends BlitzerParser  implements ToolConstants {
    String[] searchTermsArr = new String[MAXFLMESSAGES];
    int searchTermsArrCounter = 0;
    protected int foundCounter=0;
    protected String resultTextLong;
    protected String resultTextShort;

    /**
     * Konstruktor
     * @param reqURL - URL die herunter geladen werden soll
     */
    public BlitzerParserFilter(String reqURL) {
        super(reqURL);
    }

    /**
     * Suchwort hinzufügen
     * @param sw - Suchwort
     */
    public void addSearchTerm(String sw) {
        if (sw.trim().isEmpty()) {
            return;
        }
        if (searchTermsArrCounter < MAXSEARCHWORD) {
            this.searchTermsArr[searchTermsArrCounter++] = sw;
        }
    }

    /**
     * Liste aller Suchworte zurück geben
     * @return String Suchworte
     */
    public String getSearchTerms() {
        String result = "";
        for (int i = 0; i < this.searchTermsArrCounter; i++) {
            result += this.searchTermsArr[i] + "\n";
        }
        return result;
    }


    /**
     * Lädt Code herunter
     * parst ihn nach Meldungen
     * und gibt nach Suchworten gefilterte Meldungsliste zurück als String
     * @return Meldungen nach Suchworten gefiltert
     */
    @Override
    public  String parseCode() {
        if( this.htmlText==null || this.htmlText.isEmpty() ) {
            if( this.httpRequest()<0 ) {
                return this.errorText;
            }
        }

        // add Words
        BlitzerMessageContainerFinder bmcf = this.parseHtmlBlitzerToMessageContainer();
        for (int i = 0; i < this.searchTermsArrCounter; i++) {
            bmcf.addSearchWord(this.searchTermsArr[i]);
        }

        //this.resultTextView.setText(this.htmlText);
        //bmcf.addSearchWord("Wetzlar");
        //bmcf.addSearchWord("Bad");

        //str = bmcf.getSearchWords();
        //this.resultTextView.setText(str);

        //str = this.getSearchTerms();
        //this.resultTextView.setText(str);

        //String str = this.getSearchTerms();
        //str += bmcf.findSearchWords();

        this.resultTextShort = bmcf.findSearchWords();
        this.resultTextLong = this.getSearchTerms() + this.resultTextShort;
        this.foundCounter = bmcf.foundCounter;

        //str = bmcf.getTexts();
        //this.resultTextView.setText(str);

        return this.resultTextLong;

    }


    public int getFoundCounter() {
        return foundCounter;
    }

    public String getResultTextLong() {
        return resultTextLong;
    }

    public String getResultTextShort() {
        return resultTextShort;
    }





}
