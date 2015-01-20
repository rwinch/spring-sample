package demo;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

    @Autowired
    Api api;

    @Autowired
    AuthenticationManager manager;

    @Before
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("user", "password","ROLE_USER"));
    }

    @After
    public void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test(expected=AccessDeniedException.class)
    public void contextLoads() {
        api.denyAll();
    }

    @Test
    public void canAuthenticate() {
        manager.authenticate(new UsernamePasswordAuthenticationToken("rob", "password"));
    }
}
