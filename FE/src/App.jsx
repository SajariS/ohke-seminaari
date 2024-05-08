import Cards from './components/Cards';
import Container from '@mui/material/Container';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import LoadingComponent from './components/Loading';
import GameLobby from './components/Gamelobby';
import Joingame from './components/Joingame';
import FrontPage from './components/Frontpage';
import GamePlay from './components/GamePlay';
import { useEffect } from 'react';
import React, { useState } from 'react';


function App() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    //Lataa datan taalla
    //Kun data on ladattu, niin asettaa isLoading-tilan falseksi
    setTimeout(() => setIsLoading(false), 1000)
  }, []);

  return (
    <div className="App">
      {isLoading && <LoadingComponent />}
      {!isLoading && (
    <>
      <Router>
        <Container>
          <Routes>
            <Route path="Juomapeli-front/" element={<FrontPage />} />
            <Route path="/gamelobby" element={<GameLobby />} />
            <Route path="/joingame" element={<Joingame />} />
            <Route path="/cards" element={<Cards />} />
            <Route path="/gameplay" element={<GamePlay />} />
          </Routes>
        </Container>
      </Router>     
    </>
      )}
      </div>
  )
}

export default App
