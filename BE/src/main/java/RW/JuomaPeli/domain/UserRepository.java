package RW.JuomaPeli.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findUserByUsername(String username);
}
