package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutPage {

    private final WebDriver driver;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public static LogoutPage get(WebDriver driver) {
        driver.get("http://localhost/logout");
        return new LogoutPage(driver);
    }

    public void assertAt() {
        assertThat(this.driver.getTitle()).isEqualTo("Confirm Log Out?");
    }

    public LoginPage logout() {
        WebElement submitButton = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));
        submitButton.click();
        return new LoginPage(this.driver);
    }
}
