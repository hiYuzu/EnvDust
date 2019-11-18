package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IDeviceAlarmSetDao;
import com.kyq.env.pojo.DeviceAlarmSet;
import com.kyq.env.service.IDeviceAlarmSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [功能描述]：报警门限服务接口实现类
 *
 * @author kyq
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
