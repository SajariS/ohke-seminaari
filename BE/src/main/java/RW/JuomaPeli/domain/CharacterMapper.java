package RW.JuomaPeli.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CharacterMapper {

	public Character dtoToCharacter(CharacterDTO charDto) {
		Character character = new Character(charDto.getName(), charDto.getAge(), charDto.getPlayer());
		character.setId(charDto.getId());
		
		if(charDto.getCards() != null) {
			for(Card card : charDto.getCards()) {
				character.getCharacterCard().add(card);
			}
		}
		return character;
	}
	
	public CharacterDTO characterToDto(Character character) {
		List<Card> cards = new ArrayList<>(character.getCharacterCard());
		return new CharacterDTO(character.getId(), character.getName(), character.getPlayer(), character.getAge(), cards);
	}
}
