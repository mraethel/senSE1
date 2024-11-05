package org.hbrs.se1.ws24.exercises.uebung4;

import org.hbrs.se1.ws24.exercises.uebung2.Container;
import org.hbrs.se1.ws24.exercises.uebung2.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws24.exercises.uebung4.UserStory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.Collections;

@Command(name = "main",
    subcommands = { Enter.class,
                    Store.class,
                    Load.class,
                    Dump.class,
                    CommandLine.HelpCommand.class },
    description = "Verwaltung von User-Stories.")
public class Main {

  public static void main(String[] args) {
    Container.INSTANCE.setPersistenceStrategy(new PersistenceStrategyStream<>());

    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);
  }

}

@Command(name = "enter",
    description = "Eingabe einer User-Story.")
class Enter implements Runnable {
  
  @Parameters(index = "0",
      description = "Titel der User-Story.")
  private String titel;

  @Parameters(index = "1",
      description = "akzeptanz")
  private String akzeptanz;

  @Parameters(index = "2",
      converter = MehrwertConverter.class,
      description = "mehrwert")
  private Mehrwert mehrwert;

  @Parameters(index = "3",
      converter = StrafeConverter.class,
      description = "strafe")
  private Strafe strafe;

  @Parameters(index = "4",
      converter = RisikoConverter.class,
      description = "risiko")
  private Risiko risiko;

  @Parameters(index = "5",
      converter = AufwandConverter.class,
      description = "Geschätzter Aufwand der User-Story.")
  private Aufwand aufwand;

  @Parameters(index = "6",
      description = "Zugehöriges Projekt.")
  private String projekt;

  @Override
  public void run() {
    try {
      Container.INSTANCE.addMember(new UserStory(
          titel,
          akzeptanz,
          mehrwert,
          strafe,
          risiko,
          aufwand,
          projekt));
    } catch (ContainerException e) {
      System.out.println("Es existiert bereits eine User-Story mit der ID " + e.getMessage() + "!");
    };
  }
}

@Command(name = "store",
    description = "Speichern von geladenen User-Stories.")
class Store implements Runnable {

  @Override
  public void run() {
    try {
      Container.INSTANCE.store();
    } catch (PersistenceException e) {
      System.out.println(e.getMessage());
    }
  }
}

@Command(name = "load",
    description = "Laden von User-Stories.")
class Load implements Runnable {

  @Override
  public void run() {
    try {
      Container.INSTANCE.load();
    } catch (PersistenceException e) {
      System.out.println(e.getMessage());
    }
  }
}

@Command(name = "dump",
    description = "Ausgabe geladener User-Stories.")
class Dump implements Runnable {

  @Override
  public void run() {
    List<Member> sortedContainer = Container.INSTANCE.getCurrentList();
    Collections.sort(sortedContainer);
    sortedContainer.forEach(System.out::println);
  }
}