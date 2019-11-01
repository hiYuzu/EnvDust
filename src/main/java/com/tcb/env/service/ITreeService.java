package com.tcb.env.service;

import java.util.ArrayList;
import java.util.List;

import com.tcb.env.model.TreeModel;

/**
 * [功能描述]：Tree操作服务类接口
 */
public interface ITreeService {

    /**
     * [功能描述]：获取子类区域数据
     */
    ArrayList<TreeModel> getAreaChildrenTree(String parentid);

    /**
     * [功能描述]：获取子节点个数
     */
    int getSubItemCount(String parentid);

    /**
     * [功能描述]：获取用户区域权限设备数量
     */
    int getAuthorityDeviceCount(String usercode, String projectid, int areaid,
                                String devicename, String status, String nostatus);

    /**
     * [功能描述]：获取用户区域权限设备信息
     */
    ArrayList<TreeModel> getAuthorityDevices(String usercode, String projectid,
                                             int areaid, String devicename, String status, String nostatus);

    /**
     * [功能描述]：获取用户区域权限设备信息数量(通过权限组编码)
     */
    int getAuthorityDeviceCountByAhrCode(String ahrCode, int areaid,
                                         String devicename);

    /**
     * [功能描述]：获取用户区域权限设备信息(通过权限组编码)
     */
    ArrayList<TreeModel> getAuthorityDeviceByAhrCode(String ahrCode,
                                                     int areaid, String devicename);

    /**
     * [功能描述]：获取所有监测物信息(用户对应选中状态)
     */
    ArrayList<TreeModel> getAllMonitor(String ahrCode);

    /**
     * [功能描述]：查询所有权限组数据
     */
    ArrayList<TreeModel> getAllAuthority(String ahrcode);

    /**
     * [功能描述]：查询权限在组用户数据
     */
    ArrayList<TreeModel> getUserByAhrCode(String ahrcode);

    /**
     * [功能描述]：获取所有系统模块
     */
    ArrayList<TreeModel> getModule(String ahrcode);

    /**
     * [功能描述]：获取模块下所有页面
     */
    ArrayList<TreeModel> getPage(String ahrcode, String moduleid);

    /**
     * [功能描述]：获取页面下所有控件
     */
    ArrayList<TreeModel> getControl(String ahrcode, String pageid);

    /**
     * [功能描述]：获取权限底层结点区域
     */
    List<Integer> getAuthorityBottomArea(String projectId, List<Integer> listAreaId,
                                         int parentAreaId, String usercode);
}
