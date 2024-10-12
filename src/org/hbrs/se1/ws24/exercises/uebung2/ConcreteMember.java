package org.hbrs.se1.ws24.exercises.uebung2;

public class ConcreteMember implements Member {

  private static Integer nextId = Integer.MIN_VALUE;

  private Integer id;

  public ConcreteMember () { id = nextId++; }

  @Override
  public Integer getID () { return id; }

  @Override
  public String toString () { return getID().toString(); }

}
