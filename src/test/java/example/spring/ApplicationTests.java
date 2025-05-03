package example.spring;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTests {

	WebClient client = WebClient.create();

	@Test
	void exchange() {
		String body = this.client.get()
			.uri("https://spring.io/.well-known/security.txt")
			.exchange()
			.switchIfEmpty(Mono.error(new RuntimeException("response is null")))
			.flatMap(r -> r.bodyToMono(String.class))
			.switchIfEmpty(Mono.error(new RuntimeException("body is null")))
			.block();
		// succeeds
		assertThat(body).isNotNull();
	}

	@Test
	void exchangeToMonoMonoJust() {
		String body = this.client.get()
			.uri("https://spring.io/.well-known/security.txt")
			.exchangeToMono(Mono::just)
			.switchIfEmpty(Mono.error(new RuntimeException("response is null")))
			.flatMap(r -> r.bodyToMono(String.class))
			.switchIfEmpty(Mono.error(new RuntimeException("body is null")))
			.block();
		// fails with body is null
		assertThat(body).isNotNull();
	}

	@Test
	void exchangeToMono() {
		String body = this.client.get()
			.uri("https://spring.io/.well-known/security.txt")
			.exchangeToMono(r -> r.bodyToMono(String.class))
			.switchIfEmpty(Mono.error(new RuntimeException("body is null")))
			.block();
		// succeeds
		assertThat(body).isNotNull();
	}

}
