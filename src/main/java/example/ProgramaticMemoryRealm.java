package example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 * @author Rob Winch
 */
class ProgramaticMemoryRealm extends RealmBase {
	private final Map<String, UserInformation> usernameToPrincipal = new HashMap<>();

	public ProgramaticMemoryRealm(UserInformation... users) {
		this(List.of(users));
	}

	public ProgramaticMemoryRealm(List<UserInformation> users) {
		users.stream().forEach(this::addUser);
	}

	private void addUser(UserInformation user) {
		this.usernameToPrincipal.put(user.user().getName(), user);
	}

	@Override
	protected String getPassword(String username) {
		UserInformation userInformation = this.usernameToPrincipal.get(username);
		return userInformation == null ? null : userInformation.password();
	}

	@Override
	protected GenericPrincipal getPrincipal(String username) {
		UserInformation userInformation = this.usernameToPrincipal.get(username);
		return userInformation == null ? null : userInformation.user();
	}

	public record UserInformation(GenericPrincipal user, String password) {

	}
}
