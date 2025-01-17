package org.hbrs.se1.ws24.exercises.uebung4;

import org.hbrs.se1.ws24.exercises.uebung2.Container;
import org.hbrs.se1.ws24.exercises.uebung2.ContainerException;
import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.MemberView;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.MemberType;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws24.exercises.uebung4.UserStory;

import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Supplier;

import java.io.PrintWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.shell.jline3.PicocliCommands.PicocliCommandsFactory;
import picocli.shell.jline3.PicocliCommands;

import org.jline.reader.LineReader;
import org.jline.reader.Parser;
import org.jline.reader.Binding;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.TerminalBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.terminal.Terminal;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.SystemRegistryImpl;

import org.fusesource.jansi.AnsiConsole;

@Command(name = "\u0008",
    subcommands = { Enter.class
                  , Store.class
                  , Load.class
                  , Dump.class
                  },
    description = "Verwaltung von User-Stories.")
public class Main {

  PrintWriter out;

  public static void main(String[] args) {
    Container.INSTANCE.setPersistenceStrategy(new PersistenceStrategyStream<>());

    AnsiConsole.systemInstall();
    try {
      Supplier<Path> workDir = () -> Paths.get(System.getProperty("user.dir"));

      Main commands = new Main();

      PicocliCommandsFactory factory = new PicocliCommandsFactory();

      CommandLine cmd = new CommandLine(commands, factory);
      PicocliCommands picocliCommands = new PicocliCommands(cmd);
      
      Parser parser = new DefaultParser();
      try (Terminal terminal = TerminalBuilder.builder().build()) {
        SystemRegistry systemRegistry = new SystemRegistryImpl(parser, terminal, workDir, null);
        systemRegistry.setCommandRegistries(picocliCommands);

        LineReader reader = LineReaderBuilder.builder()
          .terminal(terminal)
          .completer(systemRegistry.completer())
          .parser(parser)
          .variable(LineReader.LIST_MAX, 50)
          .build();
        commands.setReader(reader);
        factory.setTerminal(terminal);

        String prompt = "> ";
        String rightPrompt = null;

        String line;
        while (true) {
          try {
            systemRegistry.cleanUp();
            line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
            systemRegistry.execute(line);
          } catch (UserInterruptException e) {
          } catch (EndOfFileException e) {
            return;
          } catch (Exception e) {
            systemRegistry.trace(e);
          }
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      AnsiConsole.systemUninstall();
    }
  }

  public void setReader(LineReader reader) {
    out = reader.getTerminal().writer();
  }

}

@Command(name = "enter",
    description = "Eingabe einer User-Story.")
class Enter implements Runnable {
  
  @Parameters(index = "0",
      description = "Titel der User-Story.")
  private String titel;

  @Parameters(index = "1",
      description = "Akzeptanzkriterium der User-Story.")
  private String akzeptanz;

  @Parameters(index = "2",
      converter = MehrwertConverter.class,
      description = "Mehrwert der User-Story.")
  private Mehrwert mehrwert;

  @Parameters(index = "3",
      converter = StrafeConverter.class,
      description = "Strafe bei auslassen der User-Story.")
  private Strafe strafe;

  @Parameters(index = "4",
      converter = RisikoConverter.class,
      description = "Risiko der User-Story.")
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
      Container.INSTANCE.addMember(new UserStory(titel
                                                ,akzeptanz
                                                ,mehrwert
                                                ,strafe
                                                ,risiko
                                                ,aufwand
                                                ,projekt
                                                ));
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
      
      UserStory.loadIDs(new PriorityQueue<>(Container.INSTANCE.getCurrentList()));
    } catch (PersistenceException e) {
      System.out.println(e.getMessage());
    }
  }

}

@Command(name = "dump",
    description = "Ausgabe geladener User-Stories.")
class Dump implements Runnable {

  @Option(names = { "-p", "--projekt" },
      defaultValue = "",
      description = "Filtern nach Projekt.")
  private String projekt;

  @Override
  public void run() {
    Container.INSTANCE.sort();
    
    MemberView.dump(Container.INSTANCE.getCurrentList(), MemberType.USERSTORY, projekt.isEmpty()
        ? member -> true
        : member -> ((UserStory)member).getProject().equals(projekt));
  }

}
