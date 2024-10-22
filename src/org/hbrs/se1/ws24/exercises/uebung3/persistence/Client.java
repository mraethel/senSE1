package org.hbrs.se1.ws24.exercises.uebung3.persistence;

import org.hbrs.se1.ws24.exercises.uebung2.ConcreteMember;
import org.hbrs.se1.ws24.exercises.uebung2.Container;
import org.hbrs.se1.ws24.exercises.uebung2.ContainerException;

public class Client {

    public void readOut() throws ContainerException {
        while(Container.INSTANCE.size() < 5) {
            Container.INSTANCE.addMember(new ConcreteMember());
        }
        MemberView.dump(Container.INSTANCE.getCurrentList());
    }
}
