package demo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
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
				.build();
	}

	@Test
	public void homeRequiresAuthentication() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void homeAllowsUser() throws Exception {
		mockMvc.perform(get("/").with(usersCredentials()))
			.andExpect(status().isOk());
	}

	@Test
	public void adminRequiresAuthentication() throws Exception {
		mockMvc.perform(get("/admin/"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void adminForbidsUser() throws Exception {
		mockMvc.perform(get("/admin/").with(usersCredentials()))
			.andExpect(status().isForbidden());
	}

	@Test
	public void adminAllowsAdmin() throws Exception {
		mockMvc.perform(get("/admin/").with(adminsCredentials()))
			.andExpect(status().isOk());
	}

	private RequestPostProcessor usersCredentials() {
		return httpBasic("user", "password");
	}

	private RequestPostProcessor adminsCredentials() {
		return httpBasic("admin", "password");
	}


}
