package org.hbrs.se1.ws24.tests.uebung2;

import org.hbrs.se1.ws24.exercises.uebung2.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Der ContainerTest bildet den in der Übung behandelten Automaten ab.
 */
public class ContainerTest {

  private static Member member1;
  private static Member member2;

  private static Container container = Container.INSTANCE;

  private static ContainerException exception1;
  private static ContainerException exception2;

  @BeforeEach
  public void init () throws ContainerException {
    container.clear();

    member1 = new ConcreteMember();
    member2 = new ConcreteMember();

    exception1 = new ContainerException(member1.getID());
    exception2 = new ContainerException(member2.getID());
  }

  @Test
  public void testStage0 () {
    testStage0Members();
    testStage0Size();
    testStage0Deletion();
  }

  @Test
  public void testStage0Members () {
    assertFalse(container.contains(member1));
    assertFalse(container.contains(member2));
  }

  @Test
  public void testStage0Size () {
    assertEquals(0, container.size());
  }

  @Test
  public void testStage0Deletion () {
    assertNotNull(container.deleteMember(member1.getID()));
    assertNotNull(container.deleteMember(member2.getID()));
  }

  @Nested
  class Stage1 {

    @BeforeEach
    public void addMember1 () {
      try { container.addMember(member1); }
      catch(ContainerException e) { throw new RuntimeException("Stage1: ContainerException!"); };
    }

    @Test
    public void testStage1() {
      testStage1Members();
      testStage1Size();
      testStage1Duplicate();
      testStage1Deletion();
      testStage0FromStage1();
    }

    @Test
    public void testStage1Members () {
      assertTrue(container.contains(member1));
      assertFalse(container.contains(member2));
    }

    @Test
    public void testStage1Size () {
      assertEquals(1, container.size());
    }

    @Test
    public void testStage1Duplicate () {
      assertThrows(ContainerException.class,
        () -> container.addMember(member1));
    }

    @Test
    public void testStage1Deletion () {
      assertNotNull(container.deleteMember(member2.getID()));
    }

    @Test
    public void testStage0FromStage1 () {
      assertNull(container.deleteMember(member1.getID()));
      testStage0();
    }

    @Nested
    class Stage2 {

      @BeforeEach
      public void addMember2 () {
        try { container.addMember(member2); }
        catch(ContainerException e) { throw new RuntimeException("Stage2: ContainerException!"); };
      }

      @Test
      public void testStage2 () {
        testStage2Members();
        testStage2Size();
        testStage2Duplicate();
        testStage1FromStage2();
      }

      @Test
      public void testStage2Members () {
        assertTrue(container.contains(member1));
        assertTrue(container.contains(member2));
      }

      @Test
      public void testStage2Size () {
        assertEquals(2, container.size());
      }

      @Test
      public void testStage2Duplicate () {
        assertThrows(ContainerException.class,
          () -> container.addMember(member1));
        assertThrows(ContainerException.class,
          () -> container.addMember(member2));
      }

      @Test
      public void testStage1FromStage2 () {
        assertNull(container.deleteMember(member2.getID()));
        testStage1();
      }

    }

  }

}
