package RW.JuomaPeli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RW.JuomaPeli.domain.Card;
import RW.JuomaPeli.domain.Game;
import RW.JuomaPeli.domain.GameDTO;
import RW.JuomaPeli.domain.GameMapper;
import RW.JuomaPeli.domain.GameRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gRepo;
	@Autowired
	private CardService cService;
	@Autowired
	private GameMapper gMapper;
	
	public Game setUpGame(String code) {
		GameDTO gameDto = new GameDTO(code, false);
		//Jatko kehitys pelaaja määrään, mennään nyt neljällä alkuun
		List<Card> pulledCards = cService.dealCards(4);	
		gameDto.setCards(pulledCards);
		
		return gRepo.save(gMapper.dtoToGame(gameDto));
	}
}
