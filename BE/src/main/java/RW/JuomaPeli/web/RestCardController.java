package RW.JuomaPeli.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import RW.JuomaPeli.domain.*;
import RW.JuomaPeli.service.CardService;

@RestController
public class RestCardController {
	
	@Autowired
	private CardRepository cRepo;
	@Autowired
	private CardService cardService;
	
	
	//Tällä hetkellä palauttaa ja pyytää suoraan entityjä, tulevaisuudessa vaihtuu DTO:si
	@GetMapping("/api/cards")
	public ResponseEntity<List<Card>> cardListRest() {
		List<Card> cards = (List<Card>) cRepo.findAll();
		return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
	}
	
	@GetMapping("api/card")
	public ResponseEntity<Card> getOneCard(){
		Card card = cRepo.findRandomCard();
		return new ResponseEntity<Card>(card, HttpStatus.OK);
	}
	
	@GetMapping("/api/cards/deal")
	public ResponseEntity<?> dealCards(@RequestParam(required = true) int playerCount){
		List<Card> cards = cardService.dealCards(playerCount);
		if (cards == null) {
	        return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Not enough cards for the requested player count.");
	    }
		else if(cards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
	}
	
	///ES. api/cards/custom?players=2
	/* karvalakki ratkaisu kommentilla pois
	@GetMapping("/api/cards/custom")
	public List<List<Card>> getCards(@RequestParam(name = "players") int players) {
		List<List<Card>> cards = cardService.dealCards(players);
		return cards;
	} */
	
	@PostMapping("/api/cards")
	public ResponseEntity<Card> addCard(@RequestBody Card card) {
		Card addedCard = cRepo.save(card);
		return new ResponseEntity<Card>(addedCard, HttpStatus.OK);
	}
	
	@PostMapping("/api/cards/many")
	public ResponseEntity <List<Card>> addMultipleCards(@RequestBody List<Card>cards){
		for(Card card : cards) {
			cRepo.save(card);
		}
		return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
	}
	
	@PostMapping("/api/cards/generate")
	public void generateCards() {
		cardService.generateCards();
	}
}
