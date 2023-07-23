package com.example.batchfront.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Data
@ConfigurationProperties(prefix = "settlement-new")
public class SettlementNewProperties {

    private String protocol = "http";
    private String host;
    private int port;

    public String getUrl() {
        if (protocol.endsWith("://")) {
            protocol = protocol.split("://")[0];
        }
        try {
            URL url = new URL(protocol, host, port, "");
            return url.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
