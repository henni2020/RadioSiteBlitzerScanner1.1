package com.example.radiositeblitzerscanner11.tools;

/**
 * Speichert BliterMessage Objekte und durchsucht sie
 * 
 */
public class BlitzerMessageContainer implements ToolConstants {
//	protected String headline="";
	//private final int MAXFLMESSAGES = 30;
	protected BlitzerMessage[] flashMessageArr = new BlitzerMessage[MAXFLMESSAGES];
	private int flashMessageCount = 0;

	/**
	 * Std. Konstruktor
	 */
	public BlitzerMessageContainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Blitzermeldung einfügen
	 * 
	 * @param 	bm Blitzermeldung
	 */
	public void addFlashMessage(BlitzerMessage bm) {
		if (flashMessageCount < MAXFLMESSAGES) {
			bm.marker = false;
			this.flashMessageArr[flashMessageCount++] = bm;
		}
	}

	/**
	 * Alle Blitzermeldungstexte als einen Text zurück geben
	 * 
	 * @return	Texte
	 */
	public String getTexts() {
		String result = this.statString();
		for (int i = 0; i < this.flashMessageCount; i++) {
			result += this.flashMessageArr[i].getTexts() + "\n";
		}
		return result;
	}

	private String statString() {
		return "["+this.flashMessageCount + " Messages]\n\n";
	}

	/**
	 * Die Marker aller gespeicherten Blitzermeldungen zurück setzen
	 */
	protected void resetMarkers() {
		for (int i = 0; i < this.flashMessageCount; i++) {
			this.flashMessageArr[i].marker = false;
		}

	}

}


