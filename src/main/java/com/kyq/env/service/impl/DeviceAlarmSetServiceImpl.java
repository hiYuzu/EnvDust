package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IDeviceAlarmSetDao;
import com.kyq.env.pojo.DeviceAlarmSet;
import com.kyq.env.service.IDeviceAlarmSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * [功能描述]：报警门限服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:38:25
 * @since EnvDust 1.0.0
 */
@Service("deviceAlarmSetService")
@Transactional(rollbackFor = Exception.class)
public class DeviceAlarmSetServiceImpl implements IDeviceAlarmSetService {
    @Resource
    private IDeviceAlarmSetDao deviceAlarmSetDao;

    @Override
    public List<DeviceAlarmSet> getDeviceAlarmSetSingle(String deviceCode,
                                                        List<String> thingCodeList) {
        return deviceAlarmSetDao.getDeviceAlarmSetSingle(deviceCode, thingCodeList);
    }
}
