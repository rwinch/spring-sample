package example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;



import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	MessageService service;

	@Test
	@WithMockUser // run the test as a user with the default username of user
	void secureWhenForbidden() {
		assertThatCode(() -> service.greetForName("Rob")).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockUser("comprador") // run the test as a user with the username of comprador
	void secureWhenGranted() {
		assertThatCode(() -> service.greetForName("Rob")).doesNotThrowAnyException();;
	}
}
