import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './Components/Header/Header';
import Footer from './Components/Footer/Footer'; 
import HomePage from './Components/HomePage/HomePage'; 

const App = () => {

  return (
    <Router>
    <div className="app">
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        </Routes>
      <Footer />
    </div>
    </Router>
  );
};

export default App;

