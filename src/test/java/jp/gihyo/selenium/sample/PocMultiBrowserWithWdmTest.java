package jp.gihyo.selenium.sample;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

@RunWith(Parameterized.class)
public class PocMultiBrowserWithWdmTest {
	private WebDriver driver;

	@Parameters(name = "{0}")
	public static Iterable<Class<? extends WebDriver>> getParmeters() {
		return Arrays.asList(
				FirefoxDriver.class,
				ChromeDriver.class,
				InternetExplorerDriver.class
				);
	}

	@BeforeClass
	public static void setupClass() {
		System.setProperty("http.proxyHost", "10.116.15.154");
		System.setProperty("http.proxyPort", "3128");
		ChromeDriverManager.getInstance().setup();
		InternetExplorerDriverManager.getInstance().setup(Architecture.x64,
				"2.53.0");
	}

	public PocMultiBrowserWithWdmTest(Class<? extends WebDriver> clazz)
			throws ReflectiveOperationException {
		this.driver = clazz.newInstance();
		this.driver.manage().window().setSize(new Dimension(800, 600));   // For Demo.
		this.driver.manage().window().setPosition(new Point(50, 50));   // For Demo.
		Capabilities cap = ((RemoteWebDriver)this.driver).getCapabilities();
//		System.out.println(cap.getBrowserName());
//		System.out.println(cap.getPlatform().toString());
//		System.out.println(cap.getVersion().toString());
		System.out.println("★バージョン情報等: " + cap.toString());
	}

	@After
	public void tearDown() {
		// ブラウザを閉じる
		driver.quit();
	}

	@Test
	public void testSample() throws InterruptedException {
		// サンプルページを開く
		driver.get("http://wdpress.github.io/webdb-selenium-sample/");

		// ユーザー名とパスワードのinput要素を取得する
		WebElement usernameInput = driver.findElement(By.id("username"));
		WebElement passwordInput = driver.findElement(By.id("password"));

		// ユーザー名とパスワードを入力する
		TimeUnit.SECONDS.sleep(1);   // For Demo.
		usernameInput.sendKeys("user1");
		passwordInput.sendKeys("p@ssword");
		TimeUnit.SECONDS.sleep(1);   // For Demo.

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
		TimeUnit.SECONDS.sleep(1);   // For Demo.
		commentInput.sendKeys("Hello World!");
		TimeUnit.SECONDS.sleep(1);   // For Demo.

		// 投稿ボタン要素を取得する
		WebElement postButton = driver.findElement(By.id("post"));

		// 投稿ボタンをクリックする
		postButton.click();

		// コメント要素を取得する
		List<WebElement> comments = driver
				.findElements(By.className("comment"));

		// コメントが投稿されていることをテストする
		assertThat(comments.size(), is(1));
		assertThat(comments.get(0).getText(), is("Hello World!"));
		TimeUnit.SECONDS.sleep(1);   // For Demo.
	}
}
