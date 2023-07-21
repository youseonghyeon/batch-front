package com.example.batchfront.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "settlement-new")
public class SettlementNewProperties {

    private String scheme = "http://";
    private String host;
    private int port = 8002;

    public String getUrl() {
        if (scheme.endsWith("://")) {
            return scheme + host + ":" + port;
        } else {
            return scheme + "://" + host + ":" + port;
        }
    }

}
