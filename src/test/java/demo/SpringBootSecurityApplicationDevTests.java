package demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
public class SpringBootSecurityApplicationDevTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void messageWhenNotAuthenticatedThenUnauthorized() throws Exception {
		mockMvc.perform(get("/message"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	public void messageWhenUserThenOk() throws Exception {
		mockMvc.perform(get("/message"))
			.andExpect(status().isOk());
	}
}
