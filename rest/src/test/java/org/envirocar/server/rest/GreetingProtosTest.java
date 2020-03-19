package org.envirocar.server.rest;

import org.junit.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GreetingProtosTest {

    @Test
    public void tryStuff() throws IOException {
        GreetingProtos.Greeting.Builder gp = GreetingProtos.Greeting.newBuilder();
        gp.setGreeting("Hello");

        GreetingProtos.Greeting greeting = gp.build();

        assertNotNull(greeting);
        assertEquals(greeting.getGreeting(),"Hello");
        gp.build().writeTo(System.out);
    }
}