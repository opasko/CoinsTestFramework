package com.spil.coins.tests;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.spil.coins.framework.browser.Browser;
import com.spil.coins.framework.data.DataTable;
import com.spil.coins.framework.helper.IOHelper;
import com.spil.coins.framework.properties.GlobalProperties;
import com.spil.coins.framework.reporter.Reporter;

public abstract class BaseTest {
	private DataTable dataTable;
	private List<HashMap<String, String>> testTable;
	protected HashMap<String, String> data;
	private String dataTablePath;
	private String testName;
	private Reporter reporter;
	private String browser;
	private int iteration;

	public BaseTest(String dataTablePath, String testName) {
		super();
		this.dataTablePath = GlobalProperties.getInstance().getDataStorage() + dataTablePath;
		this.testName = testName;
	}

	@Test
	public void testTemplate() {
		try {
			beforeTestExecute();
			for (int i = 0; i < testTable.size(); i++) {
				iteration = i + 1;
				try {
					beforeTestIteration(i);
					onExecute();
					afterTestIteration();
				} catch (AssertionError error) {
					onError(error);
				} catch (Exception exception) {
					onError(exception);
				} finally {
					closeApplication();
				}
			}
		} catch (AssertionError error) {
			onError(error);
		} catch (Exception exception) {
			onError(exception);
		}
		afterTestExectute();
	}

	private void beforeTestExecute() {
		setSystemPropertyOfDriversPaths();
		this.browser = System.getProperty("browser");
		Browser.getInstance().setBrowserName(browser);
		this.reporter = new Reporter(testName);
		this.dataTable = new DataTable(dataTablePath);
		this.testTable = dataTable.getGeneralTestTable();
		if (testTable.isEmpty()) {
			Assert.fail("Data Exception: The '" + dataTablePath + "' data table is missing you.");
		}
	}

	private void beforeTestIteration(int i) {
		this.data = testTable.get(i);
		Reporter.setIterationFail(false);
		startApplication();
	}

	private void startApplication() {
		Browser.getInstance().start(browser);
	}

	private void afterTestIteration() {
		reportIterationResult();
	}

	private void reportIterationResult() {
		if (reporter.isIterationFail()) {
			reporter.writeIteration(iteration, "FAILED \n");
		} else {
			reporter.writeIteration(iteration, "PASSED \n");
		}
	}

	private void closeApplication() {
		Browser.getInstance().stop();
	}

	private void onError(Throwable fail) {
		fail.printStackTrace();
		Reporter.setIterationFail(true);
		reportIterationResult();
	}

	private void afterTestExectute() {
		if (reporter.isTestFail()) {
			Assert.fail("Test failed. See report file for details. " + reporter.getReportFile());
		}
	}

	/**
	 * Sets system properties for ie and chrome drivers
	 */
	private void setSystemPropertyOfDriversPaths() {
		System.setProperty("webdriver.chrome.driver", IOHelper.getAbsolutePath("drivers\\chromedriver.exe"));
		System.setProperty("webdriver.ie.driver", IOHelper.getAbsolutePath("drivers\\IEDriverServer.exe"));
	}

	protected abstract void onExecute();
}
