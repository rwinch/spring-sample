package demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final CustomUserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(CustomUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		CustomUser user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Cannot find user "+username);
		}

		return new CustomUserDetails(user);
	}

	private static class CustomUserDetails extends CustomUser implements UserDetails {
		public CustomUserDetails(CustomUser user) {
			super(user);
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		private static final long serialVersionUID = 6834782094910023817L;
	}
}
