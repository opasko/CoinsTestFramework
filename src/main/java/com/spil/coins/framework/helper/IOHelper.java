package com.spil.coins.framework.helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.spil.coins.framework.reporter.Reporter;

public class IOHelper {

	/**
	 * Checks whether provided file ends with particular extension.
	 */
	public static boolean fileExtensionEquals(String filePath, String extension) {
		if (filePath.endsWith(extension)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets absolute file's or folder's path with the separator in the end
	 */
	public static String getAbsolutePath(String path) {
		String result = path;
		try {
			result = new File(".").getCanonicalPath() + File.separator + path + File.separator;
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.reportFail(e.getMessage());
		}
		return result;
	}

	/**
	 * Creates specified folder.
	 */
	public static void createFolder(String path) {
		try {
			FileUtils.forceMkdir(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.reportFail(e.getMessage());
		}
	}

	public static void writeToFile(File file, String message) {
		try {
			FileUtils.writeStringToFile(file, message + "\n", true);
		} catch (IOException e) {
			e.printStackTrace();
			Reporter.reportFail(e.getMessage());
		}
	}
}