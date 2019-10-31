package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.tcb.env.pojo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.service.IDeviceAlarmSetService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IMonitorService;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:37:09
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DeviceAlarmSetController")
public class DeviceAlarmSetController {
    /**
     * 声明报警门限服务类
     */
    @Resource
    private IDeviceAlarmSetService deviceAlarmSetService;
    /**
     * 声明Monitor服务
     */
    @Resource
    private IMonitorService monitorService;

    /**
     * 声明设备服务
     */
    @Resource
    private IDeviceService deviceService;
    /**
     * <p>
     * [功能描述]：获取设备监测物报警门限(thingname)
     * </p>
     *
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年6月2日下午3:40:11
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmLineThgName", method = {RequestMethod.POST})
    public @ResponseBody
    HashMap<String, HashMap<String, List<HashMap<String, Object>>>> getDeviceAlarmLineThgName(
            @RequestParam(value = "listdev[]") List<String> listDeviceCode,
            @RequestParam(value = "listthg[]") List<String> listThingCode) {
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> hashMap = new HashMap<String, HashMap<String, List<HashMap<String, Object>>>>();
        if (listDeviceCode != null && listDeviceCode.size() > 0
                && listThingCode != null && listThingCode.size() > 0
                && !listThingCode.get(0).equals(DefaultArgument.INT_DEFAULT)) {
            for (String deviceCode : listDeviceCode) {
                HashMap<String, List<HashMap<String, Object>>> hashMapThing = new HashMap<String, List<HashMap<String, Object>>>();
                List<DeviceAlarmSet> deviceAlarmSetList = deviceAlarmSetService.getDeviceAlarmSetSingle(deviceCode, listThingCode);
                for (DeviceAlarmSet deviceAlarmSet : deviceAlarmSetList) {
                    if (deviceAlarmSet != null) {
                        HashMap<String, Object> hashMapValue = new HashMap<String, Object>();
                        String thingCode = deviceAlarmSet.getMonitor().getThingCode();
                        double maxValue = deviceAlarmSet.getMaxValue();
                        double minValue = deviceAlarmSet.getMinValue();
                        int levelNo = deviceAlarmSet.getLevelNo();
                        if (!hashMapValue.containsKey("max")) {
                            hashMapValue.put("max", maxValue);
                        }
                        if (!hashMapValue.containsKey("min")) {
                            hashMapValue.put("min", minValue);
                        }
                        if (!hashMapValue.containsKey("lev")) {
                            hashMapValue.put("lev", levelNo);
                        }
                        String thingName = monitorService.getMonitorName(thingCode);
                        if (!hashMapThing.containsKey(thingName)) {
                            List<HashMap<String, Object>> hashMapList = new ArrayList<HashMap<String, Object>>();
                            hashMapList.add(hashMapValue);
                            hashMapThing.put(thingName, hashMapList);
                        } else {
                            hashMapThing.get(thingName).add(hashMapValue);
                        }
                    }
                }
                if (!hashMap.containsKey(deviceCode)) {
                    hashMap.put(deviceCode, hashMapThing);
                }
            }
        }
        return hashMap;
    }

    /**
     * <p>
     * [功能描述]：获取设备监测物报警门限(devicename-areaname)
     * </p>
     *
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年6月2日下午3:40:11
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmLineDevName", method = {RequestMethod.POST})
    public @ResponseBody
    HashMap<String, HashMap<String, List<HashMap<String, Object>>>> getDeviceAlarmLineDevName(
            @RequestParam(value = "listdev[]") List<String> listDeviceCode,
            @RequestParam(value = "listthg[]") List<String> listThingCode) {
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> hashMap = new HashMap<String, HashMap<String, List<HashMap<String, Object>>>>();
        if (listDeviceCode != null && listDeviceCode.size() > 0
                && listThingCode != null && listThingCode.size() > 0
                && !listThingCode.get(0).equals(DefaultArgument.INT_DEFAULT)) {

            for (String deviceCode : listDeviceCode) {
                String areaName = deviceService.getAreaName(deviceCode);
                String deviceName = deviceService.getDeviceName(deviceCode);
                HashMap<String, List<HashMap<String, Object>>> hashMapThing = new HashMap<String, List<HashMap<String, Object>>>();
                List<DeviceAlarmSet> deviceAlarmSetList = deviceAlarmSetService.getDeviceAlarmSetSingle(deviceCode, listThingCode);
                for (DeviceAlarmSet deviceAlarmSet : deviceAlarmSetList) {
                    HashMap<String, Object> hashMapValue = new HashMap<String, Object>();
                    if (deviceAlarmSet != null) {
                        String thingCode = deviceAlarmSet.getMonitor().getThingCode();
                        double maxValue = deviceAlarmSet.getMaxValue();
                        double minValue = deviceAlarmSet.getMinValue();
                        int levelNo = deviceAlarmSet.getLevelNo();
                        if (!hashMapValue.containsKey("max")) {
                            hashMapValue.put("max", maxValue);
                        }
                        if (!hashMapValue.containsKey("min")) {
                            hashMapValue.put("min", minValue);
                        }
                        if (!hashMapValue.containsKey("lev")) {
                            hashMapValue.put("lev", levelNo);
                        }
                        if (!hashMapThing.containsKey(thingCode)) {
                            List<HashMap<String, Object>> hashMapList = new ArrayList<HashMap<String, Object>>();
                            hashMapList.add(hashMapValue);
                            hashMapThing.put(thingCode, hashMapList);
                        } else {
                            hashMapThing.get(thingCode).add(hashMapValue);
                        }
                    }
                }
                String hashKey = deviceName;
                if (areaName != null && !areaName.isEmpty()) {
                    hashKey += "-" + areaName;
                }
                if (!hashMap.containsKey(hashKey)) {
                    hashMap.put(hashKey, hashMapThing);
                }
            }
        }
        return hashMap;
    }

}
