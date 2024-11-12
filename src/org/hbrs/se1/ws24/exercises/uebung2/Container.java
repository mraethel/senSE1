package org.hbrs.se1.ws24.exercises.uebung2;

import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Stream;

public enum Container implements Iterable<Member> {
  
  INSTANCE;
 
  private List<Member> members;

  private PersistenceStrategy<Member> persistenceStrategy;

  Container () { members = new ArrayList<>(); }

  public void addMember (Member member) throws ContainerException {
    for (Member memberInMembers : members)
      if (member.getID().equals(memberInMembers.getID()))
        throw new ContainerException(member.getID());

    members.add(member);
  }

  public String deleteMember (Integer id) {
    Iterator<Member> membersIterator = members.iterator();

    while (membersIterator.hasNext()) {
      Integer nextId = membersIterator.next().getID();

      if (id.equals(nextId)) {
        membersIterator.remove();

        return null;
      }
    }

    /*
     * Nachteile der folgenden Fehlerbehandlung:
     * - weniger Fehlerinformationen (vgl. Stacktrace)
     * - umständliche Unterscheidung von verschiedenen Fehlertypen
     * - umständliche Verarbeitung und Weiterleitung von Fehlern
     **/
    return "Das Member-Object mit der ID " + id + " existiert nicht!";
  }

  /* Kopie um mögliche Manipulation der Liste zu verhindern */
  public List<Member> getCurrentList () { return List.copyOf(members); }

  public void sort () { Collections.sort(members); }

  public void store () throws PersistenceException {
    if (persistenceStrategy == null)
      throw new PersistenceException(
          PersistenceException.ExceptionType.NoStrategyIsSet,
          "Es wurde keine Persistance-Strategy gesetzt");

    persistenceStrategy.save(members);
  }

  public void load() throws PersistenceException {
    if (persistenceStrategy == null)
      throw new PersistenceException(
          PersistenceException.ExceptionType.NoStrategyIsSet,
          "Es wurde keine Persistance-Strategy gesetzt");

    members = persistenceStrategy.load();
  }
  
  public int size () { return members.size(); }

  public void setPersistenceStrategy (PersistenceStrategy<Member> persistenceStrategy) {
    this.persistenceStrategy = persistenceStrategy;
  }

  public Iterator<Member> iterator() { return this.members.iterator(); }

  /**
   * Used only for test purposes.
   */
  public boolean contains (Member member) { return members.contains(member); }

  /**
   * Public for test purposes only
   */
  public void clear() { this.members.clear(); }

}
