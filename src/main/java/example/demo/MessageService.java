package example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	// we pass in the name argument into our custom expression Authz.isComprador
	@PreAuthorize("@authz.isComprador()")
	String greetForName(String name) {
		return "Hello " + name;
	}
}
