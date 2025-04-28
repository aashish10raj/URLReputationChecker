import React, { useState } from 'react';
import './HomePage.css';

const HomePage = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [showResult, setShowResult] = useState(false);

    const handleSearch = () => {
        setShowResult(true); // Show the result div when the button is clicked
    };

    return (
        <div className="homepage">
            <main className="homepage-main">
                <p>Check the reputation of any URL quickly and easily.</p>
                <div className="search-container">
                    <input
                        type="text"
                        placeholder="Enter URL"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="search-box"
                    />
                    <button onClick={handleSearch} className="search-button">
                        Search
                    </button>
                </div>
                {showResult && (
                    <div className="search-result">
                        <p>Dummy result for: {searchTerm}</p>
                    </div>
                )}
            </main>
        </div>
    );
};

export default HomePage;