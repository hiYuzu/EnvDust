package com.kyq.env.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.kyq.env.model.MonitorStorageModel;
import com.kyq.env.model.OriginalDataModel;
import com.kyq.env.pojo.NetStatusCount;
import org.apache.ibatis.annotations.Param;


/**
 * [功能描述]：监测数据Dao
 *
 * @author kyq
 */
public interface IMonitorStorageDao {

    /**
     * [功能描述]：获取分钟统计数据
     */
    List<MonitorStorageModel> getPerMinuteMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取小时统计数据
     */
    List<MonitorStorageModel> getPerHourMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取每日统计数据
     */
    List<MonitorStorageModel> getPerDayMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取每月统计数据(每日统计)
     */
    List<MonitorStorageModel> getPerMonthMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取季度统计数据(每日统计)
     */
    List<MonitorStorageModel> getPerQuarterMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取分钟统计数据(实时统计)
     */
    List<MonitorStorageModel> getPerMinuteStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取小时统计数据(实时统计)
     */
    List<MonitorStorageModel> getPerHourStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取每日统计数据(实时统计)
     */
    List<MonitorStorageModel> getPerDayStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取每月统计数据(实时统计)
     */
    List<MonitorStorageModel> getPerMonthStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取季度统计数据(实时统计)
     */
    List<MonitorStorageModel> getPerQuarterStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * [功能描述]：获取实时数据
     */
    List<MonitorStorageModel> getTimelyMonitorData(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("nowtime") Timestamp nowtime,
            @Param("selecttime") Timestamp selecttime);

    /**
     * [功能描述]：通过监测物code获取name
     */
    List<String> getMonNamebyCode(
            @Param("listMonCode") List<String> listMonCode);

    /**
     * [功能描述]：获取原始数据个数
     */
    int getOriginalDataCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("begintime") Timestamp begintime,
            @Param("endtime") Timestamp endtime,
            @Param("updateType") String updateType,
            @Param("select") String select);

    /**
     * [功能描述]：获取原始数据
     */
    List<OriginalDataModel> getOriginalData(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("begintime") Timestamp begintime,
            @Param("endtime") Timestamp endtime,
            @Param("updateType") String updateType,
            @Param("select") String select,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * [功能描述]：获取网络状态个数
     */
    List<NetStatusCount> getNetStatusCount(
            @Param("usercode") String usercode,
            @Param("listdevicecode") List<String> listdevicecode);
}
