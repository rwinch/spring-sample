package demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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
@SpringApplicationConfiguration(classes = SpringBootSecurityApplication.class)
@WebAppConfiguration
public class SpringBootSecurityApplicationTests {
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
	public void userDeniedForNoUser() throws Exception {
		mockMvc.perform(get("/user/hello"))
			.andExpect(status().isUnauthorized());
	}

	@WithMockUser
	@Test
	public void userAllowedUser() throws Exception {
		mockMvc.perform(get("/user/hello"))
			.andExpect(status().isOk());
	}

	@WithMockUser("evil")
	@Test
	public void userDeniedEvil() throws Exception {
		mockMvc.perform(get("/user/hello"))
			.andExpect(status().isForbidden());
	}

	@WithMockUser("evil")
	@Test
	public void evilAllowedEvil() throws Exception {
		mockMvc.perform(get("/evil/hello"))
			.andExpect(status().isOk());
	}
}
