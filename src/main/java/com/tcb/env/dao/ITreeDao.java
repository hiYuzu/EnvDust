package com.tcb.env.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import com.tcb.env.model.TreeModel;

/**
 * [功能描述]：树形结构操作接口
 */
public interface ITreeDao {

    /**
     * [功能描述]：获取子类区域数据
     */
    ArrayList<TreeModel> getAreaChildrenTree(
            @Param("parentid") String parentid);

    /**
     * [功能描述]：获取子节点个数
     */
    int getSubItemCount(@Param("parentid") String parentid);

    /**
     * [功能描述]：获取用户区域权限设备数量
     */
    int getAuthorityDeviceCount(
            @Param("usercode") String usercode,
            @Param("projectid") String projectid,
            @Param("areaid") int areaid,
            @Param("devicename") String devicename,
            @Param("status") String status,
            @Param("nostatus") String nostatus);

    /**
     * [功能描述]：获取用户区域权限设备信息
     */
    ArrayList<TreeModel> getAuthorityDevice(
            @Param("usercode") String usercode,
            @Param("projectid") String projectid,
            @Param("areaid") int areaid,
            @Param("devicename") String devicename,
            @Param("status") String status,
            @Param("nostatus") String nostatus);

    /**
     * [功能描述]：获取用户区域权限设备数量(通过权限组编码)
     */
    int getAuthorityDeviceCountByAhrCode(@Param("ahrCode") String ahrCode,
                                         @Param("areaid") int areaid, @Param("devicename") String devicename);

    /**
     * [功能描述]：获取用户区域权限设备信息(通过权限组编码)
     */
    ArrayList<TreeModel> getAuthorityDeviceByAhrCode(
            @Param("ahrCode") String ahrCode, @Param("areaid") int areaid,
            @Param("devicename") String devicename);

    /**
     * [功能描述]：获取所有监测物信息(权限组对应选中状态)
     */
    ArrayList<TreeModel> getAllMonitor(@Param("ahrcode") String ahrcode);

    /**
     * [功能描述]：查询所有权限组数据
     */
    ArrayList<TreeModel> getAllAuthority(@Param("ahrcode") String ahrcode);

    /**
     * [功能描述]：查询权限在组用户数据
     */
    ArrayList<TreeModel> getUserByAhrCode(
            @Param("ahrcode") String ahrcode);

    /**
     * [功能描述]：获取所有系统模块
     */
    ArrayList<TreeModel> getModule(@Param("ahrcode") String ahrcode);

    /**
     * [功能描述]：获取模块下所有页面
     */
    ArrayList<TreeModel> getPage(@Param("ahrcode") String ahrcode,
                                 @Param("moduleid") String moduleid);

    /**
     * [功能描述]：获取页面下所有控件
     */
    ArrayList<TreeModel> getControl(@Param("ahrcode") String ahrcode,
                                    @Param("pageid") String pageid);
}
