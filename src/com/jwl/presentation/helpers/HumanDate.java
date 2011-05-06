package com.jwl.presentation.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Lukas Rychtecky
 */
public class HumanDate {
	
	public static String format(Date date) {
		return format(date,"k:mm", "d.M.yyyy",  new Locale("cs","CZ"));
	} 
	
	public static String format(Date date, String timeFormat, String dateFormat, Locale currentLocale) {
		SimpleDateFormat formatter = null;
		String prefix = "";
		
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		if (isSameYear(calendarDate, Calendar.getInstance())) {
			dateFormat = dateFormat.replace("yyyy", "");
		}
		
		if (isToday(date)) {
			prefix = "today ";
			formatter = new SimpleDateFormat(timeFormat, currentLocale);
		} else if (isYesterday(date)) {
			prefix = "yesterday ";
			formatter = new SimpleDateFormat(timeFormat, currentLocale);
		} else {
			formatter = new SimpleDateFormat(timeFormat + " " + dateFormat, currentLocale);
		}
		
		
		return prefix + formatter.format(date);
	}
	
	private static Boolean isToday(Date date) {
		Calendar now = new GregorianCalendar();
		Calendar given = Calendar.getInstance();
		given.setTime(date);
		
		return isSameDay(now, given);
	}
	
	private static Boolean isYesterday(Date date) {
		Calendar tomorrow = new GregorianCalendar();
		tomorrow.add(Calendar.DATE, -1);
		Calendar given = Calendar.getInstance();
		given.setTime(date);
		
		return isSameDay(tomorrow, given);
	}
	
	private static Boolean isSameDay(Calendar c1, Calendar c2) {
		return (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) &&
				c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
				isSameYear(c1, c2));
	}
	
	private static Boolean isSameYear(Calendar c1, Calendar c2) {
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
	}
	
}
