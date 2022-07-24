package com.yiyu.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zh
 * @ClassName : zh.nb.utils.DateUtils
 * @Description :
 * Created by user on 2022-04-29 22:20:31
 * Copyright  2020 user. All rights reserved.
 */
public class DateUtils {
    public static final String SECOND = "秒前回复";
    public static final String HOUR = "小时前回复";
    public static final String MINUTE = "分钟前回复";
    public static final String DAY = "天前回复";
    public static final String MONTH = "月前回复";
    public static final String YEAR = "年前回复";
    public static final long SECOND_MILLS = 1000L;
    public static final long MINUTE_MILLS = 60000L;
    public static final long HOUR_MILLS = 3600000L;
    public static final long DAY_MILLS = 86400000L;
    public static final long MONTH_MILLS = 2592000000L;
    public static final long YEAR_MILLS = 31104000000L;

    /**
     * 计算第二天凌晨与当前时间的时间差秒数
     */
    public static Integer getNowToNextDaySeconds() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int time = (int) (cal.getTimeInMillis() - System.currentTimeMillis());
        return time / 1000;
    }

    /**
     * 获得和当前指定间隔天数的凌晨时间戳字符串
     *
     * @param intervalDays 间隔天数
     * @return 2022-06-03 00:00:00
     */
    public static String getDayDateString(int intervalDays) {
        //获取的时间比东八区慢8个小时,所以要加上少的8小时的毫秒数
        long currentTimeMillis = System.currentTimeMillis();
        //获得当天过了的毫秒数
        long l = currentTimeMillis % (60 * 60 * 24 * 1000) + 8 * 60 * 60 * 1000;
        //下一天凌晨的毫秒数
        long nextDayMill = currentTimeMillis - l + 60 * 60 * 24 * 1000;
        long needDayMill = nextDayMill - ((long) (intervalDays) * 60 * 60 * 24 * 1000);
        Date beforeDate = new Date(needDayMill);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(beforeDate);
    }

    /**
     * 获得两个日期的间隔天数
     */
    public static String getIntervalDays(Date date) {

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        int nowYear = now.get(Calendar.YEAR);
        int beginYear = begin.get(Calendar.YEAR);
        //不同年份,直接返回间隔年数
        if (nowYear != beginYear) {
            return (nowYear - beginYear) + "年";
        }
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int beginDay = begin.get(Calendar.DAY_OF_YEAR);
        return (nowDay - beginDay + 1) + "天";
    }

    /**
     * 最新回复时间和现在时间的间隔
     *
     * @param createTime 最新回复的时间
     * @return 返回时间间隔字符串
     */
    public static String getRecentDisplayTime(Date createTime) {
        long now = System.currentTimeMillis();
        long time = createTime.getTime();
        long displayTime = now - time;
        if (now - time < MINUTE_MILLS) {
            return displayTime / SECOND_MILLS + SECOND;
        } else if (now - time < HOUR_MILLS) {
            return displayTime / MINUTE_MILLS + MINUTE;
        } else if (now - time < DAY_MILLS) {
            return displayTime / HOUR_MILLS + HOUR;
        } else if (now - time < MONTH_MILLS) {
            return displayTime / DAY_MILLS + DAY;
        } else if (now - time < YEAR_MILLS) {
            return displayTime / MONTH_MILLS + MONTH;
        } else {
            return displayTime / YEAR_MILLS + YEAR;
        }
    }

}
