package org.hbrs.se1.ws24.exercises.uebung2;

import java.util.ArrayList;
import java.util.Iterator;

public class Container {
 
  private ArrayList<Member> members;

  public Container() { members = new ArrayList<>(); }

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

  public void dump () {
    members.forEach (memberInMembers -> {
      System.out.println("Member (ID = " + memberInMembers + ")");
    });
  }

  public int size () { return members.size(); }

  /**
   * Used only for test purposes.
   */
  public boolean contains (Member member) { return members.contains(member); }

}
