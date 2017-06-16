package cn.com.aiidc.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {
	
    
	/**
	 * 默认的格式:yyyy-MM-dd
	 */
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd";

	/**
	 * 将时间字符串转换成数字：根据时间计算分钟
	 * 
	 * @param time 需要计算的时间字符串
	 * @return 分钟总数
	 * @throws Exception
	 */
	public static int parseInt(String time) throws Exception {
		if (time.indexOf(":") != -1) {
			int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));//substring（）前闭后开
			int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1,time.length()));
			return hour * 60 + minute;
		} else {
			throw new Exception("输入的时间格式不对");
		}
	}
	
	/**
	 * 转换日期字符串的格式：
	 * @param dateStr    日期字符串
	 * @param src        日期原格式
	 * @param dest       日期转后的目标格式
	 * @return			   转换格式后的日期字符串
	 * @throws ParseException 如果格式转换失败，则抛出异常
	 */
	public static String changeDateStrFormate(String dateStr, String src,String dest) 
	throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(src);
		Date date = sdf.parse(dateStr);
		sdf = new SimpleDateFormat(dest);
		return sdf.format(date);
	}
	
	/**
	 * 将字符串转换为Date类型
	 * @param dateStr    日期字符串
	 * @param pattern    日期格式
	 * @return     Date对象
	 * @throws ParseException    当进行格式化的时候失败，抛出该异常。
	 */
	public static Date getDateByString(String dateStr, String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateStr);
	}
	
	/**
	 * 将Date类型转换为String类型
	 * @param date       日期
	 * @param pattern    格式
	 * @return    日期对应格式的字符串
	 * @throws ParseException
	 * 当格式转换出现异常，抛出
	 */
	public static String getStringByDate(Date date, String pattern){
		if (StringUtils.isBlank(pattern)){
			return getDefaultDateFormat().format(date);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}
	
	/**
	 * 获取默认格式对象
	 * @return    简单的时间格式化对象
	 */
	private static SimpleDateFormat getDefaultDateFormat(){
		return new SimpleDateFormat(PATTERN_DEFAULT);
	}
	
	
	 /**
     * 获取两个 Date 相差的 天数
     * @param dateBeg 开始日期
     * @param dateEnd 结束日期
     * @return 两个 Date 相差的 天数
     */
    public static long getDays(Date dateBeg, Date dateEnd) {
    	if (dateBeg != null && dateEnd != null) {
        	return (Math.abs(dateEnd.getTime() - dateBeg.getTime()) / 24 / 3600 / 1000 );
    	}
    	else {
    		return 0;
    	}
    }
    
    /**
     * 获取两个 Date 相差的 时间  最小单位：分钟
     * @param dateBeg 开始日期
     * @param dateEnd 结束日期
     * @return 两个 Date 相差的 时间
     */
    public static String getDiffrentTime(Date dateBeg, Date dateEnd) {
    	String timeString = "";
    	if (dateBeg != null && dateEnd != null) {
        	int s=(int) (Math.abs(dateEnd.getTime() - dateBeg.getTime()) / 1000 );
        	if (s<60) {
				timeString = s+"秒钟前";
			}else {
				int mi=(int) (Math.abs(dateEnd.getTime() - dateBeg.getTime()) / 1000/60 );
				if (mi<60) {
					timeString=mi+"分钟前";
				}else {
					int h=(int) (Math.abs(dateEnd.getTime() - dateBeg.getTime()) / 1000/60/60 );
					if (h<24) {
						timeString = h+"小时前";
					}else {
						int d=(int) (Math.abs(dateEnd.getTime() - dateBeg.getTime()) / 1000/60/60/24 );
						timeString = d+"天前";
					}
				}
			}
   
    	}   
    		return timeString;    	
    }
    
    /**
     * 日期 加 天 数
     * @param currentDate 当前时间
     * @param days 日数量
     * @return 加后的日期
     */
    public static Date addDays(Date currentDate, int days) {
    	return addDateTime(currentDate, Calendar.DATE, days);
    }
    
    public static Date addDateTime(Date currentDate, int AddType, int dateTimeCount) {
    	Date addedDate = currentDate;
    	if (currentDate != null && dateTimeCount != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);    			
			calendar.add(AddType, dateTimeCount);
			addedDate = calendar.getTime();
    	}
    	return addedDate;
    }
    
    /**
     * 根据传入的出生年月日计算年龄
     * @param date  出生年月日
     * @param pattern
     * @return 当前年龄
     * @throws Exception
     */
    public static String calculateAge(Date date, String pattern) throws Exception{
    	SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(pattern);
    	Date currentDate = new Date();
    	Date birthdayDate =  simpleDateFormat.parse(getStringByDate(date,pattern));
    	Date endDate      =  simpleDateFormat.parse(getStringByDate(currentDate,pattern));	
    	long day = (endDate.getTime()-birthdayDate.getTime())/(24*60*60*1000)+1;
    	String year = new DecimalFormat("#.00").format(day/365f);
    	String[] age = year.split("\\.");
    	return age[0].replace("","").length()<1?"0":age[0];
    }
    
    /** 
     * 根据出生日期计算年龄 
     *  
     * @param strBirthDay 
     *            字符串型日期 
     * @param format 
     *            日期格式 
     * @return 未来日期返回0 
     * @throws Exception 
     */  
    public static int getAge(String strBirthDay, String format)  
            throws Exception {  
      
        DateFormat df = new SimpleDateFormat(format);  
        Date birthDay = df.parse(strBirthDay);  
        return getAge(birthDay);  
    }  
    
    /** 
     * 根据出生日期计算年龄 
     *  
     * @param birthDay 
     * @return 未来日期返回0 
     * @throws Exception 
     */  
    public static int getAge(Date birthDay) throws Exception {  
      
        Calendar cal = Calendar.getInstance();  
      
        if (cal.before(birthDay)) {  
            return 0;  
        }  
      
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
      
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
      
        int age = yearNow - yearBirth;  
      
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) {  
                    age--;  
                }  
            } else {  
                age--;  
            }  
        }  
      
        return age;  
    }
    /**
     * 
     * 通过时间字符串得到相差的年份
     */
     public Integer getDifferentYear(String dateA,String dateB,String format){
 	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
 	    int differentYear = -1;
 	    try {
 		Date a = sdf.parse(dateA);
 		Date b = sdf.parse(dateB);
 		differentYear=(int) (Math.abs(a.getTime() - b.getTime()) / 24 / 3600 / 1000 );
 		} catch (ParseException e) {
 			System.out.println("时间格式不对！");
 			return -1;
 		}
 	    return differentYear;
     }
     /**
      * 
      * 时间加减年月日得到新的时间
      */
     public  static Date getDateByAddTime(Date date,Integer year,Integer month,Integer dayOfYear){
    		Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            if (year!=null) {
            	rightNow.add(Calendar.YEAR,year);//日期减1年
			}
            if (month!=null) {
            	rightNow.add(Calendar.MONTH, month);
			}
            if (dayOfYear!=null) {
            	rightNow.add(Calendar.DAY_OF_YEAR,dayOfYear);
            }    
    	    return rightNow.getTime();
     }
}
