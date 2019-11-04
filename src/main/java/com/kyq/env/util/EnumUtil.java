package com.kyq.env.util;

/**
 * [功能描述]：枚举帮助类
 */
public class EnumUtil {

    /**
     * [功能描述]：树形结构状态值
     */
    public enum TreeStatus {
        open,
        closed
    }

    /**
     * [功能描述]：查询时间频率
     */
    public enum TimeFreque {
        PERMINUTE,
        PERHOUR,
        PERDAY,
        PERMONTH,
        PERQUARTER
    }
}
