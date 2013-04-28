package com.spil.coins.framework.pages;

import org.openqa.selenium.support.PageFactory;

import com.spil.coins.framework.browser.Browser;
import com.spil.coins.framework.properties.GlobalProperties;

public class MainPage {
	public PaymentPage buyCoins(String method, String amount) {
		String url = createRequest(GlobalProperties.getInstance().getApplicationPath(), method, amount);
		Browser.getInstance().openUrl(url);
		return PageFactory.initElements(Browser.getInstance().getDriver(), PaymentPage.class);
	}

	private String createRequest(String source, String method, String amount) {
		return source += "?method=" + method + "&amount=" + amount;
	}
}
