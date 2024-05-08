import { useLocation, useNavigate } from "react-router-dom";
import "./init";
import { useEffect, useState } from "react";
import TokenFetch from "../services/TokenFetch";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import Logo from "./Logo";
import { Dialog, DialogActions, DialogContent, DialogTitle } from "@mui/material";
import LoadingComponent from "./Loading";

export default function GamePlay() {

    const wsUrl = import.meta.env.VITE_PUBLIC_WS_URL;
    const apiUrl = import.meta.env.VITE_PUBLIC_API_URL;
    const sockUrl = import.meta.env.VITE_PUBLIC_SOCK_URL;
    const [token, setToken] = useState('');
    const { state } = useLocation();
    const navigate = useNavigate();

    const [open, setOpen] = useState(false);
    const [player, setPlayer] = useState({});
    const [turn, setTurn] = useState({});
    // Turn sisältää;
    //Kortin = turn.card, joka on card olio. Eli sisältää muuta dataa
    //Kenen vuoro on - turn.playersTurn
    //Koodi - turn.code
    //valinta - turn.choice, tällä ei vuoron alun kannalta väliä, lopetuksessa enemmän
    const [title, setTitle] = useState('');
    const [desc, setDesc] = useState('');
    const [partner, setPartner] = useState({});
    const [cardList, setCardList] = useState([]);
    const [connected, setConnected] = useState(false);
    const [started, setStarted] = useState(false);

    const client = new Client({
        brokerURL: wsUrl
    });

    client.webSocketFactory = () => {
        return new SockJS(sockUrl);
    }

    client.onConnect = () => {
        setConnected(true);
        client.subscribe('/game/' + state.player.code, (courier) => {
            const data = JSON.parse(courier.body)
            setTurn(data);
            if (data.pulledCard !== null) {
                setTitle(data.pulledCard.title)
                setDesc(data.pulledCard.desc)
            }
        })
    }

    useEffect(() => {

        if (!state) {
            navigate('/Juomapeli-front/');
        }
        else {
            TokenFetch.fetchToken().then(data => setToken(data.token));
            client.activate();

        }

    }, [])

    useEffect(() => {
        if (token.length !== 0) {
            handlePlayerFetch();
        }
    }, [token])

    useEffect(() => {
        if (turn !== null && player !== null) {
            if (turn.playersTurn === player.id) {
                setOpen(true);
            }
        }
    }, [turn])

    const handleClose = () => setOpen(false);

    //Choice boolean arvo
    //Palautus tulee WS kautta 
    const handleEndTurn = (choice) => {
        fetch(apiUrl + '/wsapi/game', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                playersTurn: player.id,
                code: player.code,
                choice: choice,
                pulledCard: turn.pulledCard
            })
        })
            .then(() => {
                handlePlayerFetch();
                handleClose();
            })
            .catch(err => console.error(err))
    }

    // state.player => setPLayer ei toimi niin pakko kiertää
    const handlePlayerFetch = () => {
        fetch(apiUrl + '/api/players/' + state.player.id, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error fetching player: " + response.statusText);
                }
                else {
                    return response.json();
                }
            })
            .then(data => {
                setPlayer(data);
                setPartner(data.character);
                if (data.character.characterCard === null || data.character.characterCard.length === 0) {
                    setCardList([]);
                }
                else {
                    setCardList(data.character.characterCard.map((item, _) => {
                        return {
                            ...item,
                            key: item.id
                        }
                    }));
                }
            })
            .catch(err => console.error(err))
    }

    const handleStart = () => {
        fetch(apiUrl + '/wsapi/game/' + state.player.code)
            .then(() => setStarted(true))
            .catch(err => console.error(err))
    }

    return (
        <>
            <Logo />
            {connected ?
            <>
                <div className="content-wrapper">
                    {partner !== null &&
                        <div className="partner-info">
                            <h2 className="partner-name">{partner.name} {partner.age}</h2>
                            {cardList !== null && (
                                <ul className="card-list">
                                    {cardList.map((card) => (
                                        <li key={card.id} className="card-item">{card.title} {card.desc}</li>
                                    ))}
                                </ul>
                            )}
                        </div>
                    }
                    {player.id === turn.playersTurn && Object.values(turn).length !== 0 ? (
                        <Dialog open={open} onClose={handleClose}>
                            <DialogTitle className="dialog-title">Vuorosi!</DialogTitle>
                            <DialogContent className="dialog-content">
                                <div>
                                    <h2 className="card-title">{title}</h2>
                                    <p className="card-desc">{desc}</p>
                                </div>
                            </DialogContent>
                            <DialogActions>
                                <button className="dialog-button-no" onClick={() => handleEndTurn(false)}>Ei</button>
                                <button className="dialog-button-yes" onClick={() => handleEndTurn(true)}>Juu</button>
                            </DialogActions>
                        </Dialog>
                    ) : (
                        <>
                            <br />
                            <p className="wait-message">Odota vuoroasi</p>
                        </>
                    )}
                </div>
                { !started && player.host && 
                <div className="button-wrapper">
                    <button className="start-button" onClick={() => handleStart()}>Aloita jako</button>
                </div>}
                { player.id === turn.playersTurn && Object.values(turn).length &&
                <div className="button-wrapper">
                    <button className="test-button" onClick={() => setOpen(true)}>Avaa kortti</button>
                </div>

                    }
                </>
                :
                <>
                    <LoadingComponent />
                </>}
        </>
    );
}