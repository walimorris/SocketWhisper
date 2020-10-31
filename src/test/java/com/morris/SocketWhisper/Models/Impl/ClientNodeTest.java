package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Constants;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

/**
 * Within the Server-Client model, there's much functionality and these tests ensure functionality
 * for the ClientNode class are working properly.
 * IMPORTANT: Ensure Raspberry Pi Server is active during ClientNode and RpiServer Tests.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */

@DisplayName("ClientNode Test Cases")
public class ClientNodeTest {

    /**
     * Test Case to ensure the client prompt interface provides correct output for
     * each key selection.
     * @throws IOException : Some error occurs
     */
    @Test
    @DisplayName("Client Interface Prompt Test")
    public void getPromptMapTest() throws IOException {
        ClientNode client = new ClientNode();
        Map<String, String> interfacePrompt = client.getPromptMap();
        System.out.println(interfacePrompt);
        for (String key : interfacePrompt.keySet()) {
            switch (key) {
                case "1":
                    Assert.assertEquals("weather", interfacePrompt.get(key));
                    break;
                case "2":
                    Assert.assertEquals("mars", interfacePrompt.get(key));
                    break;
                case "3":
                    Assert.assertEquals("jokes", interfacePrompt.get(key));
                    break;
                default:
                    Assert.assertEquals("exit", interfacePrompt.get(key));
                    break;
            }
        }
    }
}