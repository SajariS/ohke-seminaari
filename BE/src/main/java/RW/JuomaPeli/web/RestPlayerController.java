package RW.JuomaPeli.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import RW.JuomaPeli.domain.*;
import RW.JuomaPeli.dto.PlayerDTO;
import RW.JuomaPeli.service.*;


@RestController
public class RestPlayerController {
    
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;
    @Autowired
    private CharacterService cService;
    
    @GetMapping("/api/players")
    public ResponseEntity<List<Player>> getPlayerList() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
    
    @GetMapping("/api/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
    	return new ResponseEntity<>(playerRepository.findById(id).get(), HttpStatus.OK);
    }
    
    @PostMapping("/api/players")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
    	System.out.println(player.getHost());
        if(player.getHost()) {
        	player.setGame(gameService.setUpGame(player.getCode()));
        }
        else  {
        	player.setGame(gameRepository.findByCode(player.getCode()));
        }
        playerRepository.save(player);
        player.setCharacter(cService.createNewCharacter(player));
        
        return new ResponseEntity<>(playerRepository.save(player), HttpStatus.CREATED);
    }
    
}
