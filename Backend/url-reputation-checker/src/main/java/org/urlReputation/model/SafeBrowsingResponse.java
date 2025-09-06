package org.urlReputation.model;

import java.util.List;

public class SafeBrowsingResponse {
    private List<ThreatMatch> matches;

    public static class ThreatMatch {
        private String threatType;
        private String platformType;
        private ThreatEntry threat;

        // Getters and Setters
        public String getThreatType() { return threatType; }
        public void setThreatType(String threatType) { this.threatType = threatType; }

        public String getPlatformType() { return platformType; }
        public void setPlatformType(String platformType) { this.platformType = platformType; }

        public ThreatEntry getThreat() { return threat; }
        public void setThreat(ThreatEntry threat) { this.threat = threat; }
    }

    public static class ThreatEntry {
        private String url;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    // Getters and Setters
    public List<ThreatMatch> getMatches() { return matches; }
    public void setMatches(List<ThreatMatch> matches) { this.matches = matches; }
}
