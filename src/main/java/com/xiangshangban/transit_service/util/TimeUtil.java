package com.xiangshangban.transit_service.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TimeUtil {

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBefore(String time)throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date =format.parse(time);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int day = now.get(now.DAY_OF_WEEK) - 2;
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return format.format(now.getTime());
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateAfter(String time)throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date =format.parse(time);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int day = now.get(now.DAY_OF_WEEK) - 2;
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day + 6);
		return format.format(now.getTime());
	}
	
	public static String getDateAfterString(String time,String period)throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date =format.parse(time);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, Integer.valueOf(period));
		return format.format(now.getTime());
	}
	
}
