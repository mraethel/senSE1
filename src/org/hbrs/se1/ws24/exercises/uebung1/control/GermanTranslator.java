package org.hbrs.se1.ws24.uebung1.control;

public class GermanTranslator implements Translator {

  public String date = "Okt/2024"; // Default-Wert

  /**
   * Methode zur Übersetzung einer Zahl in eine String-Repraesentation
   */
  public String translateNumber (int number) {
    String[] nummern = {"eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn"};
    
    try {
      return nummern[number - 1];
    } catch (Exception e) {
      return "Übersetzung der Zahl " + number + " nicht möglich (Version " + this.version + ")";
    }
  }

  /**
   * Objektmethode der Klasse GermanTranslator zur Ausgabe einer Info.
   */
  public void printInfo () { System.out.println( "GermanTranslator v1.9, erzeugt am " + this.date ); }

  /**
   * Setzen des Datums, wann der Uebersetzer erzeugt wurde (Format: Monat/Jahr (Beispiel: "Okt/2024"))
   * Das Datum sollte system-intern durch eine Factory-Klasse gesetzt werden und nicht von externen View-Klassen
   */
  public void setDate (String date) { this.date = date; }

}
