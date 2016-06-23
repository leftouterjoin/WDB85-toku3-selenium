package jp.gihyo.selenium.sample;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.remote.BrowserType;

@RunWith(Parameterized.class)
public class ParameterizedTest {
	@Parameters(name = "{0}")
	public static Iterable<String> getParmeters() {
		return Arrays.asList(BrowserType.CHROME, BrowserType.FIREFOX, BrowserType.IE);
	}

	public ParameterizedTest(String flg) {
		System.out.println(flg);
	}

	@Test
	public void test1() {

	}
}
