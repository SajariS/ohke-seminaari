package RW.JuomaPeli.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GameMapper {
	
	public Game dtoToGame(GameDTO gameDto) {
		Game game = new Game(gameDto.getCode());	
		game.setId(gameDto.getId());
		
		if(gameDto.getCards() != null) {
			for(Card card : gameDto.getCards()) {
				game.getGameCard().add(card);
			}
		}
		
		return game;
	}
	
	public GameDTO GameToDto(Game game) {
		List<Card> cards = new ArrayList<>(game.getGameCard());
		return new GameDTO(game.getCode(), game.getId(), game.getPlayers(), cards);
		
	}
}
