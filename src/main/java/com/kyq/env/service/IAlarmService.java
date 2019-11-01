package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.Alarm;
import com.tcb.env.pojo.Alarm;

/**
 * [功能描述]：报警操作服务类接口
 *
 * @author kyq
 */
public interface IAlarmService {

    /**
     * [功能描述]：获取报警信息个数
     */
    int getAlarmCount(Alarm alarm, List<String> listDevCode);

    /**
     * [功能描述]：获取报警信息
     */
    List<Alarm> getAlarm(Alarm alarm, List<String> listDevCode);

    /**
     * [功能描述]：更新报警信息
     */
    int updateAlarm(Alarm alarm, List<String> listId) throws Exception;

    /**
     * [功能描述]：删除报警信息
     */
    int deleteAlarm(List<Integer> listid) throws Exception;

    /**
     * [功能描述]：删除设备报警信息
     */
    int deleteDeviceAlarm(String deviceCode) throws Exception;

    /**
     * [功能描述]：获取最新报警信息（未解决）
     */
    List<String> getRcentlyAlarmInfo(String deviceCode, String alarmType);

    /**
     * 获取设备报警级别
     */
    String getAlarmLevel(String deviceCode);

}
