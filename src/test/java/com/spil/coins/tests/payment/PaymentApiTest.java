package com.spil.coins.tests.payment;

import com.spil.coins.framework.pages.MainPage;
import com.spil.coins.framework.pages.PaymentPage;
import com.spil.coins.tests.BaseTest;

public class PaymentApiTest extends BaseTest {
	PaymentPage paymentPage;
	MainPage mainPage;

	public PaymentApiTest() {
		super("PaymentApiTestData.xlsx", "PaymentApiTest_xlsx");
	}

	@Override
	protected void onExecute() {
		buyCoins();
		checkResponseIsValid();
		checkErrorMessage();
		checkRequiredAmount();
	}
	private void buyCoins() {
		mainPage = new MainPage();
		paymentPage = mainPage.buyCoins(data.get("Method"), data.get("Amount"));
	}

	private void checkResponseIsValid() {
		paymentPage.checkResponseIsValid();
	}

	private void checkRequiredAmount() {
		paymentPage.checkRequiredAmount(data.get("ExpectedAmount"));
	}

	private void checkErrorMessage() {
		paymentPage.checkErrorMessage(data.get("ErrorMessage"));
	}
}