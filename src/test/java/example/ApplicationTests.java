package example;

import org.htmlunit.Page;
import org.htmlunit.WebClient;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "local.server.port=9090")
class ApplicationTests {

	@Autowired
	WebClient webClient;

	/**
	 * Check that we are using the port in the redirect_uri.
	 * @throws Exception
	 */
	@Test
	void redirectUriUsesPort() throws Exception {
		Page page = this.webClient.getPage("/");

		String url = page.getUrl().toExternalForm();
		assertThat(url).contains("redirect_uri=http://localhost:9090/oauth2/login");
	}

	@TestConfiguration
	static class IdPConfiguration {

		/**
		 * Allows us to mock the Identity Provider
		 */
		@RestController
		static class MockIdPController {
			@GetMapping("/oauth2/authorize")
			String authorize() {
				return "<html><head><title>Mock IdP</title></head><body>Mock Identity Provider</body></html>";
			}
		}
	}
}
