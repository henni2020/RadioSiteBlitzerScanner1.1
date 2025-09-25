package com.example.radiositeblitzerscanner11.tools;

import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import android.util.Log;

/**
 * Blitzermeldung
 * Speichert Text der Meldung und kann nach Suchworten durchsuchen
 *  
 */
public class BlitzerMessage {
//	protected String headline="";
	protected final int MAXSUBLINES = 10;
	protected String[] sublinesArr = new String[MAXSUBLINES];
	protected int sublinesCount = 0;

	
	/**
	 * Hat noch keine Funktion
	 */
	public boolean marker = false;

	/**
	 * Std. Konstruktor
	 */
	public BlitzerMessage() {
		super();
	}

//	public FlashMessage( String headline ) {
//		this.headline = headline;
//	}

	/**
	 * Zeile für Meldungstext hinzufügen
	 * 
	 * @param subline	Meldungszeile
	 */
	public void addSubline(String subline) {
		if (sublinesCount < MAXSUBLINES) {
			this.sublinesArr[sublinesCount++] = subline;
		}
	}

	/**
	 * Meldungszeilen zurück geben
	 * 
	 * @return	Texte
	 */
	public String getTexts() {
//		String result = this.headline + "\n";
		String result = "";
		for (int i = 0; i < this.sublinesCount; i++) {
			result += this.sublinesArr[i] + "\n";
		}
		return result;
	}

	/**
	 * Suche in der Meldung ob eines d. übergebenen Suchworte enthalten ist
	 * 
	 * @param searchWordArr		Suchworte als Array
	 * @return true=gefunden
	 */


	/**
	 * Finde wenigstens ein Wort aus dem Suchwort-Array
	 * @param searchWordArr
	 * @return true wenn etwas gefunden
	 */
	public boolean findSearchWords(String[] searchWordArr) {
		String sw, text = this.getTexts().toLowerCase();
		String[] swArr;
		boolean result = false;

		for (int i = 0; i < searchWordArr.length; i++) {    // Suchworte d. Reihe nach
			if (searchWordArr[i] == null) break;
			sw = searchWordArr[i].toLowerCase();            // alle klein

			swArr = sw.split("\\s");                    // mehrere Suchworte in einem?
			if (swArr.length > 1) {
				result = findAllSearchWords(swArr);            // such sie alle
				if (result == true) {                            // wenn was gefunden
					break;                                    //   weitere Suche erübrigt sich
				}
			} else {                                        // nur ein Suchwort
				if (text.contains(sw)) {
					result = true;
					break;                                    //   weitere Suche erübrigt sich
				}
			}

			//System.out.println("res:"+result);
		}
		return result;
	}

	/**
	 * Finde alle Worte aus dem Suchwort-Array
	 * @param searchWordArr
	 * @return true wenn etwas gefunden
	 */
	public boolean findAllSearchWords (String[]searchWordArr){
		String text = this.getTexts().toLowerCase();
		String sw;
		boolean result = true;
		//System.out.println("findSearchWords: text:"+text);
		//Log.d("blitz", "Suchtext:"+text+"\n" );
		if (searchWordArr.length == 0) {        // leeres Array ungültig
			result = false;
		}

		for (int i = 0; i < searchWordArr.length; i++) {    // d. Reihe nach alle Suchworte
			if (searchWordArr[i] == null) break;
			sw = searchWordArr[i].toLowerCase();
			//System.out.println("findSearchWords: sw:"+searchWordArr[i]);
			//Log.d("blitz", "Suchwort:"+sw+" contains: "+text.contains(sw)+"\n" );

			//if( text.contains(searchWordArr[i]) ) {
			if (!text.contains(sw)) {                        // wenn nicht gefunden
				result = false;                                //   alles ungültig
				break;                                        //   weitere Suche erübrigt sich
			}
		}

		//System.out.println("res:"+result);
		return result;
	}


}
