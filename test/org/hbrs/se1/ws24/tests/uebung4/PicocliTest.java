package org.hbrs.se1.ws24.exercises.uebung4;

import java.util.PriorityQueue;
import java.util.Queue;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

public class PicocliTest {
  
  public void init () { }

  @Nested
  class CommandTest { 

    @Test
    public void testEnterCmd () { }
    
    @Test
    public void testStoreCmd () { }
    
    @Test
    public void testLoadCmd () { }
    
    @Nested
    class DumpTest {
      
      @Test
      public void testDumpCmd () { }

      @Test
      public void testProjectOpt () { }

    }

  }
  
  @Nested
  class BoundsTest {
    static Queue<Byte> posQueue;
    static Queue<Byte> negQueue;

    @BeforeAll
    public static void initBounds () {
      posQueue = new PriorityQueue<>();
      for (byte i = 1; i < 6; i++) posQueue.offer(i);

      negQueue = new PriorityQueue<>();
      negQueue.offer((byte)0);
      negQueue.offer((byte)6);
    }

    public void testBounds (Function<Byte,ReadOnly> constructor) {
      posQueue.forEach(i -> assertEquals(i, constructor.apply(i).val()));
      negQueue.forEach(i -> assertThrows(
            IllegalArgumentException.class,
            () -> constructor.apply(i)));
    }

    @Test
    public void testMehrwert () { testBounds((i) -> new Mehrwert(i)); }
    
    @Test
    public void testStrafe () { testBounds((i) -> new Strafe(i)); }
    
    @Test
    public void testRisiko () { testBounds((i) -> new Risiko(i)); }
    
    @Test
    public void testAufwand () { testBounds((i) -> new Aufwand(i)); }

  } 

  @Test
  public void testPrio () {
    Prio prio = new Prio(
        new Mehrwert(1),
        new Strafe(1),
        new Risiko(1),
        new Aufwand(1));

    assertEquals(1.0f, prio.val().floatValue());

    prio = new Prio(
        new Mehrwert(1),
        new Strafe(2),
        new Risiko(3),
        new Aufwand(4));

    assertEquals(0.42857143f, prio.val().floatValue());

    prio = new Prio(
        new Mehrwert(5),
        new Strafe(5),
        new Risiko(5),
        new Aufwand(5));

    assertEquals(1.0f, prio.val().floatValue());
  }
  
}
