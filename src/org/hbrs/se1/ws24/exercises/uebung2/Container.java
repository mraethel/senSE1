package org.hbrs.se1.ws24.uebung2;

import java.util.ArrayList;
import java.util.Iterator;

public class Container {
 
  private ArrayList<Member> members;

  public void addMember (Member member) throws ContainerException {
    members.forEach (memberInMembers -> {
      if (member.getID() == memberInMembers.getID()) throw new ContainerException(member.getID());
    });

    members.add(member);
  }

  public String deleteMember (Integer id) {
    Iterator<Member> membersIterator = members.iterator();

    while (membersIterator.hasNext())
      if (id == membersIterator.next().getID()) { membersIterator.remove(); return ""; }

    return "Das Member-Object mit der ID " + id + " existiert nicht!";
  }

  public void dump () {
    members.forEach (memberInMembers -> { System.out.println(memberInMembers); });
  }

  public int size () { return members.size(); }

}
