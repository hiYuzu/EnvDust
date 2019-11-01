package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.Device;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：权限设备数据库操作接口
 *
 * @author kyq
 */
public interface IAccessDeviceDao {

    /**
     * [功能描述]：查询没在权限用户中的设备个数
     */
    int getAhrDeviceCount(@Param("device") Device device,
                          @Param("ahrCode") String ahrCode, @Param("flag") String flag);

    /**
     * [功能描述]：查询没在权限用户中的设备
     */
    List<Device> getAhrDevice(@Param("device") Device device,
                              @Param("ahrCode") String ahrCode, @Param("flag") String flag);

    /**
     * [功能描述]：添加权限组设备
     */
    int insertAccessDevice(@Param("ahrCode") String ahrCode,
                           @Param("listDevCode") List<String> listDevCode,
                           @Param("optUser") int optUser);

    /**
     * [功能描述]：删除权限组设备
     */
    int deleteAccessDevice(@Param("listAhrCode") List<String> listAhrCode);

    /**
     * [功能描述]：删除权限组设备
     */
    int deleteAccessDeviceSingle(@Param("ahrCode") String ahrCode,
                                 @Param("listDevCode") List<String> listDevCode);

}
