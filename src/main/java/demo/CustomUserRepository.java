package demo;

import org.springframework.data.repository.CrudRepository;

public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {

	CustomUser findByUsername(String username);
}
