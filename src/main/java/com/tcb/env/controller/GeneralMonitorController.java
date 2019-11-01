package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.service.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.model.GeneralMonitorCountModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.NetStatusCount;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * [功能描述]：查询面板数据控制器
 */
@Controller
@RequestMapping("/GeneralMonitorController")
public class GeneralMonitorController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "GeneralMonitorController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(GeneralMonitorController.class);
    /**
     * 声明监测物查询服务类
     */
    @Resource
    private IMonitorStorageService monitorStorageService;
    /**
     * 声明监控数据服务类
     */
    @Resource
    private IGeneralMonitorService generalMonitorService;
    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;
    /**
     * 声明控制器
     */
    @Resource
    private MonitorStorageController monitorStorageController;

    /**
     * [功能描述]：查询设备状态数据
     */
    @RequestMapping(value = "/getGenaralMonitorCount", method = {RequestMethod.POST})
    public @ResponseBody
    GeneralMonitorCountModel getGenaralMonitorCount(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelFlag, HttpSession httpsession) {
        GeneralMonitorCountModel generalMonitorCountModel = new GeneralMonitorCountModel();
        int normalCount = 0;
        int overCount = 0;
        int disconnectCount = 0;
        int nodataCount = 0;
        int otherCount = 0;
        int onlineCount = 0;
        int offlineCount = 0;
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return generalMonitorCountModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<NetStatusCount> listNSCount = new ArrayList<NetStatusCount>();
            List<String> listdevicecode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return generalMonitorCountModel;
            } else if (list == null || list.size() == 0) {
                return generalMonitorCountModel;
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        list.get(0))) {
                    List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId,
                            DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listdevicecode.add(mapModel.getCode());
                    }
                    ;
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, new ArrayList<String>());
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listdevicecode.add(mapModel.getCode());
                        }
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                } else {
                    for (String temp : list) {
                        listdevicecode.add(temp);
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                }

            }
            if (listNSCount != null && listNSCount.size() > 0) {
                for (NetStatusCount netStatusCount : listNSCount) {
                    switch (netStatusCount.getStatusCode()) {
                        case "N":
                            normalCount += netStatusCount.getStatusCount();
                            break;
                        case "NT":
                            overCount += netStatusCount.getStatusCount();
                            break;
                        case "O":
                            disconnectCount += netStatusCount.getStatusCount();
                            break;
                        case "Z":
                            nodataCount += netStatusCount.getStatusCount();
                            break;
                        default:
                            otherCount += netStatusCount.getStatusCount();
                            break;
                    }
                }
                onlineCount = normalCount + overCount;
                offlineCount = disconnectCount + nodataCount + otherCount;
                generalMonitorCountModel.setNormalCount(normalCount);
                generalMonitorCountModel.setOverCount(overCount);
                generalMonitorCountModel.setDisconnectCount(disconnectCount);
                generalMonitorCountModel.setNodataCount(nodataCount);
                generalMonitorCountModel.setOtherCount(otherCount);
                generalMonitorCountModel.setOnlineCount(onlineCount);
                generalMonitorCountModel.setOfflineCount(offlineCount);
                generalMonitorCountModel.setResult(true);
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询设备状态数据错误，错误信息为：" + e.getMessage());
        }
        return generalMonitorCountModel;
    }

    /**
     * [功能描述]：获取设备浓度排名
     */
    @RequestMapping(value = "/getGenaralMonitorValueRanking", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceValueModel> getGenaralMonitorValueRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String thingCode, String dataType, String order,
            int limit, String levelFlag, HttpSession httpsession) {
        ResultListModel<DeviceValueModel> resultListModel = new ResultListModel<DeviceValueModel>();
        List<DeviceValueModel> deviceValueList = new ArrayList<DeviceValueModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> listDeviceCode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                return resultListModel;
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        list.get(0))) {
                    List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId,
                            DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listDeviceCode.add(mapModel.getCode());
                    }
                    ;
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listDeviceCode.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        listDeviceCode.add(temp);
                    }
                }
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0) {
                deviceValueList = generalMonitorService.getGenaralMonitorValueRanking(
                        listDeviceCode, thingCode, dataType, DefaultArgument.ON_LINE_FLAG, order, limit);
                if (deviceValueList != null && deviceValueList.size() > 0) {
                    int i = 1;
                    for (DeviceValueModel deviceValueModel : deviceValueList) {
                        deviceValueModel.setDataOrder(String.valueOf(i));
                        i++;
                    }
                }
                resultListModel.setRows(deviceValueList);
            }
            resultListModel.setResult(true);
        } catch (Exception e) {
            logger.error(LOG + ":获取设备浓度排名错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }
}
