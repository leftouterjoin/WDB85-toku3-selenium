package jp.gihyo.selenium.sample.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommentPage {
    private final WebDriver driver;

    public CommentPage(WebDriver driver) {
        this.driver = driver;

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.getTitle().equals("Sample Application");
            }
        });
    }

    public CommentPage addComment(String comment) {
        WebElement commentInput = driver.findElement(By.id("comment-text"));
        commentInput.sendKeys(comment);

        WebElement postButton = driver.findElement(By.id("post"));
        postButton.click();

        return this;
    }

    public List<String> getComments() {
        List<WebElement> elements = driver.findElements(By.className("comment"));
        List<String> comments = new ArrayList<String>();
        for (WebElement element : elements) {
            comments.add(element.getText());
        }
        return comments;
    }
}
