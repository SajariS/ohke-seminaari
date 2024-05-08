package RW.JuomaPeli.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
	Player findByUserName(String userName);
	
	List<Player> findByCode(String code);
}
