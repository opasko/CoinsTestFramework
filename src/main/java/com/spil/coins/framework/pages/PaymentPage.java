package com.spil.coins.framework.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.spil.coins.framework.helper.JsonHelper;
import com.spil.coins.framework.reporter.Reporter;

public class PaymentPage {
	@FindBy(how = How.XPATH, using = "html/body")
	private WebElement lblResult;

	@FindBy(how = How.XPATH, using = "//body/h2")
	private WebElement lblHttpError;

	public void checkRequiredAmount(String expected) {
		String actual = JsonHelper.getValue(lblResult.getText(), "payment;amount");
		if (!StringUtils.equalsIgnoreCase(actual, expected)) {
			Reporter.reportFail("Actual amount '" + actual + "' differs from expected '" + expected + "'.");
		}
	}

	public void checkErrorMessage(String expected) {
		String actual = JsonHelper.getValue(lblResult.getText(), "payment;error");
		if (!StringUtils.equalsIgnoreCase(actual, expected)) {
			Reporter.reportWarning("Actual error '" + actual + "' differs from expected '" + expected + "'.");
		}
	}

	public void checkResponseIsValid() {
		try {
			if (lblHttpError.isDisplayed()) {
				Reporter.reportFail("HTTP Error is displayed.");
			}
		} catch (NoSuchElementException e) {
			// do nothing is good
		}

	}
}
