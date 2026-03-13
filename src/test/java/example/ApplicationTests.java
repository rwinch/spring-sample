package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void springSecurityFilterChainIsFilterChainProxy() {
		Object bean = applicationContext.getBean("springSecurityFilterChain");
		assertThat(bean).isInstanceOf(FilterChainProxy.class);
	}

	@Test
	void defaultSecurityHeaders() throws Exception {
		this.mockMvc.perform(get("/").secure(true))
				.andExpect(status().isOk()) // The MessageController returns 200
				.andExpect(header().string("X-Content-Type-Options", "nosniff"))
				.andExpect(header().string("X-XSS-Protection", "0"))
				.andExpect(header().string("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"))
				.andExpect(header().string("Pragma", "no-cache"))
				.andExpect(header().string("Expires", "0"))
				.andExpect(header().exists("Strict-Transport-Security")) // Will match something like "max-age=31536000 ; includeSubDomains"
				.andExpect(header().string("X-Frame-Options", "DENY"));
	}

	@Test
	void formLoginWorks() throws Exception {
		this.mockMvc.perform(formLogin())
				.andExpect(authenticated().withUsername("user").withRoles("USER"));
	}

	@Test
	void formLoginFails() throws Exception {
		this.mockMvc.perform(formLogin().password("invalid"))
				.andExpect(unauthenticated());
	}
}