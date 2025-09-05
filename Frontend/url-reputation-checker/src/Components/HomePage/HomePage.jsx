import React, { useState } from 'react';
import './HomePage.css';

const HomePage = () => {
  const [url, setUrl] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!url.trim()) {
      setError('Please enter a URL to check');
      return;
    }

    // Basic URL validation
    if (!isValidUrl(url.trim())) {
      setError('Please enter a valid URL (include http:// or https://)');
      return;
    }

    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const response = await fetch('http://localhost:8080/api/url/check', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ url: url.trim() }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setResult(data);
    } catch (error) {
      console.error('Error checking URL:', error);
      setError('Failed to check URL. Please make sure the backend is running.');
    } finally {
      setLoading(false);
    }
  };

  const isValidUrl = (string) => {
    try {
      new URL(string);
      return true;
    } catch (_) {
      return false;
    }
  };

  const clearResults = () => {
    setResult(null);
    setError(null);
    setUrl('');
  };

  const getThreatDescription = (threatType) => {
    const descriptions = {
      'MALWARE': 'This URL contains or distributes malicious software that could harm your device.',
      'SOCIAL_ENGINEERING': 'This URL appears to be a phishing or social engineering attack designed to steal personal information.',
      'UNWANTED_SOFTWARE': 'This URL may install unwanted software or potentially unwanted programs (PUPs).',
      'POTENTIALLY_HARMFUL_APPLICATION': 'This URL may contain applications that could potentially harm your device or data.'
    };
    return descriptions[threatType] || 'Unknown threat detected';
  };

  return (
    <div className="homepage">
      <div className="container">
        <div className="header">
          <h1>🔒 URL Reputation Checker</h1>
          <p className="subtitle">
            Check any URL for malware, phishing, and other security threats using Google Safe Browsing
          </p>
        </div>

        <form onSubmit={handleSubmit} className="url-form">
          <div className="input-group">
            <input
              type="text"
              placeholder="Enter URL to check (e.g., https://example.com)"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              className="url-input"
              disabled={loading}
            />
            <button 
              type="submit" 
              className="check-button"
              disabled={loading}
            >
              {loading ? (
                <>
                  <span className="spinner"></span>
                  Checking...
                </>
              ) : (
                'Check URL'
              )}
            </button>
          </div>
        </form>

        {error && (
          <div className="error-message">
            <span className="error-icon">⚠️</span>
            {error}
          </div>
        )}

        {result && (
          <div className="result-container">
            <div className={`result-card ${result.safe ? 'safe' : 'unsafe'}`}>
              <div className="result-header">
                <div className="result-icon">
                  {result.safe ? '✅' : '⚠️'}
                </div>
                <div className="result-status">
                  <h3>{result.safe ? 'Safe URL' : 'Unsafe URL Detected'}</h3>
                  <p className="checked-url">{result.url}</p>
                </div>
              </div>

              <div className="result-details">
                {result.safe ? (
                  <div className="safe-details">
                    <p className="safe-message">
                      ✨ This URL appears to be safe according to Google Safe Browsing.
                    </p>
                    <p className="disclaimer">
                      Note: This check is based on known threat databases. Always exercise caution when visiting unknown websites.
                    </p>
                  </div>
                ) : (
                  <div className="unsafe-details">
                    <div className="threat-type">
                      <strong>Threat Type:</strong> 
                      <span className="threat-badge">{result.threatType}</span>
                    </div>
                    <div className="threat-description">
                      <p>{getThreatDescription(result.threatType)}</p>
                    </div>
                    <div className="warning">
                      <strong>⚠️ Warning:</strong> Do not visit this URL as it may harm your device or compromise your personal information.
                    </div>
                  </div>
                )}
                
                <div className="api-message">
                  <small>{result.message}</small>
                </div>
              </div>

              <div className="result-actions">
                <button onClick={clearResults} className="clear-button">
                  Check Another URL
                </button>
              </div>
            </div>
          </div>
        )}

        <div className="features">
          <h2>What We Check For</h2>
          <div className="feature-grid">
            <div className="feature-item">
              <div className="feature-icon">🦠</div>
              <h3>Malware</h3>
              <p>Detects websites distributing malicious software, viruses, and trojans</p>
            </div>
            <div className="feature-item">
              <div className="feature-icon">🎣</div>
              <h3>Phishing</h3>
              <p>Identifies fake websites designed to steal personal information</p>
            </div>
            <div className="feature-item">
              <div className="feature-icon">💻</div>
              <h3>Unwanted Software</h3>
              <p>Finds sites that install unwanted programs or browser hijackers</p>
            </div>
            <div className="feature-item">
              <div className="feature-icon">⚡</div>
              <h3>Real-time</h3>
              <p>Uses Google Safe Browsing API for up-to-date threat intelligence</p>
            </div>
          </div>
        </div>

        <div className="test-urls">
          <h2>Test URLs</h2>
          <p>Try these URLs to see how the checker works:</p>
          <div className="test-buttons">
            <button 
              onClick={() => setUrl('https://www.google.com')}
              className="test-button safe-test"
            >
              Safe URL (Google)
            </button>
            <button 
              onClick={() => setUrl('http://testsafebrowsing.appspot.com/apiv4/ANY_PLATFORM/MALWARE/URL/')}
              className="test-button unsafe-test"
            >
              Test Malware URL
            </button>
            <button 
              onClick={() => setUrl('http://testsafebrowsing.appspot.com/apiv4/ANY_PLATFORM/SOCIAL_ENGINEERING/URL/')}
              className="test-button unsafe-test"
            >
              Test Phishing URL
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
