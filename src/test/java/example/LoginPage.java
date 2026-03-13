package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static LoginPage get(WebDriver driver) {
        driver.get("http://localhost/login");
        return new LoginPage(driver);
    }

    public void assertAt() {
        assertThat(this.driver.getTitle()).isEqualTo("Please sign in");
    }

    public void assertError() {
        assertThat(this.driver.getPageSource()).contains("Invalid credentials");
    }

    public void assertLogoutSuccess() {
        assertThat(this.driver.getPageSource()).contains("You have been signed out");
    }

    public void login(String username, String password) {
        WebElement usernameInput = this.driver.findElement(By.cssSelector("input[name=\"username\"]"));
        WebElement passwordInput = this.driver.findElement(By.cssSelector("input[name=\"password\"]"));
        WebElement submitButton = this.driver.findElement(By.cssSelector("button[type=\"submit\"]"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public <T> T login(String username, String password, Class<T> pageClass) {
        login(username, password);
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(this.driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
