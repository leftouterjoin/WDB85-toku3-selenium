package jp.gihyo.selenium.sample;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class PocChromeWithWdmTest {
    private WebDriver driver;
    private static final int TARGET_BROWSER = 2;
    
    @BeforeClass
    public static void setupClass() {
    	System.setProperty("http.proxyHost", "10.116.15.154");
    	System.setProperty("http.proxyPort", "3128");
    	switch (TARGET_BROWSER) {
    	case 0:
    		break;
    	case 1:
        	ChromeDriverManager.getInstance().setup();
    		break;
    	case 2:
//    		new InternetExplorerDriverManager().setup(Architecture.x64);
        	InternetExplorerDriverManager.getInstance().setup(Architecture.x64, "2.53.0");
    		break;
    	}
    }

    @Before
    public void setUp() {
    	switch (TARGET_BROWSER) {
    	case 0:
    		driver = new FirefoxDriver();
    		break;
    	case 1:
        	ChromeDriverManager.getInstance().setup();
            driver = new ChromeDriver();
    		break;
    	case 2:
            driver = new InternetExplorerDriver();
    		break;
    	}
    }

    @After
    public void tearDown() {
        // ブラウザを閉じる
        driver.quit();
    }

    @Test
    public void testSample() {
        // サンプルページを開く
        driver.get("http://wdpress.github.io/webdb-selenium-sample/");

        // ユーザー名とパスワードのinput要素を取得する
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        // ユーザー名とパスワードを入力する
        usernameInput.sendKeys("user1");
        passwordInput.sendKeys("p@ssword");

        // ログインボタン要素を取得する
        WebElement loginButton = driver.findElement(By.id("login"));

        // ログインボタンをクリックする
        loginButton.click();

        // 画面が遷移するのを待つ
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.getTitle().equals("Sample Application");
            }
        });

        // コメントのinput要素を取得する
        WebElement commentInput = driver.findElement(By.id("comment-text"));

        // コメントを入力する
        commentInput.sendKeys("Hello World!");

        // 投稿ボタン要素を取得する
        WebElement postButton = driver.findElement(By.id("post"));

        // 投稿ボタンをクリックする
        postButton.click();

        // コメント要素を取得する
        List<WebElement> comments = driver.findElements(By.className("comment"));

        // コメントが投稿されていることをテストする
        assertThat(comments.size(), is(1));
        assertThat(comments.get(0).getText(), is("Hello World!"));
    }
}
