package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void requiresLogin() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void httpBasicWorks() throws Exception {
		this.mockMvc.perform(get("/").with(httpBasic("user","password")))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello Boot!"));
	}

	@WithMockUser
	@Test
	public void authenticatedWorks() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string("Hello Boot!"));
	}
}
