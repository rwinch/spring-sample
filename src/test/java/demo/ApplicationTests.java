package demo;

import demo.webdriver.IndexPage;
import demo.webdriver.LogInPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executors;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ApplicationTests {
	WebDriver driver;

	@Autowired
	public void setHtmlUnitDriver(HtmlUnitDriver driver) {
		driver.setExecutor(new DelegatingSecurityContextExecutor(
				Executors.newSingleThreadExecutor()));
		this.driver = driver;
	}

	@Test
	public void requiresLogin() throws Exception {
		IndexPage.to(this.driver, LogInPage.class)
			.assertAt();
	}

	@Test
	@WithMockUser
	public void indexPage() throws Exception {
		IndexPage.to(this.driver, IndexPage.class)
				.assertAt();
	}
}
