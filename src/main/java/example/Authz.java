package example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class Authz {
	public boolean hasPermission(Authentication authentication, Object object, String permission) {
		return true;
	}
}
