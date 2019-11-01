package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IMonitorDao;
import com.kyq.env.pojo.Monitor;
import com.kyq.env.service.IMonitorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * [功能描述]：监测物服务接口实现类
 */
@Service("monitorService")
@Transactional(rollbackFor = Exception.class)
public class MonitorServiceImpl implements IMonitorService {

    @Resource
    private IMonitorDao monitorDao;

    @Override
    public List<Monitor> getMonitor(Monitor monitor) {

        return monitorDao.getMonitor(monitor);
    }

    @Override
    public int getCount(Monitor monitor) {

        return monitorDao.getCount(monitor);
    }

    @Override
    public int insertMonitor(Monitor monitor) throws Exception {

        return monitorDao.insertMonitor(monitor);
    }

    @Override
    public int deleteMonitor(List<Integer> monitorid) throws Exception {

        return monitorDao.deleteMonitor(monitorid);
    }

    @Override
    public int getMonitorExist(int monitorid, String monitorcode, String monitorname) {

        return monitorDao.getMonitorExist(monitorid, monitorcode, monitorname);
    }

    @Override
    public int updateMonitor(List<Monitor> listMonitor) throws Exception {

        return monitorDao.updateMonitor(listMonitor);
    }

    @Override
    public String getMonitorName(String thingCode) {
        return monitorDao.getMonitorName(thingCode);
    }
}
