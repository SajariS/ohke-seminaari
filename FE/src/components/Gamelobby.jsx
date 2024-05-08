/* eslint-disable react/prop-types */
//Stomp tai SockJS vaatii globalin, vite ei sitä määritä
//Jokaisella WS/STOMP komponentilla pakko importoida ensimmäisenä init.js
import "./init";
import { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useLocation, useNavigate } from "react-router-dom";
import Logo from './Logo';
import TokenFetch from "../services/TokenFetch";
import LoadingComponent from "./Loading";

//Saadaan player olio mikä sisältää ,isHost, code yms
const GameLobby = () => {

  const wsUrl = import.meta.env.VITE_PUBLIC_WS_URL;
  const apiUrl = import.meta.env.VITE_PUBLIC_API_URL;
  const sockUrl = import.meta.env.VITE_PUBLIC_SOCK_URL;

  const navigate = useNavigate();
  const { state } = useLocation();

  const [token, setToken] = useState('');
  const [pin, setPin] = useState('');
  const [host, setHost] = useState(false);
  const [connected, setConnected] = useState(false);

  // Osallistujien listan tila
  const [playerList, setPlayerList] = useState([]);

  //Stomp client määritys, brokerURL = ws:<SockJS osoite>
  //Alempi SockJS "emuloi" WS yhteyttä, käytännössä kääntää http metodilla saadun osoitteen ws metodille
  const client = new Client({
    brokerURL: wsUrl,
    debug: (str) => console.log(str)
  })

  //SockJS määritys
  client.webSocketFactory = () => {
    return new SockJS(sockUrl);
  }

  client.onConnect = () => {
    setConnected(true);
    client.subscribe('/lobby/' + state.player.code, (courier) => {
      const recievedData = JSON.parse(courier.body)
      if (Array.isArray(recievedData)) {
        //Lista pakko käsitellä, koska map ei syystä tai toisesta hyväksy key arvoksi id:tä suoraan
        setPlayerList(recievedData.map((item, _) => {
          return {
            ...item,
            key: item.id
          }
        }))
      }
      else {
        console.log(recievedData);
        if (recievedData !== null) {
          console.log("testi")
          if (recievedData.kickedPlayerId === state.player.id) {
            navigate('/Juomapeli-front/');
          }
          else if (recievedData.started) {
            handleTransition();
          }
        }
      }
    });
    client.publish({ destination: '/app/join/' + state.player.code, body: JSON.stringify(state.player) });
  }

  const handleKick = (playerId) => {
    fetch(apiUrl + '/wsapi/lobby/' + playerId, {
      method: 'DELETE',
      headers: {
        'Content-type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .catch(error => console.error(error))
  }

  const handleStartGame = () => {
    fetch(apiUrl + '/wsapi/start/' + pin, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .catch(error => console.error(error))
  }

  const handleTransition = () => {
    const player = state.player;
    client.unsubscribe(0);
    navigate('/gameplay', { state: { player } });
  }

  const handleRejoin = () => {
    navigate('/joingame');
  }

  useEffect(() => {

    //Tarkastetaan onko state tyhjä ja käsitellään dataa tai siirretään takaisin etusivulle
    if (!state) {
      navigate('/Juomapeli-front/');
    }
    else {
      TokenFetch.fetchToken().then(data => setToken(data.token));
      setPin(state.player.code);
      setHost(state.player.host);
      client.activate();

    }

  }, []);

  return (
    <>
      <br />
      <Logo />
      {connected ?
        <div className='body'>
          <h1>Lobby</h1>
          <p>Game pin: {pin}</p>
          <ul style={{ listStyleType: 'none', padding: 0 }}>
            {playerList.map((player) => (
              <li key={player.key} style={{ textAlign: 'center' }}>
                <div style={{ margin: 'auto', display: 'inline-block', marginLeft: host && player.id !== state.player.id && '70px' }}>{player.userName}</div>
                {host && player.id !== state.player.id &&
                  <button className="x-button" onClick={() => handleKick(player.id)}>
                    X
                  </button>
                }
              </li>
            ))}
          </ul>
          {host && <button onClick={() => handleStartGame()}>Aloita peli</button>}
        </div> :
        <>
          <LoadingComponent />
        </>}
    </>
  );
};

export default GameLobby;
