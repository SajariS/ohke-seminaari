package RW.JuomaPeli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RW.JuomaPeli.domain.TurnDTO;
import RW.JuomaPeli.domain.TurnMapper;

@RestController
public class RestWSLGameController {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private TurnMapper tMapper;
	
	@PostMapping("/wsapi/game")
	public void handleTurn(@RequestBody TurnDTO turn) {
		//Käsittelee hahmon muutokset, joita vuorolla tapahtuu, ei palauta mitään
		tMapper.handleCharecterChange(turn);
		//Lähettää uuden vuoron kanavalle 
		messagingTemplate.convertAndSend("/game/" + turn.getCode(), tMapper.handleTurn(turn));
	}
	
	@GetMapping("/wsapi/game/{code}")
	public void startGame(@PathVariable String code) {
		System.out.println("TEsti");
		messagingTemplate.convertAndSend("/game/" + code, tMapper.handleStart(code) );
	}
}