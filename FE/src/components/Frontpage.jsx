import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Logo from './Logo';
import TokenFetch from '../services/TokenFetch';

const FrontPage = () => {
  const [username, setUsername] = useState('');
  const navigate = useNavigate();
  const [gamePin, setGamePin] = useState('');
  const [showInstructions, setShowInstructions] = useState(false);
  const apiUrl = import.meta.env.VITE_PUBLIC_API_URL;
  const [token, setToken] = useState('');
  const [showPinModal, setShowPinModal] = useState(false);
  const [pinInput, setPinInput] = useState('');

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  function generateRandomPin() {
    const min = 100000;
    const max = 999999;
    return Math.floor(Math.random() * (max - min + 1)) + min;
  };

  const handleStartGame = () => {
    // Tarkistetaan, onko käyttäjänimi annettu
    if (username.trim() === '') {
      alert('Syötä käyttäjänimesi ennen pelin aloittamista!');
      return;
    }

    // Luodaan satunnais koodi pelille
    const pin = generateRandomPin();
    setGamePin(pin);

    // Lähetetään tieto pelaajasta API:lle
    fetch(apiUrl + '/api/players', {
      method: 'POST',
      headers: { 'Content-type': 'application/json', 'Authorization': `Bearer ${token}` },
      body: JSON.stringify({
        userName: username,
        code: pin,
        host: true,
      }),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Virhe pelin luomisessa: ' + response.statusText);
        } else {
          return response.json();
        }
      })
      .then(player => {
        // Siirrytään peliaulaan
        console.log(player);
        navigate('/gamelobby', {
          state: { player }
        });
      })
      .catch(err => console.error(err));
  };

  const handleJoinGame = () => {
    // Tarkistetaan käyttäjänimen syöttäminen
    if (!username || username.trim() === '') {
      alert("Syötä käyttäjänimesi ennen peliin liittymistä!");
      return;
    }
    setShowPinModal(true);
  };
  // Promptin avulla kysytään PIN-koodi
  const handleJoinWithPin = (pin) => {
    if (pin.trim() == '') {
      alert("Syötä PIN-koodi liittyäksesi peliin!");
      return;
    }

    // Tarkistetaan, onko peli olemassa
    fetch(apiUrl + '/api/games/' + pin, {
      headers: { 'Authorization': `Bearer ${token}` }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Peliä ei löytynyt");
        }
        return response.json();
      })
      .then(game => {
        // Jos peli löytyy, suoritetaan joinGame-funktio
        joinGame(pin);
      })
      .catch(err => {
        // Virheilmoitus, jos peliä ei löytynyt
        alert("Peliä ei löytynyt PIN-koodilla: " + pin);
        console.error("Virhe pelin tarkistuksessa:", err);
      });
  };

  // Funktio peliin liittymiseen
  const joinGame = (pin) => {
    fetch(apiUrl + '/api/players', {
      method: 'POST',
      headers: { 'Content-type': 'application/json', 'Authorization': `Bearer ${token}` },
      body: JSON.stringify({
        userName: username,
        code: pin,
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
      .then(player => {
        navigate('/gamelobby', { state: { player } });
      })
      .catch(err => console.error(err));
  };

  const toggleInstructions = () => {
    setShowInstructions(!showInstructions);
  }

  const closeModal = () => {
    setShowInstructions(false);
  }

  useEffect(() => {
    TokenFetch.fetchToken().then(data => setToken(data.token));
  }, [])

  return (
    <>
      <br />
      <Logo />
      <div className='body'>
        <h2>Syötä käyttäjänimesi</h2>
        <input
          type="text"
          value={username}
          onChange={handleUsernameChange} />
        <br />
        <h2>Haluatko luoda pelin vai liittyä peliin?</h2>
        <button onClick={handleStartGame}>Luo uusi peli</button>
        <button onClick={handleJoinGame}>Liity peliin</button>
      </div>
      {showPinModal && (
        <div className="modal-overlay" onClick={() => setShowPinModal(false)} >
          <div className="modal" onClick={(e) => e.stopPropagation()} >
            <span className="close" onClick={() => setShowPinModal(false)}>&times;</span>
            <h2>Syötä pelin PIN-koodi</h2>
            <input
              type="text"
              value={pinInput}
              onChange={(e) => setPinInput(e.target.value)}
              placeholder='PIN-koodi'
            />
            <div>
              <button onClick={() => handleJoinWithPin(pinInput)}>Liity</button>
            </div>
          </div>
        </div>
      )}
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '20px' }}>
        <button className="manual" onClick={toggleInstructions}>?</button>
      </div>
      {showInstructions && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <span className="close" onClick={toggleInstructions}>&times;</span>
            <h2>Pelin kulku:</h2>
            <p>1.Jokainen pelaaja kirjoittaa kolme hyvää ja kolme huonoa piirrettä fiktiivisestä treffikumppanistaan erillisille lapuille. <br />
              2. Laput taitetaan ja laitetaan pipoon tai muuhun säilytysastiaan. <br />
              3. Pelaajat istuvat ympyrässä. <br />
              4. Peli alkaa siten, että ensimmäinen pelaaja nostaa yhden lapun piposta. <br />
              5. KAIKKI pelaajat juovat "ensimmäisille treffeille" ennen kuin ensimmäinen pelaaja lukee ääneen, mitä lapussa lukee. Sen jälkeen pelaaja päättää, haluaako hän jatkaa fiktiivisen treffikumppaninsa kanssa vai erota. <br />
              6. Jos pelaaja haluaa jatkaa treffikumppanin kanssa, on seuraavan pelaajan vuoro. Jos pelaaja haluaa erota, hän heittää lapun pois. Sen jälkeen kaikki pelaajat juovat ja on seuraavan pelaajan vuoro. <br />
              7. Peli jatkuu samalla kaavalla, ja kaikki pelaajat juovat jokaisen kolmansille, viidensille ja kuudensille treffeille. <br />
              8. Peli päättyy, kun kaikki pelaajat ovat käyneet kuusilla treffeillään.</p>
          </div>
        </div>
      )}
    </>
  );
};

export default FrontPage;