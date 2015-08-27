package lotic.lin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils {
	private static Map<String, Properties> CACHE = new HashMap<String, Properties>();

	public static String getValue(String key, String fileName) {
		Object obj = getProperties(fileName).get(key);
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	public static Properties getProperties(String fileName) {
		Properties p = CACHE.get(fileName);
		if (p != null) {
			return p;
		} else {
			p = new Properties();
		}
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		if (in != null) {
			try {
				p.load(in);

			} catch (IOException e) {
				LogUtils.printLog(fileName, e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			CACHE.put(fileName, p);
		}
		return p;
	}

}
