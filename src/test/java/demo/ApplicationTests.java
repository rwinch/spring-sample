package demo;

import java.net.URI;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	void firewallWhenSpringPathThenEnabled() {
		assertThatExceptionOfType(RequestRejectedException.class)
				.isThrownBy(() -> mockMvc.perform(request("INVALID", URI.create("/springpath/bar"))));

		assertThatExceptionOfType(RequestRejectedException.class)
				.isThrownBy(() -> mockMvc.perform(request("INVALID", URI.create("/springpath/foo/bar"))));
	}

	@Test
	void firewallWhenNotSpringPathThenNotEnabled() {
		assertThatNoException()
				.isThrownBy(() -> mockMvc.perform(request("INVALID", URI.create("/foo/bar"))));
	}
}
