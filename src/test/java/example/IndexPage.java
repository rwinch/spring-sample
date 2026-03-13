package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexPage {

    private final WebDriver driver;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    public static IndexPage get(WebDriver driver) {
        driver.get("http://localhost/");
        return new IndexPage(driver);
    }

    public void assertAt() {
        assertThat(this.driver.getTitle()).isEqualTo("Spring Sample");
    }

    public void assertGreeting(String expectedGreeting) {
        WebElement h1 = this.driver.findElement(By.tagName("h1"));
        assertThat(h1.getText()).isEqualTo(expectedGreeting);
    }
}
