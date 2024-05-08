package RW.JuomaPeli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RW.JuomaPeli.domain.Player;
import RW.JuomaPeli.domain.PlayerRepository;
import RW.JuomaPeli.dto.PlayerDTO;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	public Player createPlayer(PlayerDTO playerDTO) {
        Player player = new Player(playerDTO.getUserName(), playerDTO.getCode(), playerDTO.getHost());
        return playerRepository.save(player);
    }

}
