package jp.gihyo.selenium.sample;

import jp.gihyo.selenium.sample.page.CommentPage;
import jp.gihyo.selenium.sample.page.LoginPage;
import jp.gihyo.selenium.sample.rule.RetryRule;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class SampleTestWithRetryRule {
    // 最大3回までリトライする
    @Rule
    public RetryRule retry = new RetryRule(3);

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSample() {
        LoginPage loginPage = LoginPage.open(driver);
        CommentPage commentPage = loginPage.login("user1", "p@ssword");
        commentPage.addComment("Hello World!");

        List<String> comments = commentPage.getComments();
        assertThat(comments.size(), is(1));
        assertThat(comments.get(0), is("Hello World!"));
    }
}
