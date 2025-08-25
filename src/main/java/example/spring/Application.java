package example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class Application {
	static void test() {
		RestClient rest = RestClient.create();
		rest.get()
			.uri("http://localhost:8080")
			.attribute("a", "b")
			.retrieve()
			.body(String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
