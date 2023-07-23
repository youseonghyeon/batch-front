package com.example.batchfront.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettlementNewPropertiesTest {

    private SettlementNewProperties properties = new SettlementNewProperties();

    @Test
    void getUrl() {
        propertySetting("http://", "localhost", 8002);
        assertEquals("http://localhost:8002", properties.getUrl());

        propertySetting("http", "localhost", 8002);
        assertEquals("http://localhost:8002", properties.getUrl());
    }

    private void propertySetting(String protocol, String localhost, int port) {
        properties.setProtocol(protocol);
        properties.setHost(localhost);
        properties.setPort(port);
    }
}
