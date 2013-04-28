package com.spil.coins.framework.reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.spil.coins.framework.browser.Browser;
import com.spil.coins.framework.helper.IOHelper;
import com.spil.coins.framework.properties.GlobalProperties;

public class Reporter {
	private static Boolean testFail;
	private static Boolean iterationFail;
	private static String testName;
	private String reportFolder;
	private static String reportFile;

	private final static String TEST_SEPARATOR = StringUtils.repeat("=", 21);

	public Reporter(String testName) {
		setTestFail(false);
		Reporter.testName = testName;
		this.reportFolder = GlobalProperties.getInstance().getReportStorage() + getDateTime("YYYY-MM-dd");
		reportFile = reportFolder + "/" + testName + "_" + Browser.getInstance().getBrowserName() + "_"
				+ getDateTime("hh-mm-ss") + ".txt";
		createReportFile();
		writeNewTest();
	}

	private void writeNewTest() {
		IOHelper.writeToFile(FileUtils.getFile(reportFile), TEST_SEPARATOR);
		IOHelper.writeToFile(FileUtils.getFile(reportFile), testName);
		IOHelper.writeToFile(FileUtils.getFile(reportFile), TEST_SEPARATOR);
	}

	public Boolean isTestFail() {
		return testFail;
	}

	public void setTestFail(Boolean testFail) {
		Reporter.testFail = testFail;
	}

	public String getReportFile() {
		return reportFile;
	}

	private void createReportFile() {
		IOHelper.createFolder(reportFolder);
		try {
			File file = new File(reportFile);
			FileUtils.deleteQuietly(file);
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.reportFail(e.getMessage());
		}
	}

	private static String getDateTime(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
	}

	public static void reportFail(String message) {
		Reporter.setIterationFail(true);
		IOHelper.writeToFile(FileUtils.getFile(reportFile), getDateTime("[hh-mm-ss] ") + message);
		Assert.fail(message);
	}

	public static void reportWarning(String message) {
		IOHelper.writeToFile(FileUtils.getFile(reportFile), getDateTime("[hh-mm-ss] ") + message);
	}

	public void writeIteration(int iteration, String message) {
		IOHelper.writeToFile(FileUtils.getFile(reportFile), "Iteration " + iteration + ": " + message);
	}

	public void writeIteration(int iteration) {
		writeIteration(iteration, "");
	}

	public Boolean isIterationFail() {
		return iterationFail;
	}

	public static void setIterationFail(Boolean iterationFail) {
		if (iterationFail) {
			testFail = true;
		}
		Reporter.iterationFail = iterationFail;
	}

}