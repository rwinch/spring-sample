package example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Authz {

	public boolean isComprador() {
		// Authentication is the currently logged in user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && "comprador".equals(authentication.getName());
	}
}
