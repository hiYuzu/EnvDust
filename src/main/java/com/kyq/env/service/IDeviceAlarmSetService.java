package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.DeviceAlarmSet;

/**
 * [功能描述]：报警门限服务类接口
 *
 * @author kyq
 */
public interface IDeviceAlarmSetService {

    /**
     * [功能描述]：获取单个报警门限信息（设备编码和监测物编码）
     */
    List<DeviceAlarmSet> getDeviceAlarmSetSingle(String deviceCode, List<String> thingCodeList);

}
