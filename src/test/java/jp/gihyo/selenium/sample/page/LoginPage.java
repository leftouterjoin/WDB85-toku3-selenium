package jp.gihyo.selenium.sample.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static LoginPage open(WebDriver driver) {
        driver.get("http://wdpress.github.io/webdb-selenium-sample/");
        return new LoginPage(driver);
    }

    public CommentPage login(String username, String password) {
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();

        return new CommentPage(driver);
    }
}
