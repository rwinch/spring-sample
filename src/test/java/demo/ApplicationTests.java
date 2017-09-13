package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void requiresLogin() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void adminOk() throws Exception {
		mockMvc.perform(get("/").with(httpBasic("admin","admin")))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Hello Admin!"));
	}

	@Test
	public void userDenied() throws Exception {
		mockMvc.perform(get("/").with(httpBasic("user","user")))
				.andExpect(status().isForbidden());
	}

	@Test
	public void failedLogin() throws Exception {
		mockMvc.perform(get("/").with(httpBasic("user","invalid")))
				.andExpect(status().isUnauthorized());
	}

	@WithMockUser(roles = "ADMIN")
	@Test
	public void authenticatedWorks() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string("Hello Admin!"));
	}
}
