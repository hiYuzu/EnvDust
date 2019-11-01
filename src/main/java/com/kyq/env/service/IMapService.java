package com.kyq.env.service;

import java.util.List;

import com.tcb.env.model.MapModel;

/**
 * [功能描述]：查询id-name形式操作类接口
 * 
 * @author kyq
 */
public interface IMapService {

    /**
     * [功能描述]：获取区域id-name
     */
    List<MapModel> getAreaMap(int id, int levelFlag);

    /**
     * [功能描述]：获取区域级别flag-name
     */
    List<MapModel> getAreaLevelFlag(int id);

    /**
     * [功能描述]：获取区域级别id-name
     */
    List<MapModel> getAreaLevelId(int id, int levelflag);

    /**
     * [功能描述]：设备获取厂商名称
     */
    List<MapModel> getDeviceMfrCode(String mfrCode);

    /**
     * [功能描述]：设备获取状态code-name
     */
    List<MapModel> getDeviceStatusCode(String statusCode);

    /**
     * [功能描述]：设备项目负责人Id-name
     */
    List<MapModel> getDeviceprinciple(int devicePrinciple);

    /**
     * [功能描述]：设备监督单位id-name
     */
    List<MapModel> getDeviceOversightUnit(int orgId);

    /**
     * [功能描述]：获取权限监测物，编号-名称（单位）
     */
    List<MapModel> getAuthorityMonitors(String usercode, int thingid,
                                        String thingname, List<String> deviceCodeList);

    /**
     * [功能描述]：获取权限设备监测物，编号-名称（单位）
     */
    List<MapModel> getAuthorityDeviceMonitors(
            String usercode,
            String devicecode,
            int thingid,
            String thingname);

    /**
     * [功能描述]：获取系统状态值,编号-名称
     */
    List<MapModel> getStatus(String status, String nostatus, String statusType);
}
