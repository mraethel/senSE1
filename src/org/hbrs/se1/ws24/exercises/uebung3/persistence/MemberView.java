package org.hbrs.se1.ws24.exercises.uebung3.persistence;

import org.hbrs.se1.ws24.exercises.uebung2.Member;
import org.hbrs.se1.ws24.exercises.uebung4.UserStory;
import org.hbrs.se1.ws24.exercises.uebung4.Prio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MemberView {

    public static void dump (List<Member> members) { dump(members, MemberType.CONCRETE); }

    public static void dump (List<Member> members, MemberType memberType) {
      dump(members, memberType, member -> true);
    }

    public static void dump (List<Member> members, MemberType memberType, Predicate<Member> filter) {
      switch (memberType) {
        case USERSTORY: {
          System.out.println("+-------------+------------+----------------------+-----------+------------+");
          System.out.printf("| %11s | %10s | %20s | %9s | %10s |\n", "ID", "Titel", "Akzeptanz", "Priorität", "Projekt");
          System.out.println("+-------------+------------+----------------------+-----------+------------+");
          
          members.stream().filter(filter).map(member -> { return (UserStory)member; }).forEach(userStory -> {
            System.out.printf("| %11s | %10s | %20s | %9f | %10s |\n",
                userStory.getID(),
                userStory.getTitel(),
                userStory.getAkzeptanz(),
                userStory.getPrio().val(),
                userStory.getProject()); 
          });

          System.out.println("+-------------+------------+----------------------+-----------+------------+");
          
          break;
        } default: {
          System.out.println("+-------------+");
          System.out.printf("| %11s |\n", "ID");
          System.out.println("+-------------+");
          
          members.stream().filter(filter).forEach(member -> {
            System.out.printf("| %11s |\n", member.getID());
          });

          System.out.println("+-------------+");
        }
      };
    }

}
