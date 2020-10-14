package com.morris.SocketWhisper.Models.Impl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

@DisplayName("Jokes Database Testcases")
public class JokesDBTest {

    /**
     * Test should be run independently from Raspberry Pi.
     * @throws IOException in some case sql statement fails.
     */
    @Test
    @DisplayName("LightJokes DB Test")
    public void TestLightJokesBD() throws IOException {
        RpiServer rpiServer = new RpiServer(6066);
        String jokes = rpiServer.fetchJokesDB();
        Assert.assertNotNull(jokes);
        System.out.println(jokes);
    }
}
