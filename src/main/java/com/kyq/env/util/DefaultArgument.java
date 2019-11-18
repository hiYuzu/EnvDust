package com.kyq.env.util;

/**
 * [功能描述]：默认参数值
 */
public class DefaultArgument {

    /**
     * 登录session标识
     */
    public static final String LOGIN_USER = "loginUser";
    /**
     * int默认值:-1
     */
    public static final int INT_DEFAULT = -1;
    /**
     * 无选择默认值:0
     */
    public static final String NONE_DEFAULT = "0";
    /**
     * double默认值:-1
     */
    public static final double DOUBLE_DEFAULT = -1;

    /**
     * 小时内实时数据个数
     */
    public static final int HOUR_COUNT = 60;

    /**
     * pwd默认值:111111
     */
    public static final String PWD_DEFAULT = "111111";

    /**
     * 删除标记默认值:false
     */
    public static final boolean DEL_DEFAULT = false;

    /**
     * 系统最底层级别
     */
    public static final String BOTTOM_LEVEL_FALG = "4";

    /**
     * 默认实时查询时间段（单位：毫秒）
     */
    public static final int TIMELY_DEFAULT = 1 * 60 * 60 * 1000;

    /**
     * 统计时间字段名称
     */
    public static final String STA_TIME = "time";

    /**
     * 请求天气时间间隔（毫秒）
     */
    public static final int WEATHER_TIME = 10000;

    /**
     * 查询控件级别：1（模块）
     */
    public static final String MODULE = "1";
    /**
     * 查询控件级别：2（页面）
     */
    public static final String PAGE = "2";
    /**
     * 查询控件级别：3（控件）
     */
    public static final String CONTROL = "3";

    /**
     * 控件处理方式：可用
     */
    public static final int DEAL_TYPE = 1;

    /**
     * 是否服务器计算统计小时数据：true,false
     */
    public static final boolean SYS_STA_MINUTE = false;

    /**
     * 是否服务器计算统计小时数据：true,false
     */
    public static final boolean SYS_STA_HOUR = false;

    /**
     * 是否服务器计算统计每日数据：true,false
     */
    public static final boolean SYS_STA_DAY = false;

    /**
     * 设备站点图片名称(正常)
     */
    public static String DEV_NORMAL = "icon-stationlink";

    /**
     * 设备站点图片名称(超标)
     */
    public static String DEV_ALARM = "icon-stationalarm";

    /**
     * 设备站点图片名称(一级)
     */
    public static String DEV_ALARM1 = "icon-stationalarm1";

    /**
     * 设备站点图片名称(二级)
     */
    public static String DEV_ALARM2 = "icon-stationalarm2";

    /**
     * 设备站点图片名称(三级)
     */
    public static String DEV_ALARM3 = "icon-stationalarm3";

    /**
     * 设备站点图片名称(故障)
     */
    public static String DEV_FAULT = "icon-stationfault";

    /**
     * 设备站点图片名称(断连)
     */
    public static String DEV_UNLINK = "icon-stationunlink";

    /**
     * 取监测参数实时数据
     */
    public static String PRO_CN_REALBEGIN = "2011";

    /**
     * 权限组最大容量
     */
    public static int MAX_AHR_COUNT = 5000;

    /**
     * 已在权限组中添加此设备
     */
    public static String IS_AHR_DEVICE = "2";

    /**
     * 未在权限组中添加此设备
     */
    public static String NOT_AHR_DEVICE = "1";

    /**
     * 在线设备标识
     */
    public static String ON_LINE_FLAG = "online";

    /**
     * 主数据库保存数据天数
     */
    public static int RECENT_DAYS = 30;

    /**
     * 请求天气正确编码:ok
     */
    public static final String HE_OK = "ok";
}
