package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.DeviceAlarmSet;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.DeviceAlarmSet;

/**
 * [功能描述]：设备报警门限信息Dao
 *
 * @author kyq
 */
public interface IDeviceAlarmSetDao {
    /**
     * 获取单个报警门限信息
     */
    List<DeviceAlarmSet> getDeviceAlarmSetSingle(
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList);
}
