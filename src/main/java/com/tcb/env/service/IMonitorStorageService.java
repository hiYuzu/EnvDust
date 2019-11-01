package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.tcb.env.model.MonitorStorageModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.pojo.NetStatusCount;

/**
 * [功能描述]：监测物查询服务类接口
 *
 * @author kyq
 */
public interface IMonitorStorageService {

    /**
     * [功能描述]：获取分钟原始统计数据
     */
    Map<Integer, List<MonitorStorageModel>> getPerMinuteMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取小时原始小时数据
     */
    Map<Integer, List<MonitorStorageModel>> getPerHourMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取天原始每日数据
     */
    Map<Integer, List<MonitorStorageModel>> getPerDayMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取月统计数据(每日)
     */
    Map<Integer, List<MonitorStorageModel>> getPerMonthMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取季度统计数据(每日)
     */
    Map<Integer, List<MonitorStorageModel>> getPerQuarterMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取分钟统计数据(实时统计)
     */
    Map<Integer, List<MonitorStorageModel>> getPerMinuteStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取小时统计数据(实时统计)
     */
    Map<Integer, List<MonitorStorageModel>> getPerHourStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取每日统计数据(实时统计)
     */
    Map<Integer, List<MonitorStorageModel>> getPerDayStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取月统计数据(实时统计)
     */
    Map<Integer, List<MonitorStorageModel>> getPerMonthStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取季度统计数据(实时统计)
     */
    Map<Integer, List<MonitorStorageModel>> getPerQuarterStaMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp starttime, Timestamp endtime);

    /**
     * [功能描述]：获取实时数据
     */
    List<MonitorStorageModel> getTimelyMonitorData(
            List<String> listdevicecode, List<String> listthingcode,
            Timestamp nowtime, Timestamp selecttime);

    /**
     * [功能描述]：通过监测物code获取name
     */
    List<String> getMonNamebyCode(List<String> listMonCode);

    /**
     * [功能描述]：获取原始数据个数
     */
    int getOriginalDataCount(List<String> listdevicecode,
                             List<String> listthingcode, Timestamp begintime, Timestamp endtime,
                             String updateType, String select);

    /**
     * [功能描述]：获取原始数据
     */
    List<OriginalDataModel> getOriginalData(List<String> listdevicecode,
                                            List<String> listthingcode, Timestamp begintime, Timestamp endtime,
                                            String updateType, String select, int rowIndex, int rowCount);

    /**
     * [功能描述]：获取网络状态个数
     */
    List<NetStatusCount> getNetStatusCount(String userCode, List<String> listdevicecode);
}
