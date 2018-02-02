package demo.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

/**
 * @author Rob Winch
 * @since 5.0
 */
public interface MessageRepository extends CrudRepository<Message, Long> {
	@Override
	@PostAuthorize("hasPermission(returnObject, 'read')")
	Message findOne(Long id);
}
