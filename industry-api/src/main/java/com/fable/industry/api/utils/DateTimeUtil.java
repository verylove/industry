package com.fable.industry.api.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


import com.fable.industry.api.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 常用日期相关操作
 * 
 * @author wuanguo
 */
public final class DateTimeUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	/**
	 *
	 */
	private DateTimeUtil() {
		// TODO Auto-generated constructor stub
	}

	public static final long getStringToTime(String dateTime, String pattern) {
		return getStringToDate(dateTime, pattern).getTime();

	}

	public static final Date getStringToDate(String dateTime, String pattern) {

		try {
			DateFormat df = new SimpleDateFormat(pattern);
			return df.parse(dateTime);
		} catch (ParseException e) {
			LoggerFactory.getLogger(DateTimeUtil.class).error(e+"");
		}

		return null;
	}

	public static String dateToString(Date dt) {
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		return from.format(dt);
	}

	public static String dateToString(Date dt, String strformat) {
		SimpleDateFormat from = new SimpleDateFormat(strformat);
		return from.format(dt);
	}

	public static String toString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static final Date toDate(String dateTime) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将Timezone时间转换为本地时间（如将2014-08-23T09:20:05.375000Z格式的日期转换为2014/08/23
	 * 09:20:05）
	 */
	public static String getFormattedStrDate(String srcDate, String srcFormat,
											 String destFormat) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(srcFormat);
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			return new SimpleDateFormat(destFormat).format(format
					.parse(srcDate));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 将Timezone时间转换为本地时间（如将2014-08-23T09:20:05.375000Z格式的日期转换为2014/08/23
	 * 09:20:05）
	 */
	public static Date fromTimeZoneDateToFormatDate(String timeZoneDate,
													String destFormat) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			String date = new SimpleDateFormat(destFormat).format(format
					.parse(timeZoneDate));
			return getStringToDate(date, destFormat);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public static Timestamp getNow() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(new Date());
		return Timestamp.valueOf(nowTime);
	}


	public static void main(String[] args) {
//		System.out.println(DateTimeUtil.fromTimeZoneDateToFormatDate(
//				"2014-08-23T09:20:05.375000Z", "yyyy-MM-dd HH:mm:ss"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(nowTime);
		System.out.println(ts);

	}
}
