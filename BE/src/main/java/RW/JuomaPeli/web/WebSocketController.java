package RW.JuomaPeli.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import RW.JuomaPeli.domain.Courier;
import RW.JuomaPeli.domain.GameRepository;
import RW.JuomaPeli.domain.Player;
import RW.JuomaPeli.domain.PlayerRepository;

@Controller
public class WebSocketController {
	
	@Autowired
	private PlayerRepository pRepo;
	@Autowired 
	private GameRepository gRepo;

	// Topic määrittää osoitteen josta käyttäjä tilaa viestejä
	// eli Vastaanotto ja samalla instanssi on /topic/input
	// Lähetys /app/courier
	//
	//Jatkokehitys, {code} on dynaaminen tila jonka käyttäjä voi "luoda"
	// ja muut voivat liittyä, eli kaksi clienttiä samalla code parametrillä voi keskustella
	
	@MessageMapping("/{code}")
	@SendTo("/topic/{code}")
	public Courier sendCourier(@DestinationVariable String code, Courier courier) {
		return courier;
	}
	
	@MessageMapping("/join/{code}")
	@SendTo("/lobby/{code}")
	public List<Player> sendJoinInfo(@DestinationVariable String code, Player player) {
		System.out.println(player);
		System.out.println(code);
		
		return pRepo.findByCode(code);
	}
	
}
