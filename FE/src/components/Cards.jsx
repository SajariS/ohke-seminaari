import React, { useState } from 'react';
import Logo from './Logo';
import TokenFetch from '../services/TokenFetch';
import '../index.css';

function Cards() {
  const apiUrl = import.meta.env.VITE_PUBLIC_API_URL;
  const [goodCards, setGoodCards] = useState([{ title: 'good1', desc: '', userMade: true, goodTrait: true }]);
  const [badCards, setBadCards] = useState([{ title: 'bad1', desc: '', userMade: true, goodTrait: false }]);

  const addGoodCard = () => {
    if (goodCards.length < 3) {
      const newId = `good${goodCards.length + 1}`;
      setGoodCards([...goodCards, { title: newId, desc: '', userMade: true, goodTrait: true }]);
    }
  };

  const addBadCard = () => {
    if (badCards.length < 3) {
      const newId = `bad${badCards.length + 1}`;
      setBadCards([...badCards, { title: newId, desc: '', userMade: true, goodTrait: false }]);
    }
  };

  const handleInputChange = (title, desc, type) => {
    if (type === 'good') {
      const updatedCards = goodCards.map(card =>
        card.title === title ? { ...card, desc: desc, title } : card
      );
      setGoodCards(updatedCards);
      console.log(goodCards);
    } else if (type === 'bad') {
      const updatedCards = badCards.map(card =>
        card.title === title ? { ...card, desc: desc } : card
      );
      setBadCards(updatedCards);
      console.log(badCards);
    }
  };

  const saveCards = async (cards) => {
    if (goodCards.every(card => card.desc.trim() !== '') && badCards.every(card => card.desc.trim() !== '')) {
      try {
        const tokenData = await TokenFetch.fetchToken();
        const token = tokenData.token;

        const response = await fetch(apiUrl + '/api/cards/many', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
          body: JSON.stringify(cards),
        });

        if (response.ok) {
          alert('Kortit tallennettu');
        } else {
          throw new Error('Korttien tallennus ei onnistunut');
        }
      } catch (error) {
        alert('Korttien tallennus ei onnistunut');
        console.error(error);
      }
    } else {
      alert('Kaikki kortit eivät ole täytettyjä.');
    }

  };

  const getButtonLabel = (type, count) => {
    switch (count) {
      case 1:
        return `Kirjoita toinen ${type === 'good' ? 'hyvä' : 'huono'} piirre`;
      case 2:
        return `Kirjoita kolmas ${type === 'good' ? 'hyvä' : 'huono'} piirre`;
      default:
        return `Lisää ${type === 'good' ? 'hyvä' : 'huono'} piirre`;
    }
  };

  return (
    <div className="container">
      <br />
      <Logo />
      <div className='body'>
        <p>Kirjoita kolme hyvää piirrettä fiktiivisestä treffikumppanistasi:</p>
        <div className="cards">
          {goodCards.map(card => (
            <div key={card.title} className="card">
              <textarea
                rows="3"
                placeholder="Kirjoita hyvä piirre tähän"
                value={card.desc}
                onChange={e => handleInputChange(card.title, e.target.value, "good")}
              ></textarea>
            </div>
          ))}
        </div>
        {goodCards.length < 3 && (
          <button className="add-card-btn" onClick={addGoodCard}>{getButtonLabel('good', goodCards.length)}</button>
        )}

        <p>Kirjoita kolme huonoa piirrettä fiktiivisestä treffikumppanistasi:</p>
        <div className="cards">
          {badCards.map(card => (
            <div key={card.title} className="card">
              <textarea
                rows="3"
                placeholder="Kirjoita huono piirre tähän"
                value={card.desc}
                onChange={e => handleInputChange(card.title, e.target.value, "bad")}
              ></textarea>
            </div>
          ))}
        </div>
        {badCards.length < 3 && (
          <button className="add-card-btn" onClick={addBadCard}>{getButtonLabel('bad', badCards.length)}</button>
        )}
        {
          goodCards.length === 3 && badCards.length === 3 &&
          <button onClick={() => saveCards([...goodCards, ...badCards])}>Tallenna kortit</button>
        }
      </div>
    </div>
  );
}

export default Cards;
