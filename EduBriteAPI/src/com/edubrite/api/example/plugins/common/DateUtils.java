package com.edubrite.api.example.plugins.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static final String DEFAULT_DATETIME_DISPLAY_FORMAT = "MM/dd/yyyy HH:mm z";
	public static final String DEFAULT_DATE_DISPLAY_FORMAT = "MM/dd/yyyy";
	
	private static DateUtils singleton = new DateUtils();
	private DateUtils(){
		
	}
	public static DateUtils getInstance(){
		return singleton;
	}
	
	public String getFormattedDateTime(Date date) {
		return getFormattedDateTime(date, null, null);
	}
	
	public String getFormattedDateTime(Date date, String format, TimeZone tz) {
		if (date == null) {
			return "";
		}
		if (null == format)
			format = DEFAULT_DATETIME_DISPLAY_FORMAT;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if(tz != null){
			formatter.setTimeZone(tz);
		}
		return formatter.format(date);
	}
	
	public String getFormattedDate(Date date, String format, TimeZone tz) {
		if (date == null) {
			return "";
		}
		if (null == format)
			format = DEFAULT_DATE_DISPLAY_FORMAT;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if(tz != null){
			formatter.setTimeZone(tz);
		}
		return formatter.format(date);
	}
}
