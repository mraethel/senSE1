package org.hbrs.se1.ws24.exercises.uebung4;

import org.hbrs.se1.ws24.exercises.uebung2.ConcreteMember;

public class UserStory extends ConcreteMember {

  private String titel;
  private String akzeptanz;
  private Prio prio;
  private String projekt;

  public UserStory(
      String titel,
      String akzeptanz,
      Mehrwert mehrwert,
      Strafe strafe,
      Risiko risiko,
      Aufwand aufwand,
      String projekt) {
    this.titel = titel;
    this.akzeptanz = akzeptanz;
    this.prio = new Prio(
        mehrwert.val(),
        strafe.val(),
        risiko.val(),
        aufwand.val());
    this.projekt = projekt;
  }

  public class Mehrwert {
    
    private char val;

    public Mehrwert(char val) throws IllegalArgumentException {
      if (0 < val && val < 6)
        throw new IllegalArgumentException("Mehrwert");

      this.val = val;
    }

    public char val() { return val; }

  }

  public class Strafe {
    
    private char val;

    public Strafe(char val) throws IllegalArgumentException {
      if (0 < val && val < 6)
        throw new IllegalArgumentException("Strafe");

      this.val = val;
    }

    public char val() { return val; }

  }

  public class Risiko {
    
    private char val;

    public Risiko(char val) throws IllegalArgumentException {
      if (0 < val && val < 6)
        throw new IllegalArgumentException("Risiko");

      this.val = val;
    }
    
    public char val() { return val; }

  }

  public class Aufwand {
    
    private char val;

    public Aufwand(char val) throws IllegalArgumentException {
      if (0 < val && val < 6)
        throw new IllegalArgumentException("Aufwand");

      this.val = val;
    }

    public char val() { return val; }

  }

  public class Prio {
    
    private float val;

    public Prio(char mehrwert, char strafe, char risiko, char aufwand) {
      val = (mehrwert + strafe) / (aufwand + risiko);
    }

    public float val() { return val; }

  }

}
