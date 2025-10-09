package example;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ApplicationTests {

	RestClient rest;

	@Autowired
	void setRestClient(@LocalServerPort int port, RestClient.Builder rest) {
		this.rest = rest.baseUrl("http://localhost:"+ port +"/").build();
	}

	@Test
	void getMessage() throws Exception {
	 Message message = this.rest.get()
			.uri("/")
			.retrieve()
			.body(Message.class);

	 assertThat(message).isEqualTo(new Message());

	}

}
