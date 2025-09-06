package org.urlReputation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.urlReputation.config.SafeBrowsingConfig;

@SpringBootApplication
@EnableConfigurationProperties(SafeBrowsingConfig.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("URL Reputation Checker API is running!");
    }
}
