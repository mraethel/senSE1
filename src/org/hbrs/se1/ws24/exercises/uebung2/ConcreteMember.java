package org.hbrs.se1.ws24.exercises.uebung2;

public class ConcreteMember implements Member {

  private Integer id;

  public ConcreteMember () { id = Long.valueOf(System.nanoTime()).hashCode(); }

  @Override
  public Integer getID() { return id; }

  @Override
  public String toString() { return getID().toString(); }

}
