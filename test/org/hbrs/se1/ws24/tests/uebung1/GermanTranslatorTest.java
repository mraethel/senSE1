package org.hbrs.se1.ws24.tests.uebung1;

import static org.junit.jupiter.api.Assertions.*;
import org.hbrs.se1.ws24.exercises.uebung1.control.Translator;
import org.hbrs.se1.ws24.exercises.uebung1.control.TranslatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GermanTranslatorTest {

  private static Translator germanTranslator;

  @BeforeAll
  public static void setUp () {
    germanTranslator = TranslatorFactory.createTranslator(TranslatorFactory.TranslatorType.GERMAN);
  }

  @Test
  public void testPos () {
    assertEquals("drei", germanTranslator.translateNumber(3));
    assertEquals("sechs", germanTranslator.translateNumber(6));
    assertEquals("neun", germanTranslator.translateNumber(9));

    // Grenzwerte
    assertEquals("eins" , germanTranslator.translateNumber(1));
    assertEquals("zehn", germanTranslator.translateNumber(10));
  }

  @Test
  public void testNeg () {
    assertTrue(germanTranslator.translateNumber(0).contains("Version"));
    assertTrue(germanTranslator.translateNumber(11).contains("Version"));
    assertTrue(germanTranslator.translateNumber(-1).contains("Version"));
  }

}
