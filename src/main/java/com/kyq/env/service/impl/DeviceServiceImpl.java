package com.kyq.env.service.impl;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import com.kyq.env.config.Dom4jConfig;
import com.kyq.env.dao.IDeviceDao;
import com.kyq.env.model.MapDeviceModel;
import com.kyq.env.pojo.Device;
import com.kyq.env.service.IDeviceService;
import com.kyq.env.util.DateUtil;
import com.kyq.env.util.EnumUtil;
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
                return deviceDao.getMapDeviceCount(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime, overAlarmId);
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
                return deviceDao.getMapDevice(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime, overAlarmId, rowIndex, rowCount);
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

    @Override
    public List<String> getDeviceCodes() {
        return deviceDao.getDeviceCodes();
    }

    @Override
    public void insertRtdDeviceData(List<String> deviceCodes) {
        for (String deviceCode : deviceCodes) {
            deviceDao.insertRtdDeviceData(deviceCode, EnumUtil.Monitor.a01011.toString(), getRandom(), null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()));
            deviceDao.insertRtdDeviceData(deviceCode, EnumUtil.Monitor.a01012.toString(), getRandom(), null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()));
            deviceDao.insertRtdDeviceData(deviceCode, EnumUtil.Monitor.a01013.toString(), getRandom(), null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()));
            deviceDao.insertRtdDeviceData(deviceCode, EnumUtil.Monitor.a34002.toString(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()));
            deviceDao.insertRtdDeviceData(deviceCode, EnumUtil.Monitor.a34004.toString(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()));
        }
        logger.info(LOG + " : 实时数据添加成功！");
    }

    @Override
    public void insertMinuteDeviceData(List<String> deviceCodes) {
        for (String deviceCode : deviceCodes) {
            deviceDao.insertMinuteDeviceData(deviceCode, EnumUtil.Monitor.a01011.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 600000));
            deviceDao.insertMinuteDeviceData(deviceCode, EnumUtil.Monitor.a01012.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 600000));
            deviceDao.insertMinuteDeviceData(deviceCode, EnumUtil.Monitor.a01013.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 600000));
            deviceDao.insertMinuteDeviceData(deviceCode, EnumUtil.Monitor.a34002.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 600000));
            deviceDao.insertMinuteDeviceData(deviceCode, EnumUtil.Monitor.a34004.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 600000));
        }
        logger.info(LOG + " : 分钟数据添加成功！");
    }

    @Override
    public void insertHourDeviceData(List<String> deviceCodes) {
        for (String deviceCode : deviceCodes) {
            deviceDao.insertHourDeviceData(deviceCode, EnumUtil.Monitor.a01011.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 3600000));
            deviceDao.insertHourDeviceData(deviceCode, EnumUtil.Monitor.a01012.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 3600000));
            deviceDao.insertHourDeviceData(deviceCode, EnumUtil.Monitor.a01013.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 3600000));
            deviceDao.insertHourDeviceData(deviceCode, EnumUtil.Monitor.a34002.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 3600000));
            deviceDao.insertHourDeviceData(deviceCode, EnumUtil.Monitor.a34004.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 3600000));
        }
        logger.info(LOG + " : 小时数据添加成功！");
    }

    @Override
    public void insertDayDeviceData(List<String> deviceCodes) {
        for (String deviceCode : deviceCodes) {
            deviceDao.insertDayDeviceData(deviceCode, EnumUtil.Monitor.a01011.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 86400000));
            deviceDao.insertDayDeviceData(deviceCode, EnumUtil.Monitor.a01012.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 86400000));
            deviceDao.insertDayDeviceData(deviceCode, EnumUtil.Monitor.a01013.toString(), getRandom(), getMaxRandom(), getMinRandom(), null, null, null, DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 86400000));
            deviceDao.insertDayDeviceData(deviceCode, EnumUtil.Monitor.a34002.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 86400000));
            deviceDao.insertDayDeviceData(deviceCode, EnumUtil.Monitor.a34004.toString(), getRandom(), getMaxRandom(), getMinRandom(), getRandom(), getMaxRandom(), getMinRandom(), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis()), DateUtil.getSystemTime(System.currentTimeMillis() + 86400000));
        }
        logger.info(LOG + " : 每日数据添加成功！");
    }

    private double getRandom() {
        String s = String.format("%.2f", Math.random() * 1049 + 1);
        return Double.valueOf(s);
    }

    private double getMinRandom() {
        String s = String.format("%.2f", Math.random() * 499 + 1);
        return Double.valueOf(s);
    }

    private double getMaxRandom() {
        String s = String.format("%.2f", Math.random() * 499 + 1001);
        return Double.valueOf(s);
    }
}
