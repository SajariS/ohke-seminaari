package RW.JuomaPeli.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {
	@Query(value = "SELECT * FROM card ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Card findRandomCard();
}
