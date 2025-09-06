package org.urlReputation.model;

import java.util.List;

public class SafeBrowsingRequest {
    private Client client;
    private ThreatInfo threatInfo;

    // CRITICAL: Client class - REQUIRED by Google Safe Browsing API
    public static class Client {
        private String clientId;
        private String clientVersion;

        public Client() {}

        public Client(String clientId, String clientVersion) {
            this.clientId = clientId;
            this.clientVersion = clientVersion;
        }

        // Getters and Setters
        public String getClientId() { return clientId; }
        public void setClientId(String clientId) { this.clientId = clientId; }

        public String getClientVersion() { return clientVersion; }
        public void setClientVersion(String clientVersion) { this.clientVersion = clientVersion; }
    }

    public static class ThreatInfo {
        private List<String> threatTypes;
        private List<String> platformTypes;
        private List<String> threatEntryTypes;
        private List<ThreatEntry> threatEntries;

        // Getters and Setters
        public List<String> getThreatTypes() { return threatTypes; }
        public void setThreatTypes(List<String> threatTypes) { this.threatTypes = threatTypes; }

        public List<String> getPlatformTypes() { return platformTypes; }
        public void setPlatformTypes(List<String> platformTypes) { this.platformTypes = platformTypes; }

        public List<String> getThreatEntryTypes() { return threatEntryTypes; }
        public void setThreatEntryTypes(List<String> threatEntryTypes) { this.threatEntryTypes = threatEntryTypes; }

        public List<ThreatEntry> getThreatEntries() { return threatEntries; }
        public void setThreatEntries(List<ThreatEntry> threatEntries) { this.threatEntries = threatEntries; }
    }

    public static class ThreatEntry {
        private String url;

        public ThreatEntry() {}
        public ThreatEntry(String url) { this.url = url; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    // Main class getters and setters
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public ThreatInfo getThreatInfo() { return threatInfo; }
    public void setThreatInfo(ThreatInfo threatInfo) { this.threatInfo = threatInfo; }
}
