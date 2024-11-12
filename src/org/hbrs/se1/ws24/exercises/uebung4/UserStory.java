package org.hbrs.se1.ws24.exercises.uebung4;

import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung2.ConcreteMember;


import picocli.CommandLine.ITypeConverter;

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
        mehrwert,
        strafe,
        risiko,
        aufwand);
    this.projekt = projekt;
  }

  public String getTitel () { return this.titel; }

  public String getAkzeptanz () { return this.akzeptanz; }

  public Prio getPrio () { return this.prio; }

  public String getProjekt () { return this.projekt; }

  @Override
  public int compareTo(Member member) {
    return member instanceof UserStory
      ? this.getPrio().compareTo(((UserStory)member).getPrio())
      : super.compareTo(member);
  }

}

class Mehrwert {

  private byte val;

  public Mehrwert(byte val) throws IllegalArgumentException {
    if (1 > val || val > 5)
      throw new IllegalArgumentException("Mehrwert");

    this.val = val;
  }

  public byte val() { return val; } 

}

class MehrwertConverter implements ITypeConverter<Mehrwert> {
  
  @Override
  public Mehrwert convert(String value) {
    return new Mehrwert(Byte.decode(value));
  }

}

class Strafe {

  private byte val;

  public Strafe(byte val) throws IllegalArgumentException {
    if (1 > val || val > 5)
      throw new IllegalArgumentException("Strafe");

    this.val = val;
  }

  public byte val() { return val; } 

}

class StrafeConverter implements ITypeConverter<Strafe> {

  @Override
  public Strafe convert(String value) {
    return new Strafe(Byte.decode(value));
  }

}

class Risiko {

  private byte val;

  public Risiko(byte val) throws IllegalArgumentException {
    if (1 > val || val > 5)
      throw new IllegalArgumentException("Risiko");

    this.val = val;
  }

  public byte val() { return val; }

}

class RisikoConverter implements ITypeConverter<Risiko> {

  @Override
  public Risiko convert(String value) {
    return new Risiko(Byte.decode(value));
  }

}

class Aufwand {

  private byte val;

  public Aufwand(byte val) throws IllegalArgumentException {
    if (1 > val || val > 5)
      throw new IllegalArgumentException("Aufwand");

    this.val = val;
  }

  public byte val() { return val; } 

}

class AufwandConverter implements ITypeConverter<Aufwand> {

  @Override
  public Aufwand convert(String value) {
    return new Aufwand(Byte.decode(value));
  }

}

