package demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootSecurityApplication.class)
@WebAppConfiguration
public class SpringBootSecurityApplicationTests {
	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@BeforeEach
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
