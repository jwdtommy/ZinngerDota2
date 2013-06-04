package com.jwd.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
	public static String dateFormat(Date date) {
		String dateStr = "";
		if (date == null) {
			return "";
		}
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		dateStr = sd.format(date);
		return dateStr;
	}

	public static String dateFormat(Date date, String formatStr) {
		if (date == null) {
			return "";
		}
		if (!StrUtil.isNotEmpty(formatStr)) {
			formatStr = "MM月dd日    HH:mm";
		}
		SimpleDateFormat sd = new SimpleDateFormat(formatStr);
		String dateStr = sd.format(date);
		return dateStr;
	}

	public static String dateFormat(long dateTime) {
		String dateStr = "";

		if (dateTime == 0) {
			return "";
		}
		Date date = new Date(dateTime);
		dateStr = dateFormat(date);
		return dateStr;
	}

	/*
	 * 取date和当前时间的差值
	 */
	public static String distanceDate(Date date) {
		String dateStr = "";
		if (date == null) {
			return "";
		}
		if (distanceDay(date) == 0) {
			// 说明是当天的
			dateStr = dateFormat(date);
			return dateStr;
		} else if (distanceDay(date) == 1) {
			// 昨天
			String temStr = dateFormat(date);
			dateStr = "昨天" + temStr;
			return dateStr;

		} else {
			dateStr = dateFormat(date, "MM月dd日");
			return dateStr;
		}
	}

	/*
	 * 取date和当前时间的差值
	 */
	public static String distanceDate(long dateTime) {
		Date date = new Date(dateTime);
		return distanceDate(date);
	}

	public static int distanceDay(Date date) {
		if (date == null) {
			return -1;
		}
		Date nowDate = new Date();
		int nowDay = nowDate.getDay();
		int oldDay = date.getDay();
		int distance = nowDay - oldDay;
		return distance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long datetime = new Date().getTime();
		System.out.println("datetime is "+datetime);

		long time1 = getDate("2013-01-17 14:28").getTime() ; 
		System.out.println("time1 is "+time1);
		
		System.out.println("间隔是："+formatLongTime(datetime - time1));
		
		
	}

	// 时间处理函数
	public static String formatTime(long second) {
		String result = "";
		if (second >= 0 && second < 60) {
			result = second + "秒";
		} else if (second >= 60 && second < 3600) {
			result = second / 60 + "分" + second % 60 + "秒";
		} else if (second >= 3600 && second < 3660) {
			result = second / 3600 + "小时" + second % 3600 + "秒";
		} else if (second >= 3660) {
			result = second / 3600 + "小时" + second % 3600 / 60 + "分" + second
					% 3600 % 60 + "秒";
		}
		return result;
	}
	
	/**
	 * 取得两个时间之间的距离
	 * 
	 * @return
	 */
	public static String getDaysDistance(long date1 , long date2) {
		long distance = date1 - date2 ;
		if(distance < 0) {
			return 0+"" ;
		}else{
			return formatLongTime(distance);
		}	
	}
	
	
	/**
	 * 时间处理
	 * @param ms 毫秒数
	 * @return
	 */
	public static String formatLongTime(long ms) {
		String result = "";
		long second = ms/1000 ;
		if(second >0 && second < 60) {
			result = second + "秒";
		} else if(second >= 60 && second < 3600) {
			result = second / 60 + "分" + second % 60 + "秒";
		} else if (second >= 3600 && second < 3660) {
			result = second / 3600 + "小时";
			if(second % 3600 > 0) {
				result = result + second % 3600 + "秒";
			}
		} else if (second >= 3660 && second < 86400) {
			result = second / 3600 + "小时" + second % 3600 / 60  + "分";
			
		} else if (second >= 86400) {
			
			result = second / 86400 + "天" ;
			if(second % 86400 >= 3600) {
				result = result + second % 86400 / 3600 + "小时";
			}
			if(second % 86400 % 3600 > 60) {
				result = result + second % 86400 % 3600 / 60 + "分";
			}			
		}
		return result;
	}
	

	public static String formatTime(String second) {
		if (StrUtil.isEmpty(second))
			return "";
		String result = "";
		try {
			result = formatTime(Long.parseLong(second));
		} catch (NumberFormatException e) {
			return "";
		}
		return result;
	}

	/**
	 * 将一个java.util.Date对象转换成特定格式的字符串
	 * 
	 * @param date
	 *            日期对象 format 格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		if (date == null) {
			return result;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			result = formatter.format(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @param format
	 *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(strDate);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 计算生日
	 * @param strDate
	 * @return
	 */
	public static int getAge (String strDate) {
		Date birthdayDate = getDate(strDate, "yyyy-MM-dd");
		if(birthdayDate == null){
			return 0 ;
		}
		Date now = new Date();
		if(now.getYear() - birthdayDate.getYear() <=0){
			return 0 ;
		}
		return now.getYear() - birthdayDate.getYear() ;
	}
	/**
	 * 将一个字符串的日期描述转换为java.util.Date对象
	 * 
	 * @param strDate
	 *            字符串的日期描述
	 * @param format
	 *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
	 * @return 字符串转换的日期对象java.util.Date
	 */
	public static Date getDate(String strDate, String format) {
		if (strDate == null || strDate.trim().equals("")) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(timeZone);
		Date date;
		try {
			date = formatter.parse(strDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	private static final TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");

	/**
	 * 取两日期之间的天数间隔
	 * 
	 * @param strDate1
	 *            格式:yyyymmdd
	 * @param strDate2
	 *            格式:yyyymmdd
	 * @return
	 */
	public static int getDistance(String strDate1, String strDate2) {
		int distance = 0;
		Date date1 = getDate(strDate1, "yyyyMMdd");
		Date date2 = getDate(strDate2, "yyyyMMdd");
		if (date1 == null || date2 == null) {
			return distance;
		}
		distance = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
		return distance;
	}

	/**
	 * 将传入的日期(已经排序)转换为符合消息显示的字符串形式（按一定时间段对时间列表进行分割，返回Map<间隔位置,间隔位置的提示内容>
	 * 一小时以内显示“NN分钟前” 一天以内的显示“NN小时前” 3天内显示“1天前2天前3天前” 一周以外显示“2009-03-10”
	 * 
	 * @param sourceDateList
	 * @return
	 */
	public static String parseDate(Date sourceDate) {
		String resultStr = "";
		if (sourceDate == null) {
			return resultStr;
		}
		Date curDate = new Date();
		long curTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		long sourceTime = sourceDate.getTime();
		long subTime = Math.abs(curTime - sourceTime);// 毫秒单位的间隔
		int subDate = Math.abs(getDistance(formatter.format(curDate), formatter
				.format(sourceDate)));// 天单位的间隔
		if (subDate > 0) {
			// 间隔超过了一天，比如第一个时间23:50,第二个是次日0:10，已经不是同一天的了，不必考虑毫秒数
			if (subDate > 3) {
				resultStr = formatter2.format(sourceDate);
			} else {
				resultStr = subDate + "天前";
			}
		} else if (subDate == 0) {
			// 间隔在同一天之内
			if (subTime < 60 * 60 * 1000) {
				// 一小时以内
				String tempValue = ((subTime / (60 * 1000) > 0) ? ("" + subTime
						/ (60 * 1000)) : "1")
						+ "分钟之前";
				resultStr = tempValue;
			} else if (subTime >= 60 * 60 * 1000
					&& subTime < 24 * 60 * 60 * 1000) {
				// 一天以内
				resultStr = (subTime / (60 * 60 * 1000) > 0 ? "" + subTime
						/ (60 * 60 * 1000) : "1")
						+ "小时之前";
			}
		}

		return resultStr;
	}
	/**
	 * 返回两个时间间隔
	 * @param sourceDate
	 * @return
	 */
	public static int parseDateDistance(Date sourceDate){
		int distance = -1;
		if (sourceDate == null) {
			return distance;
		}
		Date curDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		int subDate = Math.abs(getDistance(formatter.format(curDate), formatter
				.format(sourceDate)));// 天单位的间隔
		return subDate;
	}
	public static int parseDateDistance(long time){
		Date date = new Date(time);
		return parseDateDistance(date);
	}
	public static String parseDate(String dateStr) {
		if (dateStr == null || dateStr.equals(""))
			return "";
		Date date = getDate(dateStr);
		return parseDate(date);

	}

	public static String parseDate(long dateTime) {
		if (dateTime <= 0)
			return "";
		Date date = new Date(dateTime);
		return parseDate(date);

	}
	
	/**解析签到记录时间*/
	public static String parseDate1(long dateTime) {
		if (dateTime <= 0)
			return "";
		Date date = new Date(dateTime);
		return parseDate1(date);

	}
	/**解析为个人签到记录类型的时间字符串 （今天、昨天、几月几日）*/
	public static String parseDate1(Date sourceDate) {
		String resultStr = "";
		if (sourceDate == null) {
			return resultStr;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("M月dd日");
		int disDay = distanceDay(sourceDate);
		if(disDay==0){
			resultStr = "今天";
		}else if(disDay==1){
			resultStr = "昨天";
		}else if(disDay>1){
			resultStr = formatter.format(sourceDate);
		}
		return resultStr;
	}
	
	/**
	 * 取服务器当前时间
	 * 
	 * @param format
	 *            "yyyy-MM-dd" yyyy-MM-dd HH:mm:ss SSS
	 * @return
	 */
	public static String getNowDate(String format) {
		SimpleDateFormat d = new SimpleDateFormat(format);
		String dd = d.format(new Date());
		return dd;
	} 

	/**
	 * 得到现在时间  
	 */
	public static String getNowDate() {
		SimpleDateFormat d = new SimpleDateFormat("M月dd日");
		String dd = d.format(new Date());
		return dd;
	}
	public static String parseDateForDay(long time){
		return parseDateForDay(new Date(time));
	}
	public static String parseDateForDay(Date date) {
		String resultStr = "";
		if (date == null){
			return resultStr;
		}
		resultStr = DateUtil.formatDate(date, "M月dd日");
		String today = DateUtil.getNowDate();
		if (today.equals(resultStr)) {
			return "今天";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("M月dd日");
		Date tempDate = new Date();
		long tempLong = tempDate.getTime();
		tempLong = tempLong - (24 * 60 * 60 * 1000);
		tempDate.setTime(tempLong);
		String yesterday = formatter.format(tempDate);
		if (yesterday.equals(resultStr)) {
			return "昨天";
		}
		return resultStr;
	}

}
