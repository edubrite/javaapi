package com.edubrite.api.example.plugins.common;

import java.io.File;
import java.io.InputStream;

public class FileUtils {

	

	public static String getExt(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf('.') + 1)
					.toLowerCase();
		} catch (Exception e) {

		}
		return null;
	}

	public static String getNameWithoutExt(String fileName) {
		try {
			return fileName.substring(0, fileName.lastIndexOf('.'));
		} catch (Exception e) {

		}
		return null;
	}

	public static boolean checkExists(String fileName, Boolean dir) {
		File f = new File(fileName);
		boolean format = dir == null ? true : dir == true ? f.isDirectory() : f
				.isFile();
		return f.exists() && format;
	}

	public static String getResourceAsString(String file) {
		InputStream is = null;
		String ret = null;
		try {
			is = FileUtils.class.getClassLoader().getResourceAsStream(file);
			StringBuilder sb = new StringBuilder();
			byte[] buf = new byte[4096]; 
			int offset = 0, read = -1;
			while ((read = is.read(buf, offset, buf.length)) != -1) {
				sb.append(new String(buf, 0, read));
			}
			ret = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
			}
		}
		return ret;
	}

}
