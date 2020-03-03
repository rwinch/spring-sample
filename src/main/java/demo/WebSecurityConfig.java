package demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**", "/css/**", "/js/**", "/auth123",
						/* "/home", */"/waiting/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
		;
	}
}

