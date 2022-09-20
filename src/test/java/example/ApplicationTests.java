package example;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootTest
public class ApplicationTests {

	@Test
	void go() {
		// error on startup
	}

	@Configuration
	static class Config {

		@Bean
		EmbeddedDatabase dataSource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).build();
		}

	}
}
