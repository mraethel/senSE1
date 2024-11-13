package org.hbrs.se1.ws24.exercises.uebung4;

import java.io.Serializable;

public class Prio implements Comparable<Prio>, Serializable {

  private Float val;

  public Prio(Mehrwert mehrwert, Strafe strafe, Risiko risiko, Aufwand aufwand) {
    this.val = (float) (mehrwert.val() + strafe.val())
      / (aufwand.val() + risiko.val());
  }

  public Float val() { return val; }

  @Override
  public int compareTo(Prio prio) {
    return this.val().compareTo(prio.val());
  }

  @Override
  public String toString() { return this.val().toString(); }

}
