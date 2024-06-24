package ru.javaops.masterjava.xml.util;

import org.junit.Test;

public class MainXmlTest {

    private JaxbMainXml jaxbMainXml = new JaxbMainXml();

    private String PROJECT_NAME = "topjava";

    @Test
    public void testPayload() throws Exception {
        jaxbMainXml.printParticipants(PROJECT_NAME);
    }
}