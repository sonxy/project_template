package com.realcan.common.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jian.mei
 * @CreateDate: 2019-11-18
 *
 */
public class DateUtil {

	/** 标准日期格式 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";

	/** 标准时间格式 */
	public final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 标准年月格式 */
	public final static String MONTH_PATTERN = "yyyy-MM";

	/** 标准年小时分钟格式 */
	public final static String HOUR_MINUTE = "HH:mm";

	/** 标准年小时分钟秒格式 */
	public final static String HOUR_MINUTE_SECOND = "HH:mm:ss";

	/** 标准日期时分秒毫秒格式 */
	public final static String DATETIME_MILL_SECOND = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 标准日期时分格式 */
	public final static String DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

	public final static String DATETIME__PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_SLASH_PATTERN = "yyyy/MM/dd";

	public final static String DATE_SHORT_PATTERN = "yyyyMMdd";

	public final static String DATE_CHINESE_PATTERN = "yyyy年MM月dd日";

	public final static String MONTH_CHINESE_PATTERN = "MM月dd日";

	public final static String DATETIME_SHORT_PATTERN = "yyyyMMddHHmmss";

	private final static String[] WEEK_NAMES = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };

	/** Number of milliseconds in a standard second. */
	public static final long MILLIS_PER_SECOND = 1000;

	/** Number of milliseconds in a standard minute. */
	public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

	/** Number of milliseconds in a standard hour. */
	public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

	/** Number of milliseconds in a standard day. */
	public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 将日期或者时间字符串转化为日期对象
	 * 
	 * @param date    日期字符串
	 * @param pattern 格式字符串</br>
	 *                yyyy-MM-DD, yyyy/MM/DD, yyyyMMdd</br>
	 *                yyyy-MM-dd-HH:mm:ss, yyyy-MM-dd HH:mm:ss
	 *                格式字符串可选字符："GyMdkHmsSEDFwWahKzZ"
	 * @see java.text.DateFormatSymbols.patternChars</br>
	 * @return Date
	 */
	public static Date convertDate(String date, String pattern) {
		if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(date)) {
			String msg = "the date or pattern is empty.";
			throw new IllegalArgumentException(msg);
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern.trim());
		try {
			return df.parse(date.trim());
		} catch (Exception e) {
			String msg = "parse date [" + date + "] with pattern [" + pattern + "] failed";
			LOG.error(msg, e);
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * 将日期或者时间戳转化为日期对象
	 * 
	 * @param date yyyy-MM-dd or yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static Date convertDate(String date) {
		if (date.indexOf(":") > 0) {
			return convertDate(date, DATETIME_PATTERN);
		} else if (date.indexOf(".") > 0) {
			return convertDate(date, DATETIME_MILL_SECOND);
		} else {
			return convertDate(date, DATE_PATTERN);
		}
	}

	/**
	 * 将long型整数转化为时间。
	 * 
	 * @param date 时间对应的long值
	 * @return 时间对象
	 */
	public static Timestamp convertDate(Long date) {
		return new Timestamp(date);
	}

	/**
	 * 将时间字符串转化为时间对象Time
	 * 
	 * @param time    时间字符串
	 * @param pattern 格式字符串 yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static Time convertTime(String time, String pattern) {
		Date d = convertDate(time, pattern);
		return new Time(d.getTime());
	}

	/**
	 * 格式为时间戳字符串
	 * 
	 * @param date 时间
	 * @return yyyy-MM-dd HH:mm:ss Date
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, DATETIME_PATTERN);
	}

	/**
	 * 格式为时间字符串
	 * 
	 * @param date 日期
	 * @return yyyy-MM-dd Date
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DATE_PATTERN);
	}

	/**
	 * 按指定格式字符串格式时间
	 * 
	 * @param date    日期或者时间
	 * @param pattern 格式化字符串 yyyy-MM-dd， yyyy-MM-dd HH:mm:ss, yyyy年MM月dd日 etc.</br>
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern.trim());
		return format.format(date);
	}

	/**
	 * 将当前时间格式为字符串'yyyyMMddHHmmss'.
	 * 
	 * @return
	 */
	public static String formatNowToYMDHMS() {
		return formatDateToYMDHMS(new Date());
	}

	/**
	 * 将制定时间格式为字符串'yyyyMMddHHmmss'.
	 * 
	 * @return
	 */
	public static String formatDateToYMDHMS(Date date) {
		return formatDate(date, DATETIME_SHORT_PATTERN);
	}

	/**
	 * 获得当前时间戳
	 * 
	 * @return Timestamp
	 */
	public static Timestamp now() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获得当前日期对象
	 * 
	 * @return
	 */
	public static Date today() {
		return convertDate(formatDate(new Date()), DATE_PATTERN);
	}

	/**
	 * 获得当前日期字符串,格式为：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String todayDate() {
		return formatDate(new Date());
	}

	public static Date tomorowDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		String tomorow = new SimpleDateFormat(DATE_PATTERN).format(cal.getTime());
		return convertDate(tomorow);
	}

	public static String yesterday(String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat(pattern).format(cal.getTime());
		return yesterday;
	}

	/**
	 * 获得当前时间字符串,格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String nowDateTime() {
		return formatDate(new Date(), DATETIME_PATTERN);
	}

	/**
	 * 获得星期数
	 * 
	 * @param date 日期
	 * @return
	 */
	public static int getWeekNumber(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int number = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (number == 0)
			number = 7;
		return number;
	}

	/**
	 * 获得星期名称
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekNumberString(Date date) {
		int dayNum = getWeekNumber(date);
		return WEEK_NAMES[dayNum - 1];
	}

	/**
	 * 按指定roundType格式化日期。
	 * 
	 * @param date      日期
	 * @param roundType
	 * @see Calendar.MONTH,Calendar.DATE,Calendar.HOUR,Calendar.MINUTE,Calendar.SECOND
	 * @return Date
	 */
	public static Date round(Date date, int roundType) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		switch (roundType) {
		case Calendar.MONTH:
			c.set(Calendar.DAY_OF_MONTH, 1);
		case Calendar.DATE:
			c.set(Calendar.HOUR_OF_DAY, 0);
		case Calendar.HOUR:
			c.set(Calendar.MINUTE, 0);
		case Calendar.MINUTE:
			c.set(Calendar.SECOND, 0);
		case Calendar.SECOND:
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		default:
			throw new IllegalArgumentException("invalid round roundType.");
		}
	}

	/**
	 * 获得本周第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfThisWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获得小时
	 * 
	 * @param time
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获得分钟数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 在指定日期增加指定天数
	 * 
	 * @param date 指定日期
	 * @param days 指定天数
	 * @return
	 */
	public static Date addDay(String date, int days) {
		return addDay(convertDate(date), days);
	}

	/**
	 * 在指定日期增加指定天数
	 * 
	 * @param date 指定日期
	 * @param days 指定天数
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		if (days == 0)
			return date;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

    public static Date addMonth(Date date, int month) {
        if (month == 0)
            return date;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
	/**
	 * 获得日期相差天数
	 * 
	 * @param date1 日期
	 * @param date2 日期
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		return (int) ((date1.getTime() - date2.getTime()) / MILLIS_PER_DAY);
	}

	/**
	 * 获得分钟数
	 * 
	 * @param date1 日期
	 * @param date2 日期
	 * @return
	 */
	public static int diffMinute(Date date1, Date date2) {
		return (int) ((date1.getTime() - date2.getTime()) / MILLIS_PER_MINUTE);
	}

	/**
	 * 当前日期之前
	 * 
	 * @param date
	 * @return
	 */
	public static boolean beforeToday(Object date) {
		Date currentDate = new Date();
		return compareDate(date, currentDate) == -1;
	}

	/**
	 * 当前日期之后
	 * 
	 * @param date
	 * @return
	 */
	public static boolean afterToday(Object date) {
		Date currentDate = new Date();
		return compareDate(date, currentDate) == 1;
	}

	/**
	 * 是否同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Object date1, Object date2) {
		return compareDate(date1, date2) == 0;
	}

	/**
	 * 比较两个日期date1大于date1 返回1 等于返回0 小于返回-1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Object date1, Object date2) {
		return compareDate(date1, date2, Calendar.DATE);
	}

	/**
	 * 比较两个日期date1大于date1 返回1 等于返回0 小于返回-1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Object date1, Object date2, int accuracy) {
		if (date1 == null || date2 == null) {
			String msg = "illegal arguments,date1 and date2 must be not null.";
			throw new IllegalArgumentException(msg);
		}
		Date d1 = (Date) ((date1 instanceof String) ? convertDate((String) date1) : date1);
		Date d2 = (Date) ((date2 instanceof String) ? convertDate((String) date2) : date2);
		return round(d1, accuracy).compareTo(round(d2, accuracy));
	}

	/**
	 * 检查时间或者字符串是否合法
	 * 
	 * @param date    时间
	 * @param pattern 格式串
	 * @return
	 */
	public static boolean isValidDate(String date, String pattern) {
		try {
			convertDate(date, pattern);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String delta(Date date1, Date date2) {
		StringBuffer sb = new StringBuffer();
		long l = date1.getTime() - date2.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		// long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (min > 0) {
			sb.append(min + "分钟");
		}
		if (day == 0 && hour == 0 & min == 0) {
			return "1分钟";
		} else {
			return sb.toString();
		}
	}

	public static double deltHours(Date beginDate, Date endDate) {
		try {
			long diff = endDate.getTime() - beginDate.getTime();
			double hours = Double.valueOf(diff) / (1000 * 60 * 60);
			return (BigDecimal.valueOf(hours).setScale(2, BigDecimal.ROUND_DOWN)).doubleValue();
		} catch (Exception ex) {
			String msg = "deltHours failed";
			LOG.error(msg, ex);
		}
		return 0;
	}

	public static Date afterXSeconds(Date date, int x) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.SECOND, x);
		return gc.getTime();
	}

	public static Date afterXMinue(Date date, int x) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MINUTE, x);
		return gc.getTime();
	}

	public static Date afterXHour(Date date, int x) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.HOUR, x);
		return gc.getTime();
	}

	/**
	 * 判断date1是否在date2之后
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isAfter(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return date1.after(date2);
	}

	/**
	 * 获取前N个月的第一天日期
	 * 
	 * @return
	 */
	public static Date firstDayOfMonth(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, n);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	/**
	 * 获取前N个月最后一天日期
	 * 
	 * @return
	 */
	public static Date lastDayOfMonth(int n) {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, n);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	/**
	 * 计算 second 秒后的时间
	 *
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		;
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 计算 minute 分钟后的时间
	 *
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 计算 hour 小时后的时间
	 *
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	/**
	 * 今天是几号
	 * 
	 * @return
	 */
	public static int numOfThisMonth() {
		Calendar c = Calendar.getInstance();
		int datenum = c.get(Calendar.DATE);
		return datenum;
	}
}
