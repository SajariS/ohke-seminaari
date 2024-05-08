package RW.JuomaPeli.domain;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
	Game findByCode(String code);
}
