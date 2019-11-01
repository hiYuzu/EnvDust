package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IGeneralMonitorDao;
import com.kyq.env.model.DeviceValueModel;
import com.kyq.env.service.IGeneralMonitorService;
import org.springframework.stereotype.Service;

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
