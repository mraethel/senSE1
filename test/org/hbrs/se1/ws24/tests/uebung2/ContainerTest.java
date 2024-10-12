package org.hbrs.se1.ws24.tests.uebung2;

import org.hbrs.se1.ws24.uebung2.*;

import org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Der ContainerTest bildet den in der Übung behandelten Automaten ab.
 */
public class ContainerTest {

  private static Member member1;
  private static Member member2;

  private static Container container;

  private static ContainerException exception1;
  private static ContainerException exception2;

  @BeforeEach
  public static void init () {
    member1 = new ConcreteMember();
    member2 = new ConcreteMember();

    container = new Container();

    exception1 = new ContainerException(member1.getID());
    exception2 = new ContainerException(member2.getID());
  }

  @Test
  public void testStage0 () {
    assertAll("testStage0Members",
      () -> assertFalse(container.contains(member1)),
      () -> assertFalse(container.contains(member2)));

    assertAll("testStage0Size",
      () -> assertEquals(0, container.size()));

    assertAll("testStage0Deletion",
      () -> assertNotNull(container.deleteMember(member1.getID())),
      () -> assertNotNull(container.deleteMember(member2.getID())));
  }

  @Nested
  class Stage1 {

    @BeforeEach
    public void addMember1 () {
      container.addMember(member1);
    }

    @Test
    public void testStage1 () {
      assertAll("testStage1Members",
        () -> assertTrue(container.contains(member1)),
        () -> assertFalse(container.contains(member2)));

      assertAll("testStage1Size",
        () -> assertEquals(1, container.size()));

      assertAll("testStage1Duplicate",
        () -> assertThrows(ContainerException.class,
          () -> container.addMember(member1)));

      assertAll("testStage1Deletion",
        () -> assertNotNull(container.deleteMember(member2.getID())));

      assertAll("testStage0FromStage1",
        () -> assertNull(container.deleteMember(member1.getID())),
        () -> testStage0());

    }

    @Nested
    class Stage2 {

      @BeforeEach
      public void addMember2 () {
        container.addMember(member2);
      }

      @Test
      public void testStage2 () {
        assertAll("testStage2Members",
          () -> assertTrue(container.contains(member1)),
          () -> assertTrue(container.contains(member2)));

        assertAll("testStage2Size",
          () -> assertEquals(2, container.size()));

        assertAll("testStage2Duplicate",
          () -> assertThrows(ContainerException.class,
            () -> container.addMember(member1)),
          () -> assertThrows(ContainerException.class,
            () -> container.addMember(member2)));

        assertAll("testStage1FromStage2",
          () -> assertNull(container.deleteMember(member2.getID())),
          () -> testStage1());
      }

    }

  }

}
