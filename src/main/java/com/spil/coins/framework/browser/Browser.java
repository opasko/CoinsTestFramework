package com.spil.coins.framework.browser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Browser {
	private static Browser browser;
	private WebDriver driver;
	private String browserName;

	public static Browser getInstance() {
		if (browser == null) {
			browser = new Browser();
		}
		return browser;
	}

	public void start(String browser) {
		if (StringUtils.equalsIgnoreCase(browser, "firefox")) {
			driver = new FirefoxDriver();
		} else if (StringUtils.equalsIgnoreCase(browser, "chrome")) {
			driver = new ChromeDriver();
		} else if (StringUtils.equalsIgnoreCase(browser, "explorer")) {
			driver = new InternetExplorerDriver();
		} else {
			driver = new HtmlUnitDriver();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void stop() {
		driver.close();
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public String getBrowserName() {
		if (browserName == null) {
			return "html";
		}
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
}