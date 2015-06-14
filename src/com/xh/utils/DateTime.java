/*
 * Copyright (c) 2006-2007 by SuyPower
 * All rights reserved.
 */
package com.xh.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 描述： 关于日期、时间经常使用和操作方法实现的封装类
 * 
 * @author dhchen
 * @version 1.0
 */
public class DateTime {
	
	private static final Logger logger = Logger.getLogger(DateTime.class);

	/**
	 * 将日期类型转化为字符串，默认格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return 返回结果
	 */
	public static String dateToStr(java.util.Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		return newFormat.format(date);
	}

	/**
	 * 将日期类型转化为字符串，默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return 返回结果
	 */
	public static String dateTimeToStr(java.util.Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return newFormat.format(date);
	}

	/**
	 * 将日期类型转化为字符串,根据传入的格式输出日期（如YYYY年MM月DD日等）
	 * 
	 * @param date
	 *            日期
	 * @param sFormat
	 *            日期格式
	 * @return 返回结果
	 */
	public static String dateToStr(Object date, String sFormat) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(sFormat) || "".equals(sFormat.trim())) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat(sFormat);
		return newFormat.format(date);
	}

	/**
	 * 字符串转化为日期类型，默认格式yyyy-MM-dd
	 * 
	 * @param strDate
	 *            字符串
	 * @return 返回日期
	 */
	public static java.util.Date strToDate(String strDate) {
		if (StringUtils.isEmpty(strDate) || "".equals(strDate.trim())) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return newFormat.parse(strDate);
		} catch (ParseException ex) {
			logger.error("", ex);
			return null;
		}
	}

	/**
	 * 将格式化的日期字串转化成日期类型(如yyyy年MM月DD日->date)
	 * 
	 * @param strDate
	 *            字符串
	 * @return 返回日期
	 */
	public static java.util.Date strToDate(String strDate, String sFormat) {
		if (StringUtils.isEmpty(strDate) || "".equals(strDate.trim())) {
			return null;
		}
		if (StringUtils.isEmpty(sFormat) || "".equals(sFormat.trim())) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat(sFormat);
		try {
			return newFormat.parse(strDate);
		} catch (ParseException ex) {
			logger.error("", ex);
			return null;
		}
	}
	
	/**
	 * 字符串转化为日期类型，默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strTimeStamp
	 *            字符串
	 * @return 返回日期
	 */
	public static Timestamp strTimeStamp(String strTimeStamp) {
		if (StringUtils.isEmpty(strTimeStamp) || "".equals(strTimeStamp.trim())) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return new Timestamp(newFormat.parse(strTimeStamp).getTime());
		} catch (ParseException ex) {
			logger.error("", ex);
			return null;
		}
	}
	public static Long getLongTime() {
		return System.currentTimeMillis() / 1000L;
	}
}
