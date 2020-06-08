package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/{username}/*").access(pathVariable("/{username}/*", "username") + "== authentication.name")
				.anyRequest().authenticated()
				.and()
			.httpBasic();
	}

	private String pathVariable(String pattern, String name) {
		return "@pathVariableEvaluator.extract(request,'"+pattern+"')['"+ name +"']";
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("{noop}password").roles("USER").and()
				.withUser("admin").password("{noop}password").roles("USER");

	}

}
