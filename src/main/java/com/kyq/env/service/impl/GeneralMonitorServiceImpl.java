package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IGeneralMonitorDao;
import org.springframework.stereotype.Service;

import com.tcb.env.dao.IGeneralMonitorDao;
import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.service.IGeneralMonitorService;

/**
 * [功能描述]：获取各物质浓度值接口实现类
 *
 * @author kyq
 */
@Service("generalMonitorService")
public class GeneralMonitorServiceImpl implements IGeneralMonitorService {

    @Resource
    private IGeneralMonitorDao generalMonitorDao;

    @Override
    public List<DeviceValueModel> getGenaralMonitorValueRanking(
            List<String> deviceCodeList, String thingCode, String dataType, String onlineFlag,
            String order, int limit) {
        return generalMonitorDao.getGenaralMonitorValueRanking(deviceCodeList, thingCode, dataType, onlineFlag, order, limit);
    }
}
