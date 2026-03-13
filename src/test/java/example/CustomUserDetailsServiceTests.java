package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CustomUserDetailsServiceTests.TestApplication.class)
@AutoConfigureMockMvc
class CustomUserDetailsServiceTests {

	@Configuration
	@EnableAutoConfiguration(exclude = ServletWebSecurityAutoConfiguration.class)
	@Import({MessageController.class, CustomUserDetailsServiceDsl.class})
	static class TestApplication {
	}

	static class CustomUserDetailsServiceDsl extends AbstractSecurityDsl {
		@Override
		public void register(HttpSecurity http) {
			http.formLogin(formLogin -> {
                formLogin.setEnabled(true);
                formLogin.setUserDetailsService(new InMemoryUserDetailsManager(
                    User.withDefaultPasswordEncoder()
                            .username("testing123")
                            .password("password")
                            .roles("USER")
                            .build()
                ));
            });
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	void customUserDetailsServiceIsRegistered(@Autowired UserDetailsService userDetailsService) {
		UserDetails user = userDetailsService.loadUserByUsername("testing123");
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo("testing123");
	}
}
