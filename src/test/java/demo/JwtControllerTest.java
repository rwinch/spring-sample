package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.web.http.SecurityHeaders.bearerToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Rob Winch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	public void tokenAndMessageWhenNoUsernamePasswordThenUnAuthorized() throws Exception {
		this.mockMvc.perform(post("/token"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void tokenAndMessage() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(post("/token").with(httpBasic("user", "password")))
				.andExpect(status().isOk())
				.andReturn();

		String jwtToken = mvcResult.getResponse().getContentAsString();

		this.mockMvc.perform(get("/").header("Authorization", "Bearer " + jwtToken))
			.andExpect(status().isOk())
			.andExpect(content().string("Hello Boot!"));
	}
}