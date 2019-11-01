package com.kyq.env.service;

import com.kyq.env.model.DeviceValueModel;

import java.util.List;


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
