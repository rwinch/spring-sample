package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void indexNotAuthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(status().isUnauthorized());
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		public MockMvc mockMvc(WebApplicationContext wac) {
			return MockMvcBuilders.webAppContextSetup(wac)
					.apply(springSecurity())
					.build();
		}
	}
}
