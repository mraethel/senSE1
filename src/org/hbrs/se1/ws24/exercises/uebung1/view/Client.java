package org.hbrs.se1.ws24.uebung1.view;

import org.hbrs.se1.ws24.uebung1.control.Translator;
import org.hbrs.se1.ws24.uebung1.control.TranslatorFactory;

public class Client {

  /**
   * Methode zur Ausgabe einer Zahl auf der Console
   * (auch bezeichnet als CLI, Terminal)
   *
   */
  void display (int aNumber) {
    Translator translator = TranslatorFactory.createTranslator(TranslatorFactory.TranslatorType.GERMAN);
    String ergebnis = translator.translateNumber(aNumber);

    System.out.println("Das Ergebnis der Berechnung: " + ergebnis);
  }
}
