package demo;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTests {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Autowired
	WebClient webClient;

	@WithMockUser
	@Test
	public void authenticated() throws Exception {
		 webClient.getPage("/bookings/new");
	}

	@Test
	public void notAuthenticated() throws Exception {
		thrown.expect(FailingHttpStatusCodeException.class);
		thrown.expectMessage("401 Full authentication is required to access this resource for http://localhost:8080/");
		webClient.getPage("/bookings/new");
	}
}
