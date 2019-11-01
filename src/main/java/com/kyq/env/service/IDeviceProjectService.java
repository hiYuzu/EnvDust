package com.kyq.env.service;

import com.kyq.env.pojo.DeviceProject;
import com.tcb.env.pojo.DeviceProject;

import java.util.List;

/**
 * [功能描述]：设备项目服务接口
 *
 * @author kyq
 */
public interface IDeviceProjectService {

    /**
     * 获取设备项目个数
     */
    int getDeviceProjectCount();

    /**
     * 获取设备项目信息
     */
    List<DeviceProject> getDeviceProject();

}
