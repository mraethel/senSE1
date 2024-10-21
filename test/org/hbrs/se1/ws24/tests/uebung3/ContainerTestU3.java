package org.hbrs.se1.ws24.tests.uebung3;

import org.hbrs.se1.ws24.exercises.uebung2.ConcreteMember;
import org.hbrs.se1.ws24.exercises.uebung2.Container;
import org.hbrs.se1.ws24.exercises.uebung2.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.*;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ContainerTestU3  {

  private static Container container;


  @BeforeAll
  public static void init() {
    container = Container.INSTANCE;
    container.clear();
  }

  @Test
  public void testNoStrategy() {
    container.setPersistenceStrategy(null);
    PersistenceException store = assertThrows(PersistenceException.class, () -> container.store());
    PersistenceException load = assertThrows(PersistenceException.class, () -> container.load());
    assertEquals(load.getExceptionTypeType(), PersistenceException.ExceptionType.NoStrategyIsSet);
    assertEquals(store.getExceptionTypeType(), PersistenceException.ExceptionType.NoStrategyIsSet);

  }

  @Test
  public void testStrategyMongoDB() {
    container.setPersistenceStrategy(new PersistenceStrategyMongoDB<>());
    PersistenceException store = assertThrows(PersistenceException.class, () -> container.store());
    PersistenceException load = assertThrows(PersistenceException.class, () -> container.load());

    assertEquals(load.getExceptionTypeType(), PersistenceException.ExceptionType.ImplementationNotAvailable);
    assertEquals(store.getExceptionTypeType(), PersistenceException.ExceptionType.ImplementationNotAvailable);
  }

  @Test
  public void testWrongFileLocation(@TempDir Path tmp) {
    PersistenceStrategyStream<Member> streamStrategy = new PersistenceStrategyStream<>();
    streamStrategy.setLocation(tmp.toString());
    container.setPersistenceStrategy(streamStrategy);
    PersistenceException store = assertThrows(PersistenceException.class, () -> container.store());
    PersistenceException load = assertThrows(PersistenceException.class, () -> container.load());

    assertEquals(load.getExceptionTypeType(), PersistenceException.ExceptionType.LoadingFailed);
    assertEquals(store.getExceptionTypeType(), PersistenceException.ExceptionType.SavingFailed);
  }

  @Test
  public void testRoundTrip(@TempDir Path tmp) throws ContainerException, PersistenceException {
    for (int i = 0; i < 5; i++) {
      container.addMember(new ConcreteMember(i));
    }
    assertEquals(5, container.size());
    PersistenceStrategyStream<Member> streamStrategy = new PersistenceStrategyStream<>();
    container.setPersistenceStrategy(streamStrategy);
    Path tmpPfad = tmp.resolve("testList.ser");
    streamStrategy.setLocation(tmpPfad.toString());

    container.store();
    assertEquals(5, container.size());
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(0)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(1)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(2)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(3)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(4)));


    container.deleteMember(4);
    container.addMember(new ConcreteMember(4));


    container.load();
    assertEquals(container.size(), 5);
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(0)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(1)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(2)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(3)));
    assertThrows(ContainerException.class, () -> container.addMember(new ConcreteMember(4)));

    List<Member> members = container.getCurrentList();
    for (int i = 0; i < 5; i++) {
      assertEquals(i, members.get(i).getID());
    }
  }
}
