package org.hbrs.se1.ws24.exercises.uebung2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Collections;

public class ConcreteMember implements Member, Serializable {

  private static volatile PriorityQueue<Integer> idQueue = new PriorityQueue<>();

  static { idQueue.offer(Integer.MIN_VALUE); }

  private static synchronized Integer getNextID () {
    if (idQueue.size() == 1 && idQueue.element() < Integer.MAX_VALUE)
      idQueue.offer(idQueue.element() + 1);

    return idQueue.poll();
  }

  public static synchronized void freeID (Member member) { idQueue.offer(member.getID()); } 

  public static synchronized void loadIDs (PriorityQueue<Member> members) {
    idQueue.clear();

    int i;
    for (i = Integer.MIN_VALUE; !members.isEmpty(); i++) {
      if (members.element().getID().equals(i)) { members.poll(); }
      else { idQueue.offer(i); };
    }

    idQueue.offer(++i);
  }

  public static void clear () {
    idQueue.clear();

    idQueue.offer(Integer.MIN_VALUE);
  }

  private Integer id;

  public ConcreteMember () { id = getNextID(); }

  @Override
  public Integer getID () { return id; }

  @Override
  public String toString () { return this.getID().toString(); }

  @Override
  public int compareTo (Member member) { return this.getID().compareTo(member.getID()); }

  @Override
  public boolean equals(Object object) {
    return object instanceof ConcreteMember
      ? this.getID().equals(((ConcreteMember)object).getID())
      : super.equals(object);
  }

}
