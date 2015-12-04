package demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {
	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
//				.alwaysDo(print())
				.build();
	}

	@Test
	public void requiresLogin() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().is3xxRedirection());
	}

	@WithMockUser
	@Test
	public void authenticatedWorks() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void loginNotAuthenticated() throws Exception {
		mockMvc.perform(get("/login").param("continue", "/"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser
	@Test
	public void loginContinueIsNull() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser
	@Test
	public void loginContinueIsSlash() throws Exception {
		mockMvc.perform(get("/login").param("continue", "/"))
			.andExpect(redirectedUrl("/"));
	}

	@WithMockUser
	@Test
	public void loginContinueIsUrl() throws Exception {
		mockMvc.perform(get("/login").param("continue", "/other"))
			.andExpect(redirectedUrl("/other"));
	}

	@WithMockUser
	@Test
	public void loginContinueStartsWithDoubleSlash() throws Exception {
		mockMvc.perform(get("/login").param("continue", "//google.com"))
			.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser
	@Test
	public void loginContinueStartsWithHttpScheme() throws Exception {
		mockMvc.perform(get("/login").param("continue", "http://google.com"))
			.andExpect(status().is2xxSuccessful());
	}


	@WithMockUser
	@Test
	public void loginContinueStartsWithHttpsScheme() throws Exception {
		mockMvc.perform(get("/login").param("continue", "https://google.com"))
			.andExpect(status().is2xxSuccessful());
	}
}
