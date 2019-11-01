package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Monitor;

/**
 * [功能描述]：J检测服务接口
 *
 * @author kyq
 */
public interface IMonitorService {

    /**
     * [功能描述]：查询检测物
     */
    List<Monitor> getMonitor(Monitor monitor);

    /**
     * [功能描述]：得到数量
     */
    int getCount(Monitor monitor);

    /**
     * [功能描述]：增加数据
     */
    int insertMonitor(Monitor monitor) throws Exception;

    /**
     * [功能描述]：删除数据
     */
    int deleteMonitor(List<Integer> monitorid) throws Exception;

    /**
     * [功能描述]：判断名称是否存在
     */
    int getMonitorExist(int monitorid, String monitorcode, String monitorname);

    /**
     * [功能描述]：更改数据
     */
    int updateMonitor(List<Monitor> listMonitor) throws Exception;

    /**
     * [功能描述]：获取监测物名称（通过编号）
     */
    String getMonitorName(String thingCode);
}
