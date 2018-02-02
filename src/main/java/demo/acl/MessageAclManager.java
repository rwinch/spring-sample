package demo.acl;

import demo.message.Message;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @author Rob Winch
 */
@Service
public class MessageAclManager {
	private final PermissionFactory permissionFactory;
	private final MutableAclService mutableAclService;

	public MessageAclManager(PermissionFactory permissionFactory,
			MutableAclService mutableAclService) {
		this.permissionFactory = permissionFactory;
		this.mutableAclService = mutableAclService;
	}

	@Transactional
	public Serializable addPermissionForUser(String user) {
		PrincipalSid sid = new PrincipalSid(user);
		Permission permission = this.permissionFactory.buildFromMask(BasePermission.READ.getMask());
		ObjectIdentity oid = new ObjectIdentityImpl(Message.class, 1);
		MutableAcl acl;
		try {
			acl = (MutableAcl) this.mutableAclService.readAclById(oid);
		}
		catch (NotFoundException nfe) {
			acl = this.mutableAclService.createAcl(oid);
		}
		acl.insertAce(acl.getEntries().size(), permission, sid, true);
		this.mutableAclService.updateAcl(acl);

		return acl.getId();
	}
}
