package demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApplicationTests {
	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void requiresLogin() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser
	public void authenticatedWorks() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string("Hello Boot!"));
	}
}
