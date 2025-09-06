package org.urlReputation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "google.safebrowsing.api")
public class SafeBrowsingConfig {
    private String key;

    // Getter and Setter - MUST have both
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Alternative method name (try this if above doesn't work)
    public String getApiKey() {
        return key;
    }
}
