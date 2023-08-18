package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RepositoryServiceConfiguration.class)
@ImportAutoConfiguration(RestClientAutoConfiguration.class)
public class RepositoryServiceTests {
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private MockRestServiceServer server;

	@Test
	void getRepository() throws Exception {
		server.expect(requestTo("/repos/owner/repo")).andRespond(withSuccess("""
    		{"owner": "owner", "name": "repo" }
				""", MediaType.APPLICATION_JSON));
		Repository repository = repositoryService.getRepository("owner", "repo");
		assertThat(repository).isEqualTo(new Repository("owner", "repo"));
	}

}
