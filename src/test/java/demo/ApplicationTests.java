package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void getMessageWhenGrantPermissionAndIdIs1AndUserThenForbidden() throws Exception {
		MockHttpServletRequestBuilder aclRequest = post("/acl")
				.param("user", "user")
				.with(userCredentials());

		this.mockMvc.perform(aclRequest)
				.andExpect(status().isOk())
				.andDo(print());

		MockHttpServletRequestBuilder messageRequest = get("/messages/1")
				.with(userCredentials());

		this.mockMvc.perform(messageRequest)
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void getMessageWhenIdIs2AndUserThenForbidden() throws Exception {
		MockHttpServletRequestBuilder messageRequest = get("/messages/2")
				.with(userCredentials());

		this.mockMvc.perform(messageRequest)
				.andExpect(status().isForbidden())
				.andDo(print());
	}

	private RequestPostProcessor userCredentials() {
		return httpBasic("user", "password");
	}
}
