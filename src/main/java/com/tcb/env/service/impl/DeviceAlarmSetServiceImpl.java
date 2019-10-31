package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IDeviceAlarmSetDao;
import com.tcb.env.pojo.CommCn;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.DeviceAlarmSet;
import com.tcb.env.pojo.Monitor;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.service.IDeviceAlarmSetService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.util.StringUtils;

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
