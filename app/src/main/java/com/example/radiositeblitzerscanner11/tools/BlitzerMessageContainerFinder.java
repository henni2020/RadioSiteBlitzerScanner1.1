package com.example.radiositeblitzerscanner11.tools;

/**
 * Erweitert den BlitzerMessageContainer um Suchfunktionen
 */
public class BlitzerMessageContainerFinder extends BlitzerMessageContainer {
	protected final int MAXSEARCHWORD = 30;
	protected String[] searchWordArr = new String[MAXSEARCHWORD];
	protected int searchWordCount = 0;

	public int foundCounter=0;

	/**
	 * Suchwort hinzu fügen
	 * 
	 * @param sw	Suchwort
	 */
	public void addSearchWord(String sw) {
		if (searchWordCount < MAXSEARCHWORD) {
			this.searchWordArr[searchWordCount++] = sw;
		}
	}

	/**
	 * Gibt alle gespeicherten Suchworte zurück
	 * 
	 * @return	Suchworte
	 */
	public String getSearchWords() {
		String result = "";
		for (int i = 0; i < this.searchWordCount; i++) {
			result += this.searchWordArr[i] + "\n";
		}
		return result;
	}

	/**
	 * Gibt die Texte aller Blitzermeldungen zurück
	 * in denen ein Suchwort gefunden wurde
	 * @return
	 */
	public String findSearchWords() {
		String result="";
		int counter=0;
		int counterTotal=0;

		for(int i = 0; i< flashMessageArr.length; i++){
			if( flashMessageArr[i]==null )	break;
			counterTotal++;
			
			if( flashMessageArr[i].findSearchWords(searchWordArr) ) { // Suchwort gefunden
				result += flashMessageArr[i].getTexts()+"\n\n";
				counter++;
			}
		}

		result = "["+counter + " messages of "+counterTotal+" messages]\n\n" + result;

		this.foundCounter = counter;
		return result;
	}	
}

