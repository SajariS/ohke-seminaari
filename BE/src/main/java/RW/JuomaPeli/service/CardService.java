package RW.JuomaPeli.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import RW.JuomaPeli.domain.Attribute;
import RW.JuomaPeli.domain.Card;
import RW.JuomaPeli.domain.CardRepository;

@Service
public class CardService {
	
	@Autowired
    private CardRepository cardRepository;

    public List<Card> dealCards(int numberOfPlayers) {
    	List<Card> allCards = (List<Card>) cardRepository.findAll();
    	List<Card> cardsToDeal = new ArrayList<>();
    	int totalNumOfCards = numberOfPlayers * 6;
    	int goodCardsCount = 0, badCardsCount = 0, cardCount = 0, i = 0;
    	
    	//Ei välttämättä paras ratkaisu, jos kortteja tehdään vaan 12 :D
    	//Karvalakki korjauksena lisäsin korttien generointiin lukua
    	if(totalNumOfCards > allCards.size()) {
    		return null;
    	}
    	
    	Collections.shuffle(allCards);
    	
    	while(cardCount < totalNumOfCards) {
    		Card card = allCards.get(i);
    		if(card.getGoodTrait() && goodCardsCount < totalNumOfCards / 2) {
    			cardsToDeal.add(card);
    			goodCardsCount++;
    			cardCount++;
    		}
    		else if(card.getGoodTrait() == false && badCardsCount < totalNumOfCards / 2) {
    			cardsToDeal.add(card);
    			badCardsCount++;
    			cardCount++;
    		}
    		i++;
    		
    	}
    	
    	return cardsToDeal;
    	
    	/*
    	 * Vedetään ns. pakasta päällimäinen kortti vuorolla, jolloin jokaiselle pelaajalle ei tavitse jakaa "kättä" suoraan
    	 * Hyvä ratkaisu, ei vaan sovi tähän 
        Collections.shuffle(goodCards);
        Collections.shuffle(badCards);

        List<List<Card>> dealtCards = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
        	List<Card> hand = new ArrayList<>();
        	for (int j = 0; j < 3; j++) {
                hand.add(goodCards.remove(0));
            }
        	for (int j = 0; j < 3; j++) {
                hand.add(badCards.remove(0));
            }
        	dealtCards.add(hand);
        }

        return dealtCards; 
        */
    }
    
    public void generateCards() {
    	Attribute attributes = getAttributes();
    	List<Card> cards = new ArrayList<>();
    	for(String i : attributes.getBad_attributes()) {
    		Card card = new Card(i, false, false);
    		cards.add(card);
    	}
    	for(String i : attributes.getGood_attributes()) {
    		Card card = new Card(i, false, true);
    		cards.add(card);
    	}
    	cardRepository.saveAll(cards);
    }
    
    public Attribute getAttributes(){
    	Gson gson = new Gson();
    	try (Reader reader = new FileReader("attributes.json")){
    		Attribute attributes = gson.fromJson(reader, Attribute.class);
    		return attributes;
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public Card pullCard() {
    	List<Card> cards = (List<Card>) cardRepository.findAll();
    	Random random = new Random();
    	int randomIndex = random.nextInt(cards.size());
    	return cards.get(randomIndex);
    }
    
	
}
