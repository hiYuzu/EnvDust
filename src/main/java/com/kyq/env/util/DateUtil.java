package com.kyq.env.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * [功能描述]：日期转换工具
 *
 * @author kyq
 */
public class DateUtil {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DateUtil";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 标准日期时间类型
     */
    public static String DATA_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认转换时间戳日期格式(yyyy-MM-dd)
     */
    public static final String DEFAULT_TIME = "yyyy-MM-dd";

    /**
     * 默认转换时间戳日期格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DEFAULT_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * [功能描述]：将时间戳转换为标准时间
     */
    public static String TimestampToString(Timestamp timestamp, String format) {
        try {
            if (timestamp != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.format(timestamp);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * [功能描述]：将字符串转换成时间戳（yyyy-MM-dd）
     */
    public static Timestamp StringToTimestamp(String datetime) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME);
                Date date = sdf.parse(datetime);
                return new Timestamp(date.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * [功能描述]：将字符串转换成时间戳（yyyy-MM-dd HH:mm:ss）
     */
    public static Timestamp StringToTimestampSecond(String datetime) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SECOND);
                Date date = sdf.parse(datetime);
                return new Timestamp(date.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * [功能描述]：获取系统时间
     */
    public static Timestamp GetSystemDateTime(int millionsecond) {
        return new Timestamp(Calendar.getInstance().getTimeInMillis() - millionsecond);
    }

    /**
     * [功能描述]：判断是否在最近时间内
     */
    public static boolean isRecentlyData(Timestamp timestamp, int days) {
        boolean flag = false;
        if (timestamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -days);
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String timeDate = formatDate.format(calendar.getTime());
            Timestamp recentTime = StringToTimestamp(timeDate);
            flag = (timestamp.after(recentTime) || timestamp.equals(recentTime));
        }
        return flag;
    }

    /**
     * [功能描述]：按照数据类型递增时间
     */
    public static Timestamp getAddTime(Timestamp timestamp, String dataType) {
        if (timestamp != null && dataType != null && !dataType.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestamp);
            switch (dataType) {
                case "2011":
                    calendar.add(Calendar.MINUTE, 1);
                    break;
                case "2031":
                    calendar.add(Calendar.DATE, 1);
                    break;
                case "2051":
                    calendar.add(Calendar.MINUTE, 10);
                    break;
                case "2061":
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                    break;
                case "C001":
                    calendar.add(Calendar.MONTH, 1);
                    break;
                default:
                    break;
            }

            timestamp = new Timestamp(calendar.getTimeInMillis());
        }
        return timestamp;
    }

    /**
     * 获取当前时间 "yyyy-MM-dd HH:mm:ss"
     */
    public static String getSystemTime(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_SECOND);
        return sdf.format(date);
    }

}
