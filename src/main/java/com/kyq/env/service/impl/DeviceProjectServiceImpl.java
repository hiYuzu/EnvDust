package com.kyq.env.service.impl;

import com.kyq.env.dao.IDeviceProjectDao;
import com.kyq.env.pojo.DeviceProject;
import com.tcb.env.dao.IDeviceProjectDao;
import com.tcb.env.pojo.DeviceProject;
import com.tcb.env.service.IDeviceProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * [功能描述]：设备项目服务接口实现类
 *
 * @author kyq
 */
@Service("deviceProjectService")
public class DeviceProjectServiceImpl implements IDeviceProjectService {

    @Resource
    private IDeviceProjectDao deviceProjectDao;

    @Override
    public int getDeviceProjectCount() {
        return deviceProjectDao.getDeviceProjectCount();
    }

    @Override
    public List<DeviceProject> getDeviceProject() {
        return deviceProjectDao.getDeviceProject();
    }

}
