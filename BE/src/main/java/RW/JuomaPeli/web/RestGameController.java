package RW.JuomaPeli.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import RW.JuomaPeli.domain.Game;
import RW.JuomaPeli.domain.GameRepository;

@RestController
public class RestGameController {
	
	@Autowired
	private GameRepository gRepo;
	
	
	@GetMapping("/api/games/{code}")
	public ResponseEntity<Game> checkGameById(@PathVariable("code") String code) {
		Optional<Game> game = Optional.ofNullable(gRepo.findByCode(code));
		
		if(game.isPresent() && !game.get().isStarted()) {
			return new ResponseEntity<>(game.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/api/games")
	public ResponseEntity<Iterable<Game>> getGames() {
		Iterable<Game> games = gRepo.findAll();
		
		return new ResponseEntity<>(games, HttpStatus.OK);
	}
}
