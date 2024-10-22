package org.hbrs.se1.ws24.exercises.uebung2;

import org.hbrs.se1.ws24.exercises.uebung3.persistence.Client;
import org.hbrs.se1.ws24.exercises.uebung3.persistence.PersistenceStrategyStream;

public class Main {

    public static void main(String[] args) throws ContainerException {
        Container.INSTANCE.setPersistenceStrategy(new PersistenceStrategyStream<Member>());
        Client client = new Client();
        client.readOut();
    }
}
