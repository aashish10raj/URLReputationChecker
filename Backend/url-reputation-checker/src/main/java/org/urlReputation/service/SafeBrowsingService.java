package org.urlReputation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.urlReputation.config.SafeBrowsingConfig;
import org.urlReputation.model.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

@Service
public class SafeBrowsingService {

    @Autowired
    private SafeBrowsingConfig config;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SafeBrowsingService.class);


    private final WebClient webClient;

    public SafeBrowsingService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://safebrowsing.googleapis.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public URLReputationResult checkURL(String url) {
        System.out.println("Using API Key: " + config.getApiKey().substring(0, 20) + "...");
        try {
            SafeBrowsingRequest request = createRequest(url);
            // DEBUG: Log the request to see what's being sent
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            logger.info("Sending request to API "+jsonRequest);
            SafeBrowsingResponse response = webClient
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v4/threatMatches:find")
                            .queryParam("key", config.getApiKey())
                            .build())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SafeBrowsingResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            return processResponse(url, response);

        } catch (WebClientResponseException e) {
            return new URLReputationResult(
                    url,
                    true, // Assume safe if API fails
                    null,
                    "API Error " + e.getStatusCode() + ": " + e.getResponseBodyAsString()
            );
        } catch (Exception e) {
            return new URLReputationResult(
                    url,
                    true, // Assume safe if API fails
                    null,
                    "API check failed: " + e.getMessage()
            );
        }
    }

    private SafeBrowsingRequest createRequest(String url) {
        SafeBrowsingRequest request = new SafeBrowsingRequest();

        // CRITICAL: Add client information - this fixes the 403 error
        SafeBrowsingRequest.Client client = new SafeBrowsingRequest.Client(
                "URLReputationChecker",  // Your app identifier
                "1.0.0"                  // Your app version
        );
        request.setClient(client);

        // Set threat info
        SafeBrowsingRequest.ThreatInfo threatInfo = new SafeBrowsingRequest.ThreatInfo();
        threatInfo.setThreatTypes(Arrays.asList(
                "MALWARE",
                "SOCIAL_ENGINEERING",
                "UNWANTED_SOFTWARE",
                "POTENTIALLY_HARMFUL_APPLICATION"
        ));

        threatInfo.setPlatformTypes(Arrays.asList("ANY_PLATFORM"));
        threatInfo.setThreatEntryTypes(Arrays.asList("URL"));
        threatInfo.setThreatEntries(
                Collections.singletonList(new SafeBrowsingRequest.ThreatEntry(url))
        );

        request.setThreatInfo(threatInfo);
        return request;
    }


    private URLReputationResult processResponse(String url, SafeBrowsingResponse response) {
        if (response == null || response.getMatches() == null || response.getMatches().isEmpty()) {
            return new URLReputationResult(url, true, null, "URL is safe");
        }

        // URL is flagged as dangerous
        SafeBrowsingResponse.ThreatMatch match = response.getMatches().get(0);
        return new URLReputationResult(
                url,
                false,
                match.getThreatType(),
                "Threat detected: " + match.getThreatType()
        );
    }
}
