package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.MapModel;

/**
 * [功能描述]：查询id-name值
 *
 * @author kyq
 */
public interface IMapDao {

    /**
     * [功能描述]：获取区域id-name
     */
    List<MapModel> getAreaMap(@Param("id") int id, @Param("levelflag") int levelflag);

    /**
     * [功能描述]：获取区域级别flag-name
     */
    List<MapModel> getAreaLevelFlag(@Param("id") int id);

    /**
     * [功能描述]：获取区域级别id-name
     */
    List<MapModel> getAreaLevelId(@Param("id") int id, @Param("levelflag") int levelflag);

    /**
     * [功能描述]：设备获取厂商，编号-名称
     */
    List<MapModel> getDeviceMfrCode(@Param("mfrCode") String mfrCode);

    /**
     * [功能描述]设备获取状态，编号-名称：
     */
    List<MapModel> getDeviceStatusCode(@Param("statusCode") String statusCode);

    /**
     * [功能描述]：设备获取负责人，ID-名称
     */
    List<MapModel> getDeviceprinciple(@Param("devicePrinciple") int devicePrinciple);

    /**
     * [功能描述]：设备获取组织iD-name
     */
    List<MapModel> getDeviceOversightUnit(@Param("orgId") int orgId);

    /**
     * [功能描述]：获取权限监测物，编号-名称（单位）
     */
    List<MapModel> getAuthorityMonitors(
            @Param("usercode") String usercode,
            @Param("thingid") int thingid,
            @Param("thingname") String thingname,
            @Param("deviceCodeList") List<String> deviceCodeList);


    /**
     * [功能描述]：获取权限设备监测物，编号-名称（单位）
     */
    List<MapModel> getAuthorityDeviceMonitors(
            @Param("usercode") String usercode,
            @Param("devicecode") String devicecode,
            @Param("thingid") int thingid,
            @Param("thingname") String thingname);

    /**
     * [功能描述]：获取系统状态值,编号-名称
     */
    List<MapModel> getStatus(
            @Param("status") String status,
            @Param("nostatus") String nostatus,
            @Param("statusType") String statusType);
}
