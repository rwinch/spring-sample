package demo.acl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author Rob Winch
 */
@RestController
public class AclController {
	private final MessageAclManager acls;

	public AclController(MessageAclManager acls) {
		this.acls = acls;
	}

	@PostMapping("/acl")
	Serializable addPermissionForUser(@RequestParam String user) {
		return this.acls.addPermissionForUser(user);
	}
}
