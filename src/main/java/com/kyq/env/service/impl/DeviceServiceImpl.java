package com.kyq.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.kyq.env.config.Dom4jConfig;
import com.kyq.env.dao.IDeviceDao;
import com.kyq.env.model.MapDeviceModel;
import com.kyq.env.model.ThermodynamicModel;
import com.kyq.env.pojo.Device;
import com.kyq.env.service.IDeviceService;
import com.kyq.env.util.DateUtil;
import com.kyq.env.util.DefaultArgument;
import com.kyq.env.util.SortListUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [功能描述]：
 *
 * @author kyq
 */
@Service("deviceService")
@Transactional(rollbackFor = Exception.class)
public class DeviceServiceImpl implements IDeviceService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DeviceServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceServiceImpl.class);

    @Resource
    private IDeviceDao deviceDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public List<Device> getDevice(Device device) {

        return deviceDao.getDevice(device);
    }

    @Override
    public int insertDevice(Device device) throws Exception {
        int result = deviceDao.insertDevice(device);
        return result;
    }

    @Override
    public int getCount(Device device) {

        return deviceDao.getCount(device);
    }

    @Override
    public int deleteDevice(List<Integer> deviceid) throws Exception {

        return deviceDao.deleteDevice(deviceid);
    }

    @Override
    public int updateDevice(List<Device> listdevice) throws Exception {
        return deviceDao.updateDevice(listdevice);
    }

    @Override
    public boolean createStorageTable(Device device) throws Exception {
        boolean flag = false;
        try {
            deviceDao.createStorageTable(device.getDeviceCode());
            flag = true;
        } catch (Exception e) {
            flag = false;
            logger.error(LOG + "：创建表失败，原因：" + e.getMessage());
        }
        return flag;
    }

    @Override
    public void dropStorageTable(Device device) throws Exception {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        String deviceCode = device.getDeviceCode();
        if (dbName != null && !dbName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbName, deviceCode) != null
                && !deviceDao.extStorageTable(dbName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbName, deviceCode);
        }
        if (dbOldName != null && !dbOldName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbOldName, deviceCode) != null
                && !deviceDao.extStorageTable(dbOldName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbOldName, deviceCode);
        }
    }

    @Override
    public int getDeviceCodeExist(int deviceId, String deviceCode) {
        return deviceDao.getDeviceCodeExist(deviceId, deviceCode);
    }

    @Override
    public int getDeviceMnExist(int deviceId, String devicemn) {
        return deviceDao.getDeviceMnExist(deviceId, devicemn);
    }

    @Override
    public int getMapDeviceCount(List<String> listDevCode, String statusCode, String noStatusCode,
                                 Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapDeviceCount(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime,overAlarmId);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<MapDeviceModel> getMapDevice(List<String> listDevCode, String statusCode, String noStatusCode,
                                             Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId, int rowIndex, int rowCount) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapDevice(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime, overAlarmId,rowIndex, rowCount);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDeviceName(String deviceCode) {
        return deviceDao.getDeviceName(deviceCode);
    }

    @Override
    public Map<String, List<ThermodynamicModel>> getThermodynamic(List<String> listDevCode,
                                                                  String dataType, String thingCode, String beginTime, String endTime, boolean wFlag) {
        Map<String, List<ThermodynamicModel>> listMap = new TreeMap<String, List<ThermodynamicModel>>();
        try {
            if (beginTime != null && !beginTime.isEmpty() && dataType != null && !dataType.isEmpty()
                    && listDevCode != null && listDevCode.size() > 0) {
                Timestamp beginStampTime = DateUtil.StringToTimestampSecond(beginTime);
                Timestamp endStampTime = DateUtil.StringToTimestampSecond(endTime);
                while (endStampTime.compareTo(beginStampTime) >= 0) {

                    List<ThermodynamicModel> thermList = new ArrayList<ThermodynamicModel>();
                    String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                    if (!DateUtil.isRecentlyData(beginStampTime, DefaultArgument.RECENT_DAYS)) {
                        dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                    }
                    //总量
                    double allCount = 0;
                    for (String deviceCode : listDevCode) {
                        List<ThermodynamicModel> tempList = deviceDao.getThermodynamic(dbName, deviceCode, dataType, thingCode, beginStampTime);
                        if (tempList != null && tempList.size() > 0) {
                            //获取风速风向
                            for (ThermodynamicModel temp : tempList) {
                                allCount += temp.getCount();
                                if (wFlag) {
                                    //获取风速
                                    String wsValue = deviceDao.getDeviceAppointValue(dbName, temp.getDeviceCode(), dataType, DefaultArgument.PRO_2017_WS_CODE, beginStampTime);
                                    temp.setWs(wsValue);
                                    //获取风向
                                    String wdValue = deviceDao.getDeviceAppointValue(dbName, temp.getDeviceCode(), dataType, DefaultArgument.PRO_2017_WD_CODE, beginStampTime);
                                    temp.setWd(wdValue);
                                }
                                thermList.add(temp);
                            }
                        }
                    }
                    //赋值百分比
                    if (thermList != null && thermList.size() > 0 && allCount > 0) {
                        for (ThermodynamicModel temp : thermList) {
                            temp.setRatio(String.format("%.2f", (temp.getCount() / allCount) * 100));
                        }
                    }
                    //排序
                    SortListUtil<ThermodynamicModel> sortList = new SortListUtil<ThermodynamicModel>();
                    sortList.sortDouble(thermList, "getCount", "desc");

                    String date_format = "";
                    switch (dataType) {
                        case "2011"://实时数据
                        case "2051"://分钟数据
                            date_format = "yyyy-MM-dd HH:mm";
                            break;
                        case "2031"://日数据
                            date_format = "yyyy-MM-dd";
                            break;
                        case "2061"://小时数据
                            date_format = "yyyy-MM-dd HH";
                        default:
                            break;
                    }
                    String timeKey = DateUtil.TimestampToString(beginStampTime, date_format);
                    if (!listMap.containsKey(timeKey)) {
                        listMap.put(timeKey, thermList);
                    } else {
                        listMap.remove(timeKey);
                        listMap.put(timeKey, thermList);
                    }
                    beginStampTime = DateUtil.getAddTime(beginStampTime, dataType);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取热力数据失败，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    @Override
    public int updateDeviceLocation(String deviceCode, String deviceX,
                                    String deviceY) {
        int result = 0;
        if (deviceX != null && !deviceX.isEmpty() && deviceY != null && !deviceY.isEmpty()) {
            result = deviceDao.updateDeviceLocation(deviceCode, deviceX, deviceY);
        }
        return result;
    }

    @Override
    public String getAreaName(String deviceCode) {
        return deviceDao.getAreaName(deviceCode);
    }
}
