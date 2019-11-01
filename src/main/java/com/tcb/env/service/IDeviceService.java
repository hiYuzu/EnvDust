package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.tcb.env.model.MapDeviceModel;
import com.tcb.env.model.ThermodynamicModel;
import com.tcb.env.pojo.Device;

/**
 * [功能描述]：设备服务接口
 *
 * @author kyq
 */
public interface IDeviceService {
    /**
     * [功能描述]：查询设备
     */
    List<Device> getDevice(Device device);

    /**
     * [功能描述]：新增设备
     */
    int insertDevice(Device device) throws Exception;

    /**
     * [功能描述]：查询个数
     */
    int getCount(Device device);

    /**
     * [功能描述]：删除设备
     */
    int deleteDevice(List<Integer> deviceid) throws Exception;

    /**
     * [功能描述]：更新设备
     */
    int updateDevice(List<Device> listdevice) throws Exception;

    /**
     * [功能描述]：查询设备编码是否存在
     */
    int getDeviceCodeExist(int deviceId, String deviceCode);

    /**
     * [功能描述]：查询设备MN码是否存在
     */
    int getDeviceMnExist(int deviceId, String devicemn);

    /**
     * [功能描述]：创建监测物存储表
     */
    boolean createStorageTable(Device device) throws Exception;

    /**
     * [功能描述]：删除监测物存储表
     */
    void dropStorageTable(Device device) throws Exception;

    /**
     * [功能描述]：查询地图数据信息个数(非statusCode)
     */
    int getMapDeviceCount(List<String> listDevCode, String statusCode, String noStatusCode, Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId);

    /**
     * [功能描述]：查询地图数据信息(非statusCode)
     */
    List<MapDeviceModel> getMapDevice(List<String> listDevCode, String statusCode, String noStatusCode,
                                      Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId, int rowIndex, int rowCount);

    /**
     * [功能描述]：通过设备编码获取名称
     */
    String getDeviceName(String deviceCode);

    /**
     * [功能描述]：获取设备热力图信息
     */
    Map<String, List<ThermodynamicModel>> getThermodynamic(List<String> listDevCode, String dataType,
                                                           String thingCode, String beginTime, String endTime, boolean wFlag);

    /**
     * [功能描述]：更新设备地图坐标
     */
    int updateDeviceLocation(String deviceCode, String deviceX, String deviceY);

    /**
     * [功能描述]：通过设备编码获取所属区域名称
     */
    String getAreaName(String deviceCode);

}
