package com.edubrite.api.plugins.common;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtils {
	private static final Log log = LogFactory.getLog(StringUtils.class
			.getName());
	private StringUtils() {
	}

	public static final String ENGLISH_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
	public static final String UTF8 = "UTF-8";
	
	public static String replace(String str, String pattern, String replace) {
		if (isBlankNull(str) || isBlankNull(pattern)) {
			return str;
		}
		int s = 0;
		int e = 0;
		StringBuilder result = new StringBuilder();
		String lowerS = str.toLowerCase();
		pattern = pattern.toLowerCase();
		while ((e = lowerS.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static boolean isBlankNull(String s) {
		//return (s == null || s.trim().isEmpty());
		return (s == null || s.trim().length() == 0);
	}

	public static boolean isArrayBlankNull(String[] s) {
		boolean returnFlag = true;
		if (s == null || s.length < 1)
			return returnFlag;
		for (String x : s) {
			if (!isBlankNull(x)) {
				returnFlag = false;
				break;
			}
		}
		return returnFlag;
	}

	public static boolean isNbsp(String s) {
		boolean ret = true;
		for (int i = 0; i < s.length(); i++) {
			if (160 != s.charAt(i)) {
				ret = false;
				break;
			}
		}
		return ret;
	}

	public static String removeRegionFromString(String orig, String startPat,
			String endPat) {
		// String matchPat =
		// "(<script[^>]*>)(.*?)(</script>)|(<script[^>]*>)(</script>)";
		String ret = null;
		boolean found = false;
		String matchPat = "(" + startPat + ")(.*?)(" + endPat + ")|(" + startPat
				+ ")(" + endPat + ")";

		Pattern pattern = Pattern.compile(matchPat, Pattern.DOTALL
				| Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(orig);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			found = true;
			ret = matcher.group();
			matcher.appendReplacement(sb, "");
		}
		matcher.appendTail(sb);
		if (!found)
			ret = orig;
		else
			ret = sb.toString();
		return ret;
	}

	/*
	 * This function escapes $ characters in the input string, this is needed
	 * instead of String.replaceAll as replaceAll treats the replacement string as
	 * regex This function should be used for any pattern replacement using regex
	 */
	public static String escapeDollar(String s) {
		String ret = s;
		if (s.indexOf("$") != -1) {
			ret = s.substring(0, s.indexOf("$")) + "\\$"
					+ escapeDollar(s.substring(s.indexOf("$") + 1));
		}
		return ret;
	}

	/*
	 * This method allows a common null and empty check for Strings. Usually used
	 * to check an argument for null. @throws NullPointerException
	 */
	public static void checkBlankNull(String str, String message) {
		if (isBlankNull(str))
			throw new NullPointerException(message);
	}
	

	public static void checkArrayBlankNull(String[] str, String message) {
		if (isArrayBlankNull(str))
			throw new NullPointerException(message);
	}
	
	public static String normalizeWhiteSpaces(String str){
		if(isBlankNull(str)) {
			return str ;
		}else{
			String pattern = "(\\s)+";
			return str.replaceAll(pattern, " ").trim();
		}
	}
	
	public static String killAllWhiteSpaces(String str){
		if(isBlankNull(str)) {
			return str ;
		}else{
			String pattern = "(\\s)+";
			return str.replaceAll(pattern, "").trim();
		}
	}
	
	public static String normalizeNonWordCharsAndSpaces(String str){
		if(isBlankNull(str)) {
			return str ;
		}else{
			String pattern = "(\\W)+";
			return normalizeWhiteSpaces(str.replaceAll(pattern, " "));
		}
	}
	
	public static String normalizeNonWordCharsAndSpacesExceptSlash(String str){
		if(isBlankNull(str)) {
			return str ;
		}else{
			String pattern = "[^a-zA-Z0-9_/]+";
			return normalizeWhiteSpaces(str.replaceAll(pattern, " "));
		}
	}
	
	public static String normalizeNonNumeric(String str){
		if(isBlankNull(str)) {
			return str ;
		}else{
			String pattern = "(\\D)+";
			return normalizeWhiteSpaces(str.replaceAll(pattern, " "));
		}
	}
	
	public static String normalizeCarriageReturns(String str){
		if(isBlankNull(str)) {
			return str;
		}else{
			Matcher m = CRLF.matcher(str);
			if (m.find()) {
				  return m.replaceAll(" ");
			}
			return str;
		}
	}
	
	public static String trim(String str) {
		if(isBlankNull(str)) {
			return str;
		}else{
			return str.trim();
		}
	}
	
	public static boolean compare(String str1, String str2) {
		if (isBlankNull(str1) && isBlankNull(str2)) {
			return true;
		} else if (!isBlankNull(str1) && !isBlankNull(str2) && str1.equals(str2)) {
			return true;
		}
		return false;
	}
	
	public static int getRegionCount(String str, String separator, boolean escape) {
		if (isBlankNull(str)) {
			return 0;
		}
		if (isBlankNull(separator)) {
			return 1;
		}
		if (escape) {
			separator = "\\" + separator;
		}
		return str.split(separator).length;
	}
	
	
	public static String safeEncoded(String s){
		try{
			return URLEncoder.encode(s, UTF8);
		}catch(Exception e){
			log.error(e);
		}
		return "";
	}
	
	public static int compareVersion(String v1, String v2) {
        String s1 = normalisedVersion(v1);
        String s2 = normalisedVersion(v2);
        int cmp = s1.compareTo(s2);
        return cmp < 0 ? -1 : cmp > 0 ? 1 : 0;
    }

    private static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }

    public static String format(double inputVal, int minFractionDigits, int maxFractionDigits) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(minFractionDigits);
		format.setMaximumFractionDigits(maxFractionDigits);
		return format.format(inputVal);
	}
	
	public static Float formatFloat(float inputVal, int minFractionDigits, int maxFractionDigits) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(minFractionDigits);
		format.setMaximumFractionDigits(maxFractionDigits);
		String s = format.format(inputVal);
		try{
			return Float.parseFloat(s);
		}catch(Exception e){
			
		}
		return inputVal;
	}
	
	
	public static void main(String [] args){
		String s = "  ####/sss/sss$$$ general \n  \t\t   \r knowledge ";
		System.out.println(normalizeNonWordCharsAndSpacesExceptSlash(s));
	}
	
	public static String dateToString(Date date, String format) {
		if (date != null) {
			String defaultDateFormat = "MM/dd/yyyy hh:mm:ss";
			DateFormat dateFormat;
			if(format!=null && !format.isEmpty()){
				dateFormat = new SimpleDateFormat(format);
			}else{
				dateFormat = new SimpleDateFormat(defaultDateFormat);
			}
			return dateFormat.format(date);
		}
		return null;
	}
}
