package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Alarm;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [功能描述]：报警数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午1:48:21
 * @since EnvDust 1.0.0
 */
public interface IAlarmDao {

    /**
     * [功能描述]：获取报警信息个数
     */
    int getAlarmCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarm") Alarm alarm,
            @Param("listDevCode") List<String> listDevCode);

    /**
     * [功能描述]：获取报警信息
     */
    List<Alarm> getAlarm(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarm") Alarm alarm,
            @Param("listDevCode") List<String> listDevCode);

    /**
     * [功能描述]：更新报警信息
     */
    int updateAlarm(
            @Param("dbName") String dbName,
            @Param("alarm") Alarm alarm);

    /**
     * [功能描述]：删除报警信息
     */
    int deleteAlarm(
            @Param("dbName") String dbName,
            @Param("listid") List<Integer> listid);

    /**
     * [功能描述]：删除设备报警信息
     */
    int deleteDeviceAlarm(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode);

    /**
     * [功能描述]：获取最新报警信息（未解决）
     */
    List<String> getRcentlyAlarmInfo(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("alarmType") String alarmType);

    /**
     * [功能描述]：获取报警设备编码
     */
    String getDeviceCode(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarmId") String alarmId);

    /**
     * 获取设备报警级别
     *
     * @param dbName
     * @param deviceCode
     * @return
     */
    String getAlarmLevel(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode);

}
