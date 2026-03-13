package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest(classes = FormLoginDisabledTests.TestApplication.class)
@AutoConfigureMockMvc
class FormLoginDisabledTests {

	@Configuration
	@EnableAutoConfiguration(exclude = ServletWebSecurityAutoConfiguration.class)
	@Import({MessageController.class, NoFormLoginSecurityDsl.class})
	static class TestApplication {
	}

	static class NoFormLoginSecurityDsl extends AbstractSecurityDsl {
		@Override
		public void register(HttpSecurity http) {
			http.setFormLogin(false);
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	void loginIsNotOk() throws Exception {
		this.mockMvc.perform(get("/login"))
				.andExpect(result -> assertThat(result.getResponse().getStatus()).isNotEqualTo(200))
				.andExpect(header().string("X-Content-Type-Options", "nosniff"));
	}
}
