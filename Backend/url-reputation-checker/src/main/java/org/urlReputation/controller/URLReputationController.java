package org.urlReputation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urlReputation.model.URLReputationResult;
import org.urlReputation.service.SafeBrowsingService;

@RestController
@RequestMapping("/api/url")
@CrossOrigin(origins = "http://localhost:3000") // Allow React app to connect
public class URLReputationController {

    @Autowired
    private SafeBrowsingService safeBrowsingService;

    @PostMapping("/check")
    public ResponseEntity<URLReputationResult> checkURL(@RequestBody URLCheckRequest request) {

        if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new URLReputationResult("", false, "INVALID_INPUT", "URL cannot be empty"));
        }

        URLReputationResult result = safeBrowsingService.checkURL(request.getUrl());
        return ResponseEntity.ok(result);
    }

    // Simple DTO for request
    public static class URLCheckRequest {
        private String url;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}
