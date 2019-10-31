package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.DeviceAlarmSet;

/**
 * <p>
 * [功能描述]：报警门限服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:24:33
 * @since EnvDust 1.0.0
 */
public interface IDeviceAlarmSetService {

    /**
     * <p>
     * [功能描述]：获取单个报警门限信息（设备编码和监测物编码）
     * </p>
     *
     * @param deviceCode
     * @param thingCodeList
     * @return
     * @author 王垒, 2016年6月2日下午3:20:29
     * @since EnvDust 1.0.0
     */
    List<DeviceAlarmSet> getDeviceAlarmSetSingle(String deviceCode, List<String> thingCodeList);

}
