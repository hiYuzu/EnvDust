package com.tcb.env.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.tcb.env.model.MapModel;
import com.tcb.env.model.MonitorStorageModel;
import com.tcb.env.model.NetMonitorDataModel;
import com.tcb.env.model.NetMonitorTimeModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.model.ResultAjaxPushModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.NetStatusCount;
import com.tcb.env.service.IAreaService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.IMonitorStorageService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IWeatherService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.EnumUtil.TimeFreque;
import com.tcb.env.util.SessionManager;

/**
 * [功能描述]：数据查询控制器
 *
 * @author kyq
 */
@Controller
@RequestMapping("/MonitorStorageController")
public class MonitorStorageController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "MonitorStorageController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(MonitorStorageController.class);
    /**
     * 声明监测物查询服务类
     */
    @Resource
    private IMonitorStorageService monitorStorageService;
    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;
    /**
     * 声明键值查询服务类
     */
    @Resource
    private IMapService mapService;
    /**
     * 声明设备服务类
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * [功能描述]：获取单站点多参数历史数据(列表)
     */
    @RequestMapping(value = "/getPerStatisticsData", method = {RequestMethod.POST})
    public @ResponseBody
    String getSingleDeviceStatisticsData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, @RequestParam(value = "list[]") List<String> list,
            String starttime, String endtime, String freque) {
        TreeMap<String, HashMap<String, Double>> treeMap = new TreeMap<String, HashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return "[]";
        }
        try {
            List<String> listDevicecode = new ArrayList<String>();
            listDevicecode.add(devicecode);
            List<String> listThingcode = list;
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            if (mapMonitor != null && mapMonitor.size() > 0) {
                for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                        .entrySet()) {
                    if (tempListMonitor.getKey() > 0) {
                        if (treeMap != null) {
                            for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                                String frequetime = tempMonitor.getFrequeTime();
                                String thingname = tempMonitor.getThingName();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(thingname)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(thingname, monitorvalue);
                                    }
                                } else {
                                    HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                    hashMaplist.put(thingname, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }
                                //折算值
                                if (zsFlag) {
                                    String thingnamezs = thingname + "-zs";
                                    Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(thingnamezs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(thingnamezs, zsmonitorvalue);
                                        }
                                    } else {
                                        HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                        hashMaplist.put(thingnamezs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return ConvertToJson(treeMap);
    }

    /**
     * [功能描述]：获取单站点多参数历史数据(图形)
     */
    @RequestMapping(value = "/getSingleDeviceStatisticsChartData", method = {RequestMethod.POST})
    public @ResponseBody
    TreeMap<String, List<String>> getSingleDeviceStatisticsChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, @RequestParam(value = "list[]") List<String> list,
            String starttime, String endtime, String freque) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        try {
            List<String> listDevicecode = new ArrayList<String>();
            listDevicecode.add(devicecode);
            List<String> listThingcode = list;
            List<String> listName = monitorStorageService.getMonNamebyCode(listThingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            // 排序处理
            TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
            for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                    .entrySet()) {
                if (tempListMonitor.getKey() > 0) {
                    for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor.entrySet()) {
                String frequetime = tempTreeMonitor.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测物
                for (String temp : listName) {
                    String thingname;
                    String monitorvalue = null;
                    String zsmonitorvalue = null;
                    for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                        thingname = tempMonitor.getThingName();
                        if (temp.equals(thingname)) {
                            monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                            zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                            break;
                        }
                    }
                    if (treeMap.containsKey(temp)) {
                        treeMap.get(temp).add(monitorvalue);
                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(temp, listThing);
                    }
                    //折算值
                    if (zsFlag) {
                        String tempzs = temp + "-折算";
                        if (treeMap.containsKey(tempzs)) {
                            treeMap.get(tempzs).add(zsmonitorvalue);
                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(zsmonitorvalue);
                            treeMap.put(tempzs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * [功能描述]：获取多站点单参数历史数据(列表)
     */
    @RequestMapping(value = "/getSingleThingStatisticsData", method = {RequestMethod.POST})
    public @ResponseBody
    String getSingleThingStatisticsData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list,
            String thingcode, String starttime, String endtime, String freque) {
        TreeMap<String, HashMap<String, Double>> treeMap = new TreeMap<String, HashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        if (thingcode.equals(DefaultArgument.INT_DEFAULT)) {
            return "[]";
        }
        try {
            List<String> listDevicecode = list;
            List<String> listThingcode = new ArrayList<String>();
            listThingcode.add(thingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            if (mapMonitor != null && mapMonitor.size() > 0) {
                for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                        .entrySet()) {
                    if (tempListMonitor.getKey() > 0) {
                        if (treeMap != null) {
                            for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                                String frequetime = tempMonitor.getFrequeTime();
//								String devicename = tempMonitor.getDeviceName();
                                String deviceCode = tempMonitor.getDeviceCode();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(deviceCode)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(deviceCode, monitorvalue);
                                    }
                                } else {
                                    HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                    hashMaplist.put(deviceCode, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }

                                //折算值
                                if (zsFlag) {
                                    String deviceCodeZs = deviceCode + "-zs";
                                    Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(deviceCodeZs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(deviceCodeZs, zsmonitorvalue);
                                        }
                                    } else {
                                        HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                        hashMaplist.put(deviceCodeZs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return ConvertToJson(treeMap);
    }

    /**
     * [功能描述]：获取多站点单参数历史数据(图形)
     */
    @RequestMapping(value = "/getSingleThingStatisticsChartData", method = {RequestMethod.POST})
    public @ResponseBody
    TreeMap<String, List<String>> getSingleThingStatisticsChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list,
            String thingcode, String starttime, String endtime, String freque) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (thingcode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        try {
            List<String> listDevicecode = list;
            List<String> listThingcode = new ArrayList<String>();
            listThingcode.add(thingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            // 排序处理
            TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
            for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                    .entrySet()) {
                if (tempListMonitor.getKey() > 0) {
                    for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor
                    .entrySet()) {
                String frequetime = tempTreeMonitor.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测设备
                for (String temp : listDevicecode) {
                    String devicename = null;
                    String devicenamezs = null;
                    String deviceCode;
                    String monitorvalue = null;
                    String zsmonitorvalue = null;
                    for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                        deviceCode = tempMonitor.getDeviceCode();
                        devicename = deviceService.getDeviceName(temp);
                        String areaName = deviceService.getAreaName(temp);
                        if (areaName != null && !areaName.isEmpty()) {
                            devicename = devicename + "-" + areaName;
                            devicenamezs = devicename + "-" + areaName + "-折算";
                        }
                        if (temp.equals(deviceCode)) {
                            monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                            zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                            break;
                        }
                    }
                    if (treeMap.containsKey(devicename)) {
                        treeMap.get(devicename).add(monitorvalue);

                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(devicename, listThing);
                    }
                    //折算值
                    if (zsFlag) {
                        if (treeMap.containsKey(devicenamezs)) {
                            treeMap.get(devicenamezs).add(zsmonitorvalue);

                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(zsmonitorvalue);
                            treeMap.put(devicenamezs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * [功能描述]：判断查询的频率周期
     */
    private Map<Integer, List<MonitorStorageModel>> getStatisticsDataByFreque(
            List<String> listdevicecode, List<String> listthingcode,
            String starttime, String endtime, String freque) {
        Map<Integer, List<MonitorStorageModel>> mapMonitor = new HashMap<Integer, List<MonitorStorageModel>>();
        TimeFreque timeFreque = TimeFreque.valueOf(freque.toUpperCase(Locale.ENGLISH));
        switch (timeFreque) {
            case PERMINUTE: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_MINUTE) {
                    mapMonitor = monitorStorageService.getPerMinuteStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerMinuteMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERHOUR: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerHourStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerHourMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }

            }
            break;
            case PERDAY: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_DAY) {
                    mapMonitor = monitorStorageService.getPerDayStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerDayMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERMONTH: {
                starttime = starttime + "-01";
                endtime = endtime + "-01";
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerMonthStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerMonthMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERQUARTER: {
                {
                    switch (starttime.substring(starttime.length() - 2,
                            starttime.length())) {
                        case "01": {
                            starttime = starttime.substring(0, 4) + "-01" + "-01";
                        }
                        break;
                        case "02": {
                            starttime = starttime.substring(0, 4) + "-04" + "-01";
                        }
                        break;
                        case "03": {
                            starttime = starttime.substring(0, 4) + "-07" + "-01";
                        }
                        break;
                        case "04": {
                            starttime = starttime.substring(0, 4) + "-10" + "-01";
                        }
                        break;
                        default:
                            break;
                    }
                    switch (endtime.substring(endtime.length() - 2,
                            endtime.length())) {
                        case "01": {
                            endtime = endtime.substring(0, 4) + "-04" + "-01";
                        }
                        break;
                        case "02": {
                            endtime = endtime.substring(0, 4) + "-07" + "-01";
                        }
                        break;
                        case "03": {
                            endtime = endtime.substring(0, 4) + "-10" + "-01";
                        }
                        break;
                        case "04": {
                            endtime = (Integer.valueOf(endtime.substring(0, 4)) + 1)
                                    + "-01" + "-01";
                        }
                        break;
                        default:
                            break;
                    }
                }
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerQuarterStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerQuarterMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            default:
                break;
        }
        return mapMonitor;
    }

    /**
     * [功能描述]：查询实时监测物数据(图形)
     */
    @RequestMapping(value = "/getTimelyMonitorChartData", method = {RequestMethod.POST})
    @ResponseBody
    public TreeMap<String, List<String>> getTimelyMonitorChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, boolean isrepeat, HttpSession httpsession) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        Timestamp nowtime = DateUtil.GetSystemDateTime(0);
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return treeMap;
            }
            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<MapModel> listMap = getAthorityMonitors(listDevicecode, httpsession);
                if (listMap != null && listMap.size() > 0) {
                    List<String> listThingcode = new ArrayList<String>();
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                    List<String> listName = monitorStorageService
                            .getMonNamebyCode(listThingcode);
                    List<MonitorStorageModel> mapMonitor = monitorStorageService.getTimelyMonitorData(
                            listDevicecode,
                            listThingcode,
                            nowtime,
                            DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                    // 排序处理
                    TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
                    for (MonitorStorageModel tempMonitor : mapMonitor) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(
                                    tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                    for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor.entrySet()) {
                        String frequetime = tempTreeMonitor.getKey();
                        // 时间
                        if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                            treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                        } else {
                            List<String> listTime = new ArrayList<String>();
                            listTime.add(frequetime);
                            treeMap.put(DefaultArgument.STA_TIME, listTime);
                        }
                        // 监测物
                        for (String temp : listName) {
                            String thingname;
                            String monitorvalue = null;
                            String zsmonitorvalue = null;
                            for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                                thingname = tempMonitor.getThingName();
                                if (temp.equals(thingname)) {
                                    monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                                    zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                                    break;
                                }
                            }
                            if (treeMap.containsKey(temp)) {
                                treeMap.get(temp).add(monitorvalue);

                            } else {
                                List<String> listThing = new ArrayList<String>();
                                listThing.add(monitorvalue);
                                treeMap.put(temp, listThing);
                            }
                            //折算值
                            String tempzs = temp + "-折算";
                            if (zsFlag) {
                                if (treeMap.containsKey(tempzs)) {
                                    treeMap.get(tempzs).add(zsmonitorvalue);

                                } else {
                                    List<String> listThing = new ArrayList<String>();
                                    listThing.add(zsmonitorvalue);
                                    treeMap.put(tempzs, listThing);
                                }
                            }

                        }
                    }
                }
            } else {
                return treeMap;
            }

        } catch (Exception e) {
            logger.error(LOG + ":查询实时数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * [功能描述]：查询实时监测物数据(列表)
     */
    @RequestMapping(value = "/getTimelyMonitorData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultAjaxPushModel getTimelyMonitorData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, boolean isrepeat, HttpSession httpsession) {
        ResultAjaxPushModel resultTimelyModel = new ResultAjaxPushModel();
        TreeMap<String, LinkedHashMap<String, Double>> treeMap = new TreeMap<String, LinkedHashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        resultTimelyModel.setSelect(devicecode);
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return resultTimelyModel;
        }
        UserModel loginuser = new UserModel();
        Timestamp nowtime = DateUtil.GetSystemDateTime(0);
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultTimelyModel;
            }
            loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            nowtime = DateUtil.GetSystemDateTime(0);
            if (loginuser != null) {
                if (!StringUtils.isEmpty(devicecode)) {
                    List<String> listDevicecode = new ArrayList<String>();
                    listDevicecode.add(devicecode);
                    List<MapModel> listMap = getAthorityMonitors(listDevicecode, httpsession);
                    if (listMap != null && listMap.size() > 0) {
                        List<String> listThingcode = new ArrayList<String>();
                        for (MapModel mapModel : listMap) {
                            listThingcode.add(mapModel.getCode());
                        }
                        // 查询24小时内数据
                        List<MonitorStorageModel> mapMonitor = monitorStorageService.getTimelyMonitorData(
                                listDevicecode,
                                listThingcode,
                                nowtime,
                                DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                        if (mapMonitor != null && mapMonitor.size() > 0) {
                            for (MonitorStorageModel tempMonitor : mapMonitor) {
                                String frequetime = tempMonitor.getFrequeTime();
                                String thingname = tempMonitor.getThingName();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(thingname)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(thingname, monitorvalue);
                                    }
                                } else {
                                    LinkedHashMap<String, Double> hashMaplist = new LinkedHashMap<String, Double>();
                                    hashMaplist.put(thingname, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }
                                //添加折算
                                String thingnamezs = thingname + "-zs";
                                Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                if (zsFlag) {
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(thingnamezs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(thingnamezs, zsmonitorvalue);
                                        }
                                    } else {
                                        LinkedHashMap<String, Double> hashMaplist = new LinkedHashMap<String, Double>();
                                        hashMaplist.put(thingnamezs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询实时数据错误，错误信息为：" + e.getMessage());
        } finally {
            loginuser.setSelectTime(nowtime);// 赋值本次查询时间，以便于下次查询
        }
        // 追加查询code
        resultTimelyModel.setResult(treeMap);
        return resultTimelyModel;
    }

    /**
     * [功能描述]：获取权限监测物
     */
    @RequestMapping(value = "/getAthorityMonitors", method = {RequestMethod.POST})
    @ResponseBody
    public List<MapModel> getAthorityMonitors(
            @RequestParam(value = "deviceCodeList[]", required = false) List<String> deviceCodeList,
            HttpSession httpsession) {
        List<MapModel> listMap = new ArrayList<MapModel>();
        try {
            String usercode = null;
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return listMap;
            }
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            listMap = mapService.getAuthorityMonitors(usercode, DefaultArgument.INT_DEFAULT, null, deviceCodeList);

        } catch (Exception e) {
            logger.error(LOG + ":查询权限监测物数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    /**
     * [功能描述]：获取权限设备监测物
     */
    @RequestMapping(value = "/getAthorityDeviceMonitors", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getAthorityDeviceMonitors(String deviceCode,
                                             HttpSession httpsession) {
        List<MapModel> listMap = new ArrayList<MapModel>();
        try {
            String usercode = null;
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                return listMap;
            }
            UserModel loginuser = (UserModel) httpsession
                    .getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            listMap = mapService.getAuthorityDeviceMonitors(usercode, deviceCode,
                    DefaultArgument.INT_DEFAULT, null);

        } catch (Exception e) {
            logger.error(LOG + ":查询权限设备监测物数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    /**
     * [功能描述]：获取权限设备数据
     */
    @RequestMapping(value = "/getAuthorityDevices", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getAuthorityDevices(
            @RequestParam(value = "projectId", required = false) String projectId,
            int areaid, String statusCode, HttpSession httpsession) {
        ArrayList<MapModel> listMapModel = new ArrayList<MapModel>();
        // 获取用户code
        String usercode = null;
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return listMapModel;
        }
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (usercode != null && !usercode.isEmpty()) {
            ArrayList<TreeModel> al = treeService.getAuthorityDevices(usercode, projectId, areaid, null, statusCode, null);
            for (TreeModel treeModel : al) {
                MapModel mapModel = new MapModel();
                mapModel.setCode(treeModel.getId());
                mapModel.setName(treeModel.getText());
                listMapModel.add(mapModel);
            }
        }

        return listMapModel;
    }

    /**
     * [功能描述]：将treeMap结果集转换成json字符串
     */
    private String ConvertToJson(TreeMap<String, HashMap<String, Double>> treeMap) {
        String reJson = "[";
        try {
            for (Entry<String, HashMap<String, Double>> tempTreeMap : treeMap
                    .entrySet()) {
                reJson += "{\"time\":";
                reJson += "\"" + tempTreeMap.getKey() + "\"";
                for (Entry<String, Double> tempMap : tempTreeMap.getValue()
                        .entrySet()) {
                    reJson += ",\"" + tempMap.getKey() + "\":";
                    reJson += String.valueOf(tempMap.getValue());
                }
                reJson += "},";
            }
            if (reJson.lastIndexOf(",") >= 0) {
                reJson = reJson.substring(0, reJson.lastIndexOf(","));
            }
        } catch (Exception e) {
            logger.error(LOG + "：转换成json字符串失败，信息：" + e.getMessage());
        }
        reJson += "]";
        return reJson;
    }

    /**
     * [功能描述]：查询原始上传数据(列表)
     */
    @RequestMapping(value = "/getOriginalData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<OriginalDataModel> getOriginalData(
            @RequestParam(value = "list[]", required = false) List<String> list,
            OriginalDataModel originalDataModel, String select,
            HttpSession httpsession) {
        ResultListModel<OriginalDataModel> resultListModel = new ResultListModel<OriginalDataModel>();
        resultListModel.setSelect(select);
        List<OriginalDataModel> listOriginal = new ArrayList<OriginalDataModel>();
        if (originalDataModel == null) {
            return resultListModel;
        }
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String devicecode = originalDataModel.getDeviceCode();
            Timestamp begintime = DateUtil.StringToTimestampSecond(originalDataModel.getBeginTime());
            Timestamp endtime = DateUtil.StringToTimestampSecond(originalDataModel.getEndTime());
            String updatetype = originalDataModel.getUpdateType();
            int rowIndex = (originalDataModel.getPage() - 1) * originalDataModel.getRows();
            int rowCount = originalDataModel.getRows();

            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<String> listDeviceCode = new ArrayList<String>();
                listDeviceCode.add(devicecode);

                List<String> listThingcode = new ArrayList<String>();
                String thingcode = originalDataModel.getThingCode();
                if (thingcode != null && !thingcode.isEmpty()) {
                    listThingcode.add(thingcode);
                } else if (list != null && list.size() > 0) {
                    for (String temp : list) {
                        listThingcode.add(temp);
                    }
                } else {
                    List<MapModel> listMap = getAthorityMonitors(listDeviceCode, httpsession);
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                }
                if (listThingcode != null && listThingcode.size() > 0) {
                    // 查询数据
                    int count = monitorStorageService.getOriginalDataCount(
                            listDeviceCode, listThingcode, begintime, endtime,
                            updatetype, select);
                    if (count > 0) {

                        listOriginal = monitorStorageService.getOriginalData(
                                listDeviceCode, listThingcode, begintime, endtime,
                                updatetype, select, rowIndex, rowCount);
                        resultListModel.setTotal(count);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(LOG + ":查询原始数据错误，错误信息为：" + e.getMessage());
        }

        // 追加查询code
        resultListModel.setRows(listOriginal);
        return resultListModel;
    }

    /**
     * [功能描述]：查询原始上传数据(图形)
     */
    @RequestMapping(value = "/getOriginalChartData", method = {RequestMethod.POST})
    @ResponseBody
    public TreeMap<String, List<String>> getOriginalChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]", required = false) List<String> list,
            OriginalDataModel originalDataModel, HttpSession httpsession) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        List<OriginalDataModel> listOriginal = new ArrayList<OriginalDataModel>();
        if (originalDataModel == null) {
            return treeMap;
        }
        try {
            String devicecode = originalDataModel.getDeviceCode();
            Timestamp begintime = DateUtil.StringToTimestampSecond(originalDataModel.getBeginTime());
            Timestamp endtime = DateUtil.StringToTimestampSecond(originalDataModel.getEndTime());
            String updatetype = originalDataModel.getUpdateType();
            int rowIndex = DefaultArgument.INT_DEFAULT;
            int rowCount = DefaultArgument.INT_DEFAULT;
            boolean rldFlag = false;
            if (DefaultArgument.PRO_CN_REALBEGIN.endsWith(originalDataModel.getUpdateType())) {
                rldFlag = true;
            } else {
                rldFlag = false;
            }
            List<String> listThingcode = new ArrayList<String>();
            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<String> listDeviceCode = new ArrayList<String>();
                listDeviceCode.add(devicecode);
                String thingcode = originalDataModel.getThingCode();
                if (thingcode != null && !thingcode.isEmpty()) {
                    listThingcode.add(thingcode);
                } else if (list != null && list.size() > 0) {
                    for (String temp : list) {
                        listThingcode.add(temp);
                    }
                } else {
                    List<MapModel> listMap = getAthorityMonitors(listDeviceCode, httpsession);
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                }
                if (listThingcode != null && listThingcode.size() > 0) {
                    // 查询数据
                    int count = monitorStorageService.getOriginalDataCount(
                            listDeviceCode, listThingcode, begintime, endtime,
                            updatetype, null);
                    if (count > 0) {
                        listOriginal = monitorStorageService.getOriginalData(
                                listDeviceCode, listThingcode, begintime, endtime,
                                updatetype, null, rowIndex, rowCount);
                    }
                }
            }
            List<String> listName = monitorStorageService.getMonNamebyCode(listThingcode);
            // 排序处理
            TreeMap<String, List<OriginalDataModel>> treeOriginal = new TreeMap<String, List<OriginalDataModel>>();
            for (OriginalDataModel tempOriginal : listOriginal) {
                String frequeTime = "";
                if (rldFlag) {
                    frequeTime = tempOriginal.getRtdTime();
                } else {
                    frequeTime = tempOriginal.getBeginTime();
                }
                if (treeOriginal.containsKey(frequeTime)) {
                    treeOriginal.get(frequeTime).add(tempOriginal);
                } else {
                    List<OriginalDataModel> listtemp = new ArrayList<OriginalDataModel>();
                    listtemp.add(tempOriginal);
                    treeOriginal.put(frequeTime, listtemp);
                }
            }
            for (Map.Entry<String, List<OriginalDataModel>> tempTreeOriginal : treeOriginal.entrySet()) {
                String frequetime = tempTreeOriginal.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测物
                for (String temp : listName) {
                    String thingname;
                    String monitorvalue = null;
                    String monitorzsvalue = null;
                    for (OriginalDataModel tempOriginal : tempTreeOriginal.getValue()) {
                        thingname = tempOriginal.getThingName();
                        if (temp.equals(thingname)) {
                            if (rldFlag) {
                                monitorvalue = String.valueOf(tempOriginal.getThingRtd());
                                monitorzsvalue = String.valueOf(tempOriginal.getThingZsRtd());
                            } else {
                                monitorvalue = String.valueOf(tempOriginal.getThingAvg());
                                monitorzsvalue = String.valueOf(tempOriginal.getThingZsAvg());
                            }
                            break;
                        }
                    }
                    if (treeMap.containsKey(temp)) {
                        treeMap.get(temp).add(monitorvalue);

                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(temp, listThing);
                    }
                    if (zsFlag) {
                        String tempzs = temp + "-折算";
                        if (treeMap.containsKey(tempzs)) {
                            treeMap.get(tempzs).add(monitorzsvalue);

                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(monitorzsvalue);
                            treeMap.put(tempzs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询原始数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }
}
