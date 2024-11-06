package org.hbrs.se1.ws24.exercises.uebung2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class ConcreteMember implements Member, Serializable {

  private static Integer maxId = Integer.MIN_VALUE;

  private static volatile Queue<Integer> idQueue = new LinkedList<>();

  private static synchronized Integer getNextID () {
    return !idQueue.isEmpty() ? idQueue.remove() : maxId++;
  }

  public static synchronized void freeID (Integer id) { idQueue.offer(id); }

  private Integer id;

  public ConcreteMember () { id = getNextID(); }

  public ConcreteMember(Integer id) {
    this.id = id;
    maxId = Math.max(maxId, id + 1);
  }

  @Override
  public Integer getID () { return id; }

  @Override
  public String toString () { return getID().toString(); }

}
