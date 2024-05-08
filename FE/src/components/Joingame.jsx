import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Input } from '@mui/material';
import Logo from './Logo';
import TokenFetch from '../services/TokenFetch';

const JoinGame = () => {
  const [gamePin, setGamePin] = useState('');
  const [playerName, setPlayerName] = useState('');
  const navigate = useNavigate();
  const apiUrl = import.meta.env.VITE_PUBLIC_API_URL;
  const [token, setToken] = useState('');

  //Testattu localina, toimii
  const handleJoinGame = () => {
    // Tarkistaa onko peli olemassa
    fetch(apiUrl + '/api/games/' + gamePin, {
      headers: { 'Authorization': `Bearer ${token}` }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Game not found");
        }
        return response.json();
      })
      .then(game => {
        // Jos peli loytyy, niin suorittaa joingame funktio
        joinGame();
      })
      .catch(err => {
        console.error("Error when checking game existence:", err);
      });

    // Funktio peliin liittymiseen
    const joinGame = () => {
      fetch(apiUrl + '/api/players', {
        method: 'POST',
        headers: { 'Content-type': 'application/json', 'Authorization': `Bearer ${token}` },
        body: JSON.stringify({
          userName: playerName,
          code: gamePin,
          isHost: false
        })
      })
        .then(response => {
          if (!response.ok) {
            throw new Error("Error when joining: " + response.statusText);
          } else {
            return response.json();
          }
        })
        // Data korvattu player, vaikuttaa GameLobbyn hallintaan
        .then(player => {
          navigate('/gamelobby', { state: { player } });
        })
        .catch(err => console.error(err));
    };
  };

  useEffect(() => {
    TokenFetch.fetchToken().then((data) => {
      setToken(data.token);
    })
  }, [])

  return (
    <>
      <br />
      <Logo />
      <br />
      <div className='body'>
        <h1>Liity peliin</h1>
        <p>Anna pelin PIN-koodi:</p>
        <Input
          type="text"
          value={gamePin}
          onChange={(e) => setGamePin(e.target.value)}
          placeholder='Koodi'
        />
        <Input
          type='text'
          value={playerName}
          onChange={(e) => setPlayerName(e.target.value)}
          placeholder='Nimi'
        />
        <button onClick={handleJoinGame}>Liity peliin</button>
      </div>
    </>
  );
};

export default JoinGame;