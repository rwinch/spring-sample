package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = TestDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	ReactiveClientRegistrationRepository clientRegistrations;

	@LocalServerPort
	int port;

	@Test
	void callApiHelloWithClientCredentialsToken() {
		Mono<String> response = clientRegistrations.findByRegistrationId("sample")
				.flatMap(this::requestClientCredentialsToken)
				.flatMap(accessToken -> callApiHello(accessToken));

		StepVerifier.create(response)
				.expectNextMatches(body -> body.contains("Contains the following claims"))
				.verifyComplete();
	}

	private Mono<String> requestClientCredentialsToken(ClientRegistration registration) {
		String tokenUri = registration.getProviderDetails().getTokenUri();
		String clientId = registration.getClientId();
		String clientSecret = registration.getClientSecret();
		

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");

		return WebClient.create()
				.post()
				.uri(tokenUri)
				.headers(h -> h.setBasicAuth(clientId, clientSecret))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.bodyToMono(ClientCredentialsTokenResponse.class)
				.map(r -> r.access_token);
	}

	private Mono<String> callApiHello(String accessToken) {
		return WebClient.create()
				.get()
				.uri("http://localhost:" + port + "/api/hello")
				.header("X-ID-Token", accessToken)
				.retrieve()
				.bodyToMono(String.class);
	}

	private static class ClientCredentialsTokenResponse {
		@SuppressWarnings("unused")
		public String access_token;
	}
}
