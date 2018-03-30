package com.fable.industry.api.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderUtil {
	private static final Map<String, Properties> propsMap = new HashMap<String, Properties>();
	private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
			"${", "}");
	private static Properties props = null;

	public static void configFile(String configFileUrl) {
		if (propsMap.get(configFileUrl) == null) {
			props = new Properties();
			try {
				props.load(PropertyPlaceholderUtil.class.getClassLoader()
						.getResourceAsStream(configFileUrl));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Object oValue = null;
			String sValue = null;
			for (Entry<Object, Object> entry : props.entrySet()) {
				oValue = entry.getValue();
				if (oValue != null) {
					sValue = oValue.toString();
					if (sValue.indexOf("${") != -1) {
						sValue = helper.replacePlaceholders(sValue, props);
						props.put(entry.getKey(), sValue);
					}
				}
			}
			propsMap.put(configFileUrl, props);
		} else {
			props = propsMap.get(configFileUrl);
		}
	}

	public static String value(String key) {
		Object value = props.get(key);
		if (value != null) {
			return value.toString();
		}
		return null;
	}
}
