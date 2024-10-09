package org.hbrs.se1.ws24.uebung1.control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Factory-Klasse zur Erzeugung von verschiedenen Translator-Objekten
 */
public final class TranslatorFactory {

  // privater Konstruktor, um Instanziierung zu verhindern
  private TranslatorFactory () { }

  /**
   * Erzeugt ein Translator-Objekt des uebergebenen Translator-Typen
   * @param type Der zu erzeugende Translator-Typ
   * @return Das erzeugte Translator-Objekt
   * @throws IllegalStateException wenn ein ungueltiger Translator-Typ uebergeben wurde
   */
  public static Translator createTranslator (TranslatorType type) {
    switch (type) {
      case GERMAN:
        GermanTranslator translator = new GermanTranslator();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM/yyyy");
        translator.setDate(LocalDate.now().format(formatter));
        return translator;
      default:
        throw new IllegalArgumentException("Kein gueltiger Translator-Typ: " + type);
    }
  }

  /**
   * Enum, um verschiedene Translatoren darzustellen, bisher nur einsprachig
   */
  public enum TranslatorType { GERMAN }

}
