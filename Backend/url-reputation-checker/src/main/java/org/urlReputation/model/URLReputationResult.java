package org.urlReputation.model;

public class URLReputationResult {
    private String url;
    private boolean isSafe;
    private String threatType;
    private String message;

    public URLReputationResult(String url, boolean isSafe, String threatType, String message) {
        this.url = url;
        this.isSafe = isSafe;
        this.threatType = threatType;
        this.message = message;
    }

    // Getters and Setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public boolean isSafe() { return isSafe; }
    public void setSafe(boolean safe) { isSafe = safe; }

    public String getThreatType() { return threatType; }
    public void setThreatType(String threatType) { this.threatType = threatType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
