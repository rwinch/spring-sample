package demo.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author Rob Winch
 */
public class LogInPage {

	private WebDriver driver;

	public LogInPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public static <T> T to(WebDriver driver, Class<T> page) {
		driver.get("http://localhost:8080/");
		return (T) PageFactory.initElements(driver, page);
	}

	public LogInPage assertAt() {
		String title = this.driver.getTitle();
		assertThat(title.contains("Please sign in") || title.contains("Login Page")).isTrue().describedAs("Expected title " + title + " to be a log in page");
		return this;
	}
}
