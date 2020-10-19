package example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
http
	.authorizeRequests()
		.antMatchers("/index").permitAll()
		.antMatchers("/main").permitAll()
		.and()
	.httpBasic()
		.and()
	.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
// @formatter:on
	}
}
