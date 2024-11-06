package org.hbrs.se1.ws24.exercises.uebung2;

import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public enum Container {
  INSTANCE;
 
  private List<Member> members;

  private PersistenceStrategy<Member> strategy;

  Container() {
    members = new ArrayList<>();
  }

  public void addMember (Member member) throws ContainerException {
    Iterator<Member> membersIterator = members.iterator();

    while (membersIterator.hasNext())
      if (member.getID().equals(membersIterator.next().getID())) throw new ContainerException(member.getID());

    members.add(member);
  }

  public String deleteMember (Integer id) {
    Iterator<Member> membersIterator = members.iterator();

    while (membersIterator.hasNext()) {
      Integer nextId = membersIterator.next().getID();

      if (id.equals(nextId)) {
        membersIterator.remove();
        ConcreteMember.freeID(nextId);

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

  /* Kopie, um moegliche Manipulation der Liste zu verhindern */
  public List<Member> getCurrentList() {
    return List.copyOf(members);
  }

  public void store() throws PersistenceException {
    if (strategy == null) throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "Es wurde keine Persistance-Strategy gesetzt");
    strategy.save(members);
  }

  public void load() throws PersistenceException {
    if (strategy == null) throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "Es wurde keine Persistance-Strategy gesetzt");
    clear();
    members = strategy.load();
  }
  public int size () { return members.size(); }

   public void setPersistenceStrategy(PersistenceStrategy<Member> strategy) {
    this.strategy = strategy;
  }

  /**
   * Used only for test purposes.
   */
  public boolean contains (Member member) { return members.contains(member); }

  /**
   * Public for test purposes only
   */
  public void clear() {
    Iterator<Member> iterator = members.iterator();
    while (iterator.hasNext()) {
      Member member = iterator.next();
      iterator.remove();
      ConcreteMember.freeID(member.getID());
    }
  }
}
