package RW.JuomaPeli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RW.JuomaPeli.domain.Game;
import RW.JuomaPeli.domain.GameRepository;
import RW.JuomaPeli.domain.LobbyEvent;
import RW.JuomaPeli.domain.Player;
import RW.JuomaPeli.domain.PlayerRepository;

@RestController
public class RestWSLobbyController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private PlayerRepository pRepo;
	@Autowired
	private GameRepository gRepo;
	
	@DeleteMapping("/wsapi/lobby/{id}")
	public void removePlayer(@PathVariable Long id) {
		try {
			Player player = pRepo.findById(id).get();
			pRepo.deleteById(id);
			messagingTemplate.convertAndSend("/lobby/" + player.getCode(), pRepo.findByCode(player.getCode()));
			LobbyEvent kickEvent = new LobbyEvent(id);
			messagingTemplate.convertAndSend("/lobby/" + player.getCode(), kickEvent);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	/*
	 * Vastaanottaa DELETE kutsun, poistaa pelaajan kannasta kokonaan
	 * ja palauttaa poistetun pelaajan koodilla luodun listan pelaajista,
	 * - /topic/<koodi> kanavan tilaajille
	 * 
	 * Frontin puolella SockJS/WebSocket .onClose metodilla, luodaan tarvittava koodi
	 * fetch kutsuun ja beforeunload event listenerillä yritetään taata, että koodi ajetaan
	 * kun käyttäjä sulkee selaimen
	 */

	@GetMapping("/wsapi/start/{code}")
	public void startGame(@PathVariable String code) {
		Game game = gRepo.findByCode(code);
		game.setStarted(true);
		//Lähetettävällä oliolla/luokalla ei väliä, tyhjää ei voi lähetää. Kunhan ei ole lista niin toimii
		messagingTemplate.convertAndSend("/lobby/" + code, gRepo.save(game));
	} 
}


