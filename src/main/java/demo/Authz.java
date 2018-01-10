package demo;

import org.springframework.stereotype.Component;

/**
 * @author Rob Winch
 */
@Component
public class Authz {
	public boolean check(String username) {
		return "user".equals(username);
	}
}
