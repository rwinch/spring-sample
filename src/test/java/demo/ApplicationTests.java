package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gargoylesoftware.htmlunit.WebClient;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
public class ApplicationTests {
	@Autowired
	WebClient webClient;

	@WithMockUser
	@Test
	public void authenticated() throws Exception {
		webClient.getPage("/");
	}


	@Test
	public void notAuthenticated() throws Exception {
		webClient.getPage("/");
	}
}
