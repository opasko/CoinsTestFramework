package com.spil.coins.framework.helper;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	public static String getValue(String source, String keys) {
		String[] arrKeys = StringUtils.split(keys, ";");
		String result = source;
		for (String key : arrKeys) {
			try {
				result = new JSONObject(result).getString(key);
			} catch (JSONException e) {
				result = "";
			}
		}
		return result;
	}
}