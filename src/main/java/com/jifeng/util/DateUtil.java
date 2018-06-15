package com.jifeng.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import org.springframework.util.StringUtils;


public class DateUtil {
	private static String defaultDatePattern = "yyyy-MM-dd";

	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static SimpleDateFormat dateFormatterYM = new SimpleDateFormat("yyyy-MM");
	
	public static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static {
		// 尝试试从messages_zh_Cn.properties中获取defaultDatePattner.

		try {
			System.setProperty("user.timezone", "GMT+8");
//			Locale locale = LocaleContextHolder.getLocale();
//			defaultDatePattern = ResourceBundle.getBundle(
//					Constants.MESSAGE_BUNDLE_KEY, locale).getString(
//					"date.default_format");
		} catch (MissingResourceException mse) {
		}
	}

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";

		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}

		return (returnValue);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(Date strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(df.format(strDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parseFormat(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);

		return DateUtil.parse(df.format(new Date()), pattern);
	}

	/**
	 * 返回当前的日期时间字符串 格式："yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return string
	 */
	public static String getCurrenDateTime() {
		String dt = dateTimeFormatter.format(new Date());
		return dt;
	}

	/**
	 * 返回当前的日期字符串 格式： "yy-MM-dd"
	 * 
	 * @return
	 */
	public static String getCurrenDate() {
		String date = dateFormatter.format(new Date());
		return date;
	}

	/**
	 * 根据所传的格式返回当前的时间字符串 格式 pattern
	 * 
	 * @param pattern
	 *            format pattern
	 * @return current datetime
	 */
	public static String getCurrenDateTimeByPattern(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dt = sdf.format(new Date());
		return dt;
	}

	/**
	 * 根据所传的格式，格式化想要处理的date 格式： pattern 如果date ＝＝ null，则返回当前的date字符串
	 * 
	 * @param date
	 *            java.util.Date
	 * @return short format datetime
	 */
	public static String dateFormatterByPattern(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (date != null) {
			return sdf.format(date);
		} else {
			return sdf.format(new Date());
		}
	}

	/**
	 * 格式化传进去的日期 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String dateTimeFormatter(Date date) {
		if (date != null) {
			return dateTimeFormatter.format(date);
		} else {
			return dateTimeFormatter.format(new Date());
		}
	}

	public static String dateTimeFormatterDate(Date date) {
		if (date != null) {
			return dateTimeFormatter.format(date);
		}
		return "";
	}

	/**
	 * 格式化传进去的日期 格式 ：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatter(Date date) {
		if (date != null) {
			return dateFormatter.format(date);
		} else {
			return dateFormatter.format(new Date());
		}
	}

	/**
	 * 把传进去的字符串按照相对应的格式转换成日期，时间 格式: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param param
	 * 
	 * @return java.util.Date
	 */
	public static Date dateTimeParser(String param) {
		Date date = null;
		try {
			date = dateTimeFormatter.parse(param);
		} catch (ParseException ex) {
		}
		return date;
	}

	/**
	 * 把传进去的字符串按照相对应的格式转换成日期 格式：yyyy-MM-dd
	 * 
	 * @param param
	 * @return
	 */
	public static Date dateParser(String param) {
		Date date = null;
		try {
			date = dateFormatter.parse(param);
		} catch (ParseException ex) {
		}
		return date;
	}

	public static Date dateParserYM(String param){
		Date date = null;
		try {
			date = dateFormatterYM.parse(param);
		} catch (ParseException ex) {
		}
		return date;
	}
	
	/**
	 * 比较两个日期的前后，前面的日期在后，则为true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateAfter(Date date1, Date date2) {
		return date1.getTime() >= date2.getTime();
	}

	/**
	 * 是否过期，过期true，未过期false
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isGuoqi(Date endTime) {
		Date today = new Date();
		return today.getTime() > endTime.getTime();
	}

	/**
	 * 比较两个日期字符串的先后，前面的日期在后，则为true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateAfter(String date1, String date2) {
		Date datea = toCalendar(date1).getTime();
		Date dateb = toCalendar(date2).getTime();
		return DateUtil.isDateAfter(datea, dateb);
	}

	public static boolean isDateInvalidation(Date date) {
		Date date2 = new Date();
		return DateUtil.isDateAfter(date2, date);
	}

	/**
	 * 从一个日期字符串中取出它的年份
	 * 
	 * @param strDate
	 * @return
	 */
	public static final int getYear(String strDate) {

		Calendar cale = toCalendar(strDate);
		if (cale == null) {
			return -1;
		}
		return cale.get(Calendar.YEAR);
	}

	/**
	 * 从一个日期字符串中取出它的月份
	 * 
	 * @param strDate
	 * @return
	 */
	public static final int getMonth(String strDate) {
		Calendar cale = toCalendar(strDate);
		if (cale == null) {
			return -1;
		}
		return cale.get(Calendar.MONTH) + 1;
	}

	/**
	 * 从一个日期字符串中取出它的日期
	 * 
	 * @param strDate
	 * @return
	 */
	public static final int getDate(String strDate) {
		Calendar cale = toCalendar(strDate);
		if (cale == null) {
			return -1;
		}
		return cale.get(Calendar.DATE);
	}

	/**
	 * 把一个日期字符串转换成Calendar形式
	 * 
	 * @param strDate
	 * @return
	 */
	private static final Calendar toCalendar(String strDate) {
		Calendar cale = null;
		try {
			Date thisDate = dateTimeFormatter.parse(strDate);
			cale = Calendar.getInstance();
			cale.setTime(thisDate);
		} catch (Exception e) {
			return null;
		}
		return cale;
	}

	/**
	 * 返回昨天的日期字符串
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getYesday() {
		String strDate = getCurrenDateTime();
		Calendar cale = toCalendar(strDate);
		cale.add(Calendar.DAY_OF_YEAR, -1);
		return dateFormatterByPattern(cale.getTime(), "yyyy-MM-dd");

	}
	/**
	 * 前多少天的日期
	 * @param beforeDays
	 * @return
	 * @author sushengli 2015年7月28日下午2:55:44
	 */
	public static final String getBeforeDay(int beforeDays) {
		String strDate = getCurrenDateTime();
		Calendar cale = toCalendar(strDate);
		cale.add(Calendar.DAY_OF_YEAR, -beforeDays);
		return dateFormatterByPattern(cale.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 前多少天的日期
	 * @param beforeDays
	 * @return
	 * @author sushengli 2015年7月28日下午2:55:44
	 */
	public static final String getBeforeDay(int beforeDays,String pattern) {
		String strDate = getCurrenDateTime();
		Calendar cale = toCalendar(strDate);
		cale.add(Calendar.DAY_OF_YEAR, -beforeDays);
		return dateFormatterByPattern(cale.getTime(), pattern);
	}
	/**
	 * 计算出strDate之后days天后的日期字符串 days可以为负数
	 * 
	 * @param strDate
	 * @param days
	 * @return
	 */
	public static final String addDayToString(String strDate, int days) {
		Calendar cale = null;
		try {
			Date thisDate = dateFormatter.parse(strDate);
			cale = Calendar.getInstance();
			cale.setTime(thisDate);
		} catch (Exception e) {
			return null;
		}
		cale.add(Calendar.DAY_OF_YEAR, days);
		return dateFormatterByPattern(cale.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 此函数计算出date之后days天后的日期,days可以是负数
	 * 
	 * @param strDate
	 * @param days
	 * @return
	 */
	public static final Date addDay(Date date, int days) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_YEAR, days);
		return cale.getTime();
	}

	/**
	 * 此函数计算出指定日期之后moths个月的日期,Months可以是负数
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static final Date addMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 此函数计算出指定日期之后moths个月的日期,Months可以是负数
	 * 
	 * @param strDate
	 * @param months
	 * @return
	 */
	public static final String addMonth(String strDate, int months) {
		Date date;
		try {
			date = dateFormatter.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return dateFormatterByPattern(cal.getTime(), "yyyy-MM-dd");
	}

	// 两个日期相减等于几天
	public static final int getDays(Date date1, Date date2) {
		Long days = date1.getTime() - date2.getTime();
		return (int) (days / 1000 / 3600 / 24);
	}

	// 两个日期相减等于几分钟
	public static final int getMinute(Date date1, Date date2) {
		Long days = date1.getTime() - date2.getTime();
		return (int) (days / 1000 / 60);
	}

	/**
	 * today、yesterday、week、month
	 * 
	 * @param timeArea
	 * @return
	 */
	public static String getCalendar(String timeArea) {
		Calendar calen = Calendar.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer dateNum = new StringBuffer();
		if ("today".equals(timeArea)) {
			Date today = calen.getTime();
			dateNum.append(dateFormatter.format(today));
			return dateNum.toString();
		} else if ("yesterday".equals(timeArea)) {
			calen.add(Calendar.DAY_OF_MONTH, -1);
			Date today = calen.getTime();
			dateNum.append(dateFormatter.format(today));
			return dateNum.toString();
		} else if ("week".equals(timeArea)) {
			calen.add(Calendar.DAY_OF_MONTH, -7);
			Date today = calen.getTime();
			dateNum.append(dateFormatter.format(today));
			return dateNum.toString();
		} else if ("month".equals(timeArea)) {
			calen.add(Calendar.DAY_OF_MONTH, -30);
			Date today = calen.getTime();
			dateNum.append(dateFormatter.format(today));
			return dateNum.toString();
		}
		return null;
	}

	
	/**
	 * 返回日期数字
	 * **/
	public static long getLongForDate(String date){
		if(date!=null&&date.trim().length()>0){
			try {
				Date date2 = dateTimeFormatter.parse(date);
				return date2.getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public static long getLongForDate(Date date){
		return date.getTime()/1000;
	}
	
	//由int转成日期
	public static String getDateForStr(long date){
		Date dae = new Date(date);
		
		return dateTimeFormatter.format(dae);
	}
	public static List<String> betweenDates(String dateFirst, String dateSecond){
        List<String> list = new ArrayList<String>();
        try{
            Date dateOne = dateFormatter.parse(dateFirst);
            Date dateTwo = dateFormatter.parse(dateSecond);
             
            Calendar calendar = Calendar.getInstance();
             
            calendar.setTime(dateOne);
             
            while(calendar.getTime().before(dateTwo) ||calendar.getTime().equals(dateTwo)){               
            	list.add(dateFormatter.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
	
	public static int compaireDate(String date1,String date2){
		date1=date1.replaceAll("\\.", "-");
		date2=date2.replaceAll("\\.", "-");
		Long d1 = Long.valueOf(date1.replaceAll("[-\\s:]", ""));
		Long d2 = Long.valueOf(date2.replaceAll("[-\\s:]", ""));
		if(d1 > d2){
			return 1;
		}
		return -1;
	}
	
	/**
	 * 本月一号
	 * @return
	 */
	public static String getCurrentMonthBeginDate(){
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH,1);
        return getDate(cal);
    }
	/**
	 * 上个月一号
	 * @return
	 */
	public static String getLastMonthBeginDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return getDate(cal);
    }
	/**
	 * 当前日期
	 * @return
	 */
	public static String getCurrentDayBeginDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return getDate(cal);
    }
	/**
	 * 当前日期的前一天
	 * @return
	 */
	public static String getCurrentBeforeDayBeginDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, -1);
        return getDate(cal);
    }
	private static String getDate(Calendar cal) {
        String v_strDate = "";
        int v_intYear = cal.get(Calendar.YEAR);
        int v_intMonth = cal.get(Calendar.MONTH) + 1;
        int v_intDAY = cal.get(Calendar.DAY_OF_MONTH);
        v_strDate = v_intYear + "-";
        if (v_intMonth < 10) {
            v_strDate = v_strDate + "0" + v_intMonth + "-";
        }else {
            v_strDate = v_strDate + v_intMonth + "-";
        }
        if (v_intDAY < 10) {
            v_strDate = v_strDate + "0" + v_intDAY;
        }else {
            v_strDate = v_strDate + v_intDAY;
        }
        cal = null;
        return v_strDate;
    }
	/**
	 * 得到当前日期的头一天或者一号到现在
	 * dayFlag(==-1:开始时间和结束时间都是头一天，否则：开始日期为一号，结束日期为当前日期的头一天)
	 * @param dayFlag(==-1:开始时间和结束时间都是头一天，否则：开始日期为一号，结束日期为当前日期的头一天)
	 * @return
	 */
	public static Map<String, String> getCurrentDayBeforeDay(int dayFlag){
		Map<String, String> map = new HashMap<String, String>();
		String beginTime = "",endTime="";
		if(dayFlag == -1){
			beginTime = getCurrentDayBeginDate();
			endTime = getCurrentDayBeginDate();
		}else{
			if(getCurrentDayBeginDate().equals(getCurrentMonthBeginDate())){//如果当前日期==1号
				beginTime = getLastMonthBeginDate();//上月一号
				endTime = getCurrentBeforeDayBeginDate();//当前日期的头一天
			}else{
				beginTime = getCurrentMonthBeginDate();//本月一号
				endTime = getCurrentBeforeDayBeginDate();//当前日期的头一天
			}
		}
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		return map;
	}
	
	public static String getCurrentYearAndMonth(){
		return dateFormatterYM.format(new Date());
	}
	
	public static String getPreYearAndMonth(){
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
	    String str = getDate(cal);
		return str.substring(0,str.lastIndexOf("-"));
	}
	
	/**
	 * e.g.: 2016-01 ---> 201601
	 * @param ymStr
	 * @return
	 * @version 1.0
	 */
	public static int turnYMStr2Int(String ymStr){
		int ret = 0;
		if(!StringUtils.isEmpty(ymStr) && ymStr.contains("-")){
			ret = Integer.parseInt(ymStr.replace("-", ""));
		}
		return ret;
	}
	
	public static void main(String[] args) {
//		System.out.println(DateUtil.getLongForDate("2012-02-01 11:11:11"));
		System.out.println(getCurrentDayBeginDate());
	}
}