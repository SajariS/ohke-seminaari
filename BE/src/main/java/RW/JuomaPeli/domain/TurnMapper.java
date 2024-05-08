package RW.JuomaPeli.domain;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RW.JuomaPeli.service.CardService;
import RW.JuomaPeli.service.NameService;

@Service
public class TurnMapper {
	
	@Autowired
	private GameRepository gRepo;
	@Autowired
	private CharacterRepository cRepo;
	@Autowired
	private PlayerRepository pRepo;
	@Autowired
	private GameMapper gMapper;
	@Autowired
	private CharacterMapper cMapper;
	@Autowired
	private CardService cService;
	@Autowired
	private NameService nService;
	
	//Käsittelee annetun vuoron, eli siirtää vuoron seuraavalle, vetää pakasta kortin,
	//Ja nolla valinnan falseen
	// TODO Korjaa ManyToMany fiksiin
	public TurnDTO handleTurn(TurnDTO oldTurn) {
		GameDTO gameDto = gMapper.GameToDto(gRepo.findByCode(oldTurn.getCode()));
		Long nextPlayerId = null;
		Card pulledCard = null;
		
		//Siirtää vuoron seuraavalle pelaajalle, arvo -> nextPlayerId
		for(Player player : gameDto.getPlayers()) {
			if(player.getId() == oldTurn.getPlayersTurn()) {
				int index = gameDto.getPlayers().indexOf(player);
				
				if(index == -1) {
					//Ei löydy TODO käsittele virhe
				}
				else if(index == gameDto.getPlayers().size() - 1) {
					//Listan viimeinen, käsittele
					nextPlayerId = gameDto.getPlayers().get(0).getId();
				}
				else {
					//Seuraava listasta
					nextPlayerId = gameDto.getPlayers().get(index + 1).getId();
				}
			}
		}
		
		Character character = cRepo.findByPlayer(pRepo.findById(nextPlayerId).get());
		pulledCard = cService.pullCard();
		
		if(character.getCharacterCard().size() > 0) {
			while(true) {
				if(character.getCharacterCard().contains(pulledCard)) {
					pulledCard = cService.pullCard();
					continue;
				}
				else {
					break;
				}
			}
		}
		
		//Palautetaan "tuore" vuoro, seuraavan pelaajan id, pelin koodi, nostettu kortti ja pelaajan valinta
		return new TurnDTO(nextPlayerId, oldTurn.getCode(), pulledCard, false);
	}
	
	//Käsittelee hahmoon liittyvät toimeenpiteet, nollaa sen jne 
	public void handleCharecterChange(TurnDTO playerTurn) {
		Player player = pRepo.findById(playerTurn.getPlayersTurn()).get();
		Character character = cRepo.findByPlayer(player);
		
		//Valinta false, eli nollataan hahmo
		if(!playerTurn.isChoice())  {
			//Ei välttämättä toimi ManyToMany kanssa, pitää testata. Jos ei toimi niin delete ja uusi alle
			character.getCharacterCard().clear();
			Random random = new Random();
			String name;
			if (random.nextInt(2) == 0) {
				name = nService.generateFemaleName();
			}
			else {
				name = nService.generateMaleName();
			}
			int age = random.nextInt(100 - 18 + 1) + 18;
			character.setName(name);
			character.setAge(age);
			
		}
		else {
			//karvalakki korjaus paskaan sekoitukseen :D
			//Jos lisättävä kortti on jo valmiiksi hahmon "omistuksessa" skipataan lisäys
			//Näin vältytään kaatuilu
			
			// Jää aikaisemmin kiinni pyyntöön, ei auta tällä hetkellä
			boolean exists = false;
			for(Card card : character.getCharacterCard()) {
				if(card.getId() == playerTurn.getPulledCard().getId()) {
					exists = true;
					System.out.println("Kopio!");
					break;
				}
			}
			
			if(!exists) {
				try {
					character.getCharacterCard().add(playerTurn.getPulledCard());
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		
		cRepo.save(character);
	}
	
	public TurnDTO handleStart(String code) {
		GameDTO gameDto = gMapper.GameToDto(gRepo.findByCode(code));
		System.out.println(gameDto.getCards().size());
		Player firstPlayer = gameDto.getPlayers().get(0);
		return new TurnDTO(firstPlayer.getId(), code, gameDto.getCards().get(0), false);

	}

}
