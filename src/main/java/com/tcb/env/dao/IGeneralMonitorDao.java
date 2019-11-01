package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.DeviceValueModel;

/**
 * [功能描述]：监控数据库接口
 *
 * @author kyq
 */
public interface IGeneralMonitorDao {

    /**
     * [功能描述]：设备排放值排名
     */
    List<DeviceValueModel> getGenaralMonitorValueRanking(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCode") String thingCode,
            @Param("dataType") String dataType,
            @Param("onlineFlag") String onlineFlag,
            @Param("order") String order,
            @Param("limit") int limit);
}
