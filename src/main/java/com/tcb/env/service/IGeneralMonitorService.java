package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.DeviceValueModel;

/**
 * [功能描述]：监控数据接口
 *
 * @author kyq
 */
public interface IGeneralMonitorService {

    /**
     * [功能描述]：区域内设备排放值排名
     */
    List<DeviceValueModel> getGenaralMonitorValueRanking(
            List<String> deviceCodeList, String thingCode,
            String dataType, String onlineFlag, String order, int limit);
}
