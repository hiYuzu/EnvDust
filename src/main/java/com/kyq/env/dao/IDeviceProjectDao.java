package com.kyq.env.dao;

import com.kyq.env.pojo.DeviceProject;

import java.util.List;

/**
 * [功能描述]：设备项目dao
 *
 * @author kyq
 */
public interface IDeviceProjectDao {

    /**
     * 获取设备项目个数
     *
     * @return
     */
    int getDeviceProjectCount();

    /**
     * 获取设备项目信息
     *
     * @return
     */
    List<DeviceProject> getDeviceProject();

}
