package org.hbrs.se1.ws24.exercises.uebung3.persistence;

import org.hbrs.se1.ws24.exercises.uebung2.ConcreteMember;
import org.hbrs.se1.ws24.exercises.uebung2.Container;
import org.hbrs.se1.ws24.exercises.uebung2.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;

public class PersistenceTest  {

  private static Member[] members = new Member[5];

  private static Container container = Container.INSTANCE;

  @BeforeAll
  public static void setup () {
    for (int i=0; i<5; i++) { members[i] = new ConcreteMember(); }

  }

  @BeforeEach
  public void init () { container.clear(); }

  @Test
  public void testNoStrategy () {
    PersistenceException store = assertThrows(
        PersistenceException.class,
        () -> container.store());
    PersistenceException load = assertThrows(
        PersistenceException.class,
        () -> container.load());
    
    assertEquals(
        load.getExceptionTypeType(),
        PersistenceException.ExceptionType.NoStrategyIsSet);
    assertEquals(
        store.getExceptionTypeType(),
        PersistenceException.ExceptionType.NoStrategyIsSet);
  }

  @Test
  public void testStrategyMongoDB() {
    container.setPersistenceStrategy(new PersistenceStrategyMongoDB<>());
    
    PersistenceException store = assertThrows(
        PersistenceException.class,
        () -> container.store());
    PersistenceException load = assertThrows(
        PersistenceException.class,
        () -> container.load());

    assertEquals(
        load.getExceptionTypeType(),
        PersistenceException.ExceptionType.ImplementationNotAvailable);
    assertEquals(
        store.getExceptionTypeType(),
        PersistenceException.ExceptionType.ImplementationNotAvailable);
  }

  @Test
  public void testWrongFileLocation (@TempDir Path tmpPath) {
    PersistenceStrategyStream<Member> streamStrategy = new PersistenceStrategyStream<>();
    streamStrategy.setLocation(tmpPath.toString());

    container.setPersistenceStrategy(streamStrategy);
    
    PersistenceException store = assertThrows(
        PersistenceException.class,
        () -> container.store());
    PersistenceException load = assertThrows(
        PersistenceException.class,
        () -> container.load());

    assertEquals(
        load.getExceptionTypeType(),
        PersistenceException.ExceptionType.LoadingFailed);
    assertEquals(
        store.getExceptionTypeType(),
        PersistenceException.ExceptionType.SavingFailed);
  }

  @Test
  public void testRoundTrip (@TempDir Path tmpPath) throws ContainerException, PersistenceException {
    for (Member member : members) { container.addMember(member); }

    assertEquals(container.size(), 5);

    PersistenceStrategyStream<Member> streamStrategy = new PersistenceStrategyStream<>();
    
    Path tmpPfad = tmpPath.resolve("testList.ser");
    streamStrategy.setLocation(tmpPfad.toString());

    container.setPersistenceStrategy(streamStrategy);
    container.store();

    assertEquals(container.size(), 5);

    for (Member member : members) { container.contains(member); }

    container.deleteMember(members[4].getID());
    ConcreteMember.freeID(members[4]);
    container.addMember(members[4]);

    container.load();
    
    assertEquals(container.size(), 5);

    for (Member member : members) { container.contains(member); }
  }

}
