package RW.JuomaPeli.domain;

import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Long> {
	Character findByPlayer(Player player);
}
