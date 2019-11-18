package com.kyq.env.dao;

import java.sql.Timestamp;
import java.util.List;

import com.kyq.env.model.MapDeviceModel;
import com.kyq.env.model.ThermodynamicModel;
import com.kyq.env.pojo.Device;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：设备dao
 *
 * @author kyq
 */
public interface IDeviceDao {
    /**
     * [功能描述]：查询设备
     */
    List<Device> getDevice(@Param("device") Device device);

    /**
     * [功能描述]：新增设备
     */
    int insertDevice(@Param("device") Device device);

    /**
     * [功能描述]：获得个数
     */
    int getCount(@Param("device") Device device);

    /**
     * [功能描述]：删除设备
     */
    int deleteDevice(@Param("deviceid") List<Integer> deviceid);

    /**
     * [功能描述]：更新设备
     */
    int updateDevice(@Param("list") List<Device> list);

    /**
     * [功能描述]：查询是设备编号否存在
     */
    int getDeviceCodeExist(@Param("deviceid") int userid,
                           @Param("devicecode") String devicecode);

    /**
     * [功能描述]：查询设备MN号是都存在
     */
    int getDeviceMnExist(@Param("deviceid") int deviceid,
                         @Param("devicemn") String devicemn);

    /**
     * [功能描述]：创建监测物存储表
     */
    int createStorageTable(@Param("tableName") String tableName);

    /**
     * [功能描述]：判断是否存在表
     */
    String extStorageTable(
            @Param("dbName") String dbName,
            @Param("tableName") String tableName);

    /**
     * [功能描述]：删除监测物存储表
     */
    int dropStorageTable(
            @Param("dbName") String dbName,
            @Param("tableName") String tableName);

    /**
     * [功能描述]：查询地图数据信息个数(非statusCode)
     */
    int getMapDeviceCount(
            @Param("listDevCode") List<String> listDevCode,
            @Param("statusCode") String statusCode,
            @Param("noStatusCode") String noStatusCode,
            @Param("beginAlarmTime") Timestamp beginAlarmTime,
            @Param("endAlarmTime") Timestamp endAlarmTime,
            @Param("overAlarmId") String overAlarmId);

    /**
     * [功能描述]：查询地图数据信息(非statusCode)
     */
    List<MapDeviceModel> getMapDevice(
            @Param("listDevCode") List<String> listDevCode,
            @Param("statusCode") String statusCode,
            @Param("noStatusCode") String noStatusCode,
            @Param("beginAlarmTime") Timestamp beginAlarmTime,
            @Param("endAlarmTime") Timestamp endAlarmTime,
            @Param("overAlarmId") String overAlarmId,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * [功能描述]：通过设备编码获取名称
     */
    String getDeviceName(@Param("deviceCode") String deviceCode);

    /**
     * [功能描述]：通过设备编码获取所属区域名称
     */
    String getAreaName(@Param("deviceCode") String deviceCode);

    /**
     * [功能描述]：获取设备热力图信息（分表数据）
     */
    List<ThermodynamicModel> getThermodynamic(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * [功能描述]：更新设备状态
     */
    int updateDeviceStatus(
            @Param("deviceCode") String deviceCode,
            @Param("statusCode") String statusCode);

    /**
     * [功能描述]：更新设备地图坐标
     */
    int updateDeviceLocation(
            @Param("deviceCode") String deviceCode,
            @Param("deviceX") String deviceX,
            @Param("deviceY") String deviceY);

    /**
     * [功能描述]：获取指定时间物质的数值
     */
    String getDeviceAppointValue(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    List<String> getDeviceCodes();

    void insertRtdDeviceData(@Param("deviceCode") String deviceCode,
                             @Param("thingCode") String thingCode,
                             @Param("thingRtd") double thingRtd,
                             @Param("thingZsRtd") Double thingZsRtd,
                             @Param("updateTime") String updateTime,
                             @Param("rtdTime") String rtdTime);

    void insertMinuteDeviceData(@Param("deviceCode") String deviceCode,
                                @Param("thingCode") String thingCode,
                                @Param("thingAvg") double thingAvg,
                                @Param("thingMax") double thingMax,
                                @Param("thingMin") double thingMin,
                                @Param("thingZsAvg") Double thingZsAvg,
                                @Param("thingZsMax") Double thingZsMax,
                                @Param("thingZsMin") Double thingZsMin,
                                @Param("thingCou") double thingCou,
                                @Param("thingZsCou") double thingZsCou,
                                @Param("updateTime") String updateTime,
                                @Param("beginTime") String beginTime,
                                @Param("endTime") String endTime);

    void insertHourDeviceData(@Param("deviceCode") String deviceCode,
                              @Param("thingCode") String thingCode,
                              @Param("thingAvg") double thingAvg,
                              @Param("thingMax") double thingMax,
                              @Param("thingMin") double thingMin,
                              @Param("thingZsAvg") Double thingZsAvg,
                              @Param("thingZsMax") Double thingZsMax,
                              @Param("thingZsMin") Double thingZsMin,
                              @Param("thingCou") double thingCou,
                              @Param("thingZsCou") double thingZsCou,
                              @Param("updateTime") String updateTime,
                              @Param("beginTime") String beginTime,
                              @Param("endTime") String endTime);

    void insertDayDeviceData(@Param("deviceCode") String deviceCode,
                              @Param("thingCode") String thingCode,
                              @Param("thingAvg") double thingAvg,
                              @Param("thingMax") double thingMax,
                              @Param("thingMin") double thingMin,
                              @Param("thingZsAvg") Double thingZsAvg,
                              @Param("thingZsMax") Double thingZsMax,
                              @Param("thingZsMin") Double thingZsMin,
                              @Param("updateTime") String updateTime,
                              @Param("beginTime") String beginTime,
                              @Param("endTime") String endTime);
}
