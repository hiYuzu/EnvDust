package com.kyq.env.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.kyq.env.pojo.*;
import com.kyq.env.util.DateUtil;
import com.kyq.env.util.DefaultArgument;
import com.kyq.env.util.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kyq.env.model.DeviceModel;
import com.kyq.env.model.MapDeviceModel;
import com.kyq.env.model.MapModel;
import com.kyq.env.model.MapMonitorData;
import com.kyq.env.model.ResultAjaxPushModel;
import com.kyq.env.model.ResultListModel;
import com.kyq.env.model.ResultModel;
import com.kyq.env.model.TreeModel;
import com.kyq.env.model.UserModel;
import com.kyq.env.service.IAlarmService;
import com.kyq.env.service.IDeviceService;
import com.kyq.env.service.IMapService;
import com.kyq.env.service.ITreeService;
import com.kyq.env.service.IUserService;

/**
 * [功能描述]：设备控制器
 *
 * @author kyq
 */
@Controller
@RequestMapping("/DeviceController")
public class DeviceController {
    /**
     * 日志输出标记
     */
    private static final String LOG = "DeviceController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceController.class);
    /**
     * 声明gson对象
     */
    Gson gson = new Gson();
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;
    /**
     * 声明device对象</p>
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * 声明查询map服务
     */
    @Resource
    private IMapService mapService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明报警服务
     */
    @Resource
    private IAlarmService alarmService;

    /**
     * 用来存储session对应的消息,并且用来停止线程
     */
    public static Map<String, MapMonitorData> monitorMap;

    /**
     *  定义同时会有20个人访问百度地图
     *
     * @Param monitorMap
     */
    static {
        monitorMap = new ConcurrentHashMap<>(20);
    }

    /**
     * [功能描述]：获取地图显示数据
     */
    @RequestMapping(value = "/getDeviceMapData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultAjaxPushModel getDeviceMapData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelflag, String nostatus, String select, int maxsize,
            HttpSession httpsession) {
        /* 存储到monitorMap中 */
        String sessionId = getSessionId(httpsession);
        if (sessionId != null && !"".equals(sessionId)) {
            /* 用来监控数量的请求 */
            MapMonitorData mapMonitorData = new
                    MapMonitorData(list, levelflag, nostatus, select, maxsize, httpsession);
            monitorMap.put(sessionId, mapMonitorData);
        }
        ResultAjaxPushModel resultAjaxPushModel = new ResultAjaxPushModel();
        resultAjaxPushModel.setSelect(select);
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER)) {
            return resultAjaxPushModel;
        }
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        List<MapDeviceModel> listMapDev;
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return resultAjaxPushModel;
                } else {
                    List<String> listdevicecode = new ArrayList<String>();
                    List<Integer> listareaid = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listareaid = treeService.getAuthorityBottomArea(projectId,
                                listareaid, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, nostatus);
                        for (TreeModel treeModel : listDev) {
                            if (maxsize > listdevicecode.size()) {
                                listdevicecode.add(treeModel.getId());
                            } else {
                                break;
                            }
                        }
                    } else if (listareaid != null && listareaid.size() > 0) {
                        for (Integer areaid : listareaid) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, nostatus);
                            for (TreeModel treeModel : listDev) {
                                if (maxsize > listdevicecode.size()) {
                                    listdevicecode.add(treeModel.getId());
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        for (String temp : list) {
                            if (maxsize > listdevicecode.size()) {
                                listdevicecode.add(temp);
                            } else {
                                break;
                            }
                        }
                    }
                    if (listdevicecode == null || listdevicecode.size() == 0) {
                        return resultAjaxPushModel;
                    }
                    listMapDev = deviceService.getMapDevice(listdevicecode, null, null, null,
                            null, null, DefaultArgument.INT_DEFAULT, DefaultArgument.INT_DEFAULT);
                    //设置设备图片
                    if (listMapDev != null && listMapDev.size() > 0) {
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            String deviceStatus = mapDeviceModel.getStatusCode();
                            if ("N".equals(deviceStatus)) {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_NORMAL);
                            } else if ("NT".equals(deviceStatus)) {
                                //判断超标等级
                                String levelNo = alarmService.getAlarmLevel(mapDeviceModel.getDeviceCode());
                                mapDeviceModel.setLevelNo(levelNo);
                                switch (levelNo) {
                                    case "1": {
                                        mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM1);
                                        break;
                                    }
                                    case "2": {
                                        mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM2);
                                        break;
                                    }
                                    case "3": {
                                        mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM3);
                                        break;
                                    }
                                    default: {
                                        mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM);
                                    }
                                }
                            } else if ("O".equals(deviceStatus) || "Z".equals(deviceStatus)) {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_UNLINK);
                            } else {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_FAULT);
                            }
                        }
                    }

                    resultAjaxPushModel.setResult(listMapDev);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询地图信息失败，信息为：" + e.getMessage());
            }
        }
        return resultAjaxPushModel;
    }

    /**
     * [功能描述]：获取报警详细信息
     */
    @RequestMapping(value = "/getDeviceMapAlarmDetail", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapDeviceModel> getDeviceMapAlarmDetail(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelflag,
            String nostatus, int rows, int page, HttpSession httpsession) {
        ResultListModel<MapDeviceModel> resultListModel = new ResultListModel<MapDeviceModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            List<String> listdevicecode = new ArrayList<String>();
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                List<TreeModel> listDev = treeService.getAuthorityDevices(
                        usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                for (TreeModel treeModel : listDev) {
                    listdevicecode.add(treeModel.getId());
                }
            } else if (levelflag != null && !levelflag.isEmpty()) {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelflag.equals(DefaultArgument.BOTTOM_LEVEL_FALG)) {
                    listareaid.add(Integer.valueOf(list.get(0)));
                } else {
                    listareaid = treeService.getAuthorityBottomArea(projectId,
                            listareaid, Integer.valueOf(list.get(0)), usercode);
                }
                for (Integer areaid : listareaid) {
                    List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, nostatus);
                    for (TreeModel treeModel : listDev) {
                        listdevicecode.add(treeModel.getId());
                    }
                }
            } else {
                for (String temp : list) {
                    listdevicecode.add(temp);
                }
            }
            int count = 0;
            List<MapDeviceModel> listMapDev;
            if (listdevicecode != null && listdevicecode.size() > 0) {
                count = deviceService.getMapDeviceCount(listdevicecode, null, nostatus, null, null, null);
                if (count > 0) {
                    listMapDev = deviceService.getMapDevice(listdevicecode, null, nostatus, null, null, null, (page - 1) * rows, rows);
                    // 设置设备状态
                    if (listMapDev != null && listMapDev.size() > 0) {
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            if (mapDeviceModel != null) {
                                List<String> statusInfoList = alarmService.getRcentlyAlarmInfo(mapDeviceModel.getDeviceCode(),
                                        mapDeviceModel.getStatusCode());
                                if (statusInfoList != null && statusInfoList.size() > 0) {
                                    String statusInfo = "";
                                    for (String tempInfo : statusInfoList) {
                                        statusInfo += tempInfo + "\r\n";
                                    }
                                    mapDeviceModel.setStatusInfo(statusInfo);
                                }
                            }
                        }
                        resultListModel.setRows(listMapDev);
                    }
                }
            }
            resultListModel.setTotal(count);
        } catch (Exception e) {
            logger.error(LOG + ":查询报警信息失败，信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * [功能描述]：新增设备
     */
    @RequestMapping(value = "/insertDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        Device device = new Device();
        if (deviceModel != null) {
            try {
                device.setDeviceCode(deviceModel.getDeviceCode());
                device.setDeviceMn(deviceModel.getDeviceMn());
                if (deviceService.getCount(device) == 0) {
                    device = ConvertDevice(deviceModel, httpsession);
                    // 创建表
                    boolean flag = deviceService.createStorageTable(device);
                    // 插入数据
                    int intresult = 0;
                    if (flag) {
                        intresult = deviceService.insertDevice(device);
                    }
                    if (intresult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("新增设备成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此设备编号或MN号，请使用其他设备编号或MN号！");
                }

            } catch (Exception e) {
                // 删除表
                try {
                    deviceService.dropStorageTable(device);
                } catch (Exception e1) {
                    logger.error("删除新建表失败，原因：" + e1.getMessage());
                } finally {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增设备失败!");
                    logger.error(LOG + ":" + e.getMessage());
                }
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：查询设备
     */
    @RequestMapping(value = "/queryDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceModel> queryDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultListModel<DeviceModel> resultListModel = new ResultListModel<DeviceModel>();
        List<DeviceModel> listDeviceModel = new ArrayList<DeviceModel>();
        List<Device> listDevice = new ArrayList<Device>();
        Device device = ConvertDevice(deviceModel, httpsession);
        int count = deviceService.getCount(device);

        if (count > 0) {
            listDevice = deviceService.getDevice(device);
            for (Device temp : listDevice) {
                DeviceModel deviceModell = ConvertDeviceModel(temp);
                if (deviceModell != null) {
                    listDeviceModel.add(deviceModell);
                }
            }
            resultListModel.setRows(listDeviceModel);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * [功能描述]：删除设备
     */
    @RequestMapping(value = "/deleteDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteDevice(@RequestParam(value = "list[]") List<Integer> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listid = new ArrayList<Integer>();
                for (Integer userid : list) {
                    listid.add(userid);
                }
                List<Device> listDevice = new ArrayList<Device>();
                for (Integer id : listid) {
                    Device device = new Device();
                    device.setDeviceId(id);
                    device.setInspectTime(DateUtil.GetSystemDateTime(0));
                    List<Device> listtemp = deviceService.getDevice(device);
                    for (Device devicetemp : listtemp) {
                        listDevice.add(devicetemp);
                    }
                }
                int intresult = deviceService.deleteDevice(listid);
                if (intresult > 0) {
                    for (Device device : listDevice) {
                        try {
                            if (device != null) {
                                //删除报警信息
                                alarmService.deleteDeviceAlarm(device.getDeviceCode());
                                //删除设备表
                                deviceService.dropStorageTable(device);
                            }
                        } catch (Exception ed) {
                            logger.error(LOG + "：删除设备表失败，设备表为：" + device.getDeviceCode());
                            continue;
                        }
                    }
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除设备失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除设备失败！");
                logger.error(LOG + ":" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除设备失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：更新设备
     */
    @RequestMapping(value = "/updateDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (deviceModel != null) {
            try {
                int codeExist = deviceService.getDeviceCodeExist(
                        deviceModel.getDeviceId(), deviceModel.getDeviceCode());
                int mnExist = deviceService.getDeviceMnExist(
                        deviceModel.getDeviceId(), deviceModel.getDeviceMn());
                // 如果两个都等于0说明不存在则可以修改
                if (codeExist == 0 && mnExist == 0) {
                    List<Device> listdevice = new ArrayList<Device>();
                    listdevice.add(ConvertDevice(deviceModel, httpsession));
                    int intresult = deviceService.updateDevice(listdevice);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此设备编号或MN号，请使用其他设备编号或MN号！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新设备失败!");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * [功能描述]：更新站点地图位置
     */
    @RequestMapping(value = "/updateDeviceLocation", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeviceLocation(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (deviceModel != null) {
            try {
                String deviceCode = deviceModel.getDeviceCode();
                String deviceX = String.valueOf(deviceModel.getDeviceX());
                String deviceY = String.valueOf(deviceModel.getDeviceY());
                if (deviceX != null && !deviceX.isEmpty() && deviceY != null && !deviceY.isEmpty()) {
                    int intresult = deviceService.updateDeviceLocation(deviceCode, deviceX, deviceY);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("更新站点地图位置成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新站点地图位置失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("服务器未接收到更新数据，请重新登陆后再尝试！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新站点失败!");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * [功能描述]：下拉框查询，设备厂商code-name
     */
    @RequestMapping(value = "/queryDevicemfrCodeDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicemfrCodeDropDown(String mfrCode) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceMfrCode(mfrCode);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * [功能描述]：下拉框查询，设备状态code-name
     */
    @RequestMapping(value = "/queryDevicestatusCodeDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicestatusCodeDropDown(String statusCode) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceStatusCode(statusCode);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * [功能描述]：下拉框查询，设备区域id-name
     */
    @RequestMapping(value = "/queryDeviceAreaDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDeviceAreaDropDown(int id, int levelFlag) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getAreaMap(id, Integer.valueOf(DefaultArgument.BOTTOM_LEVEL_FALG));
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * [功能描述]：下拉框查询，负责人id-name
     */
    @RequestMapping(value = "/queryDevicePrincipleDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicePrincipleDropDown(int devicePrinciple) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceprinciple(devicePrinciple);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * [功能描述]：下拉框查询，监督单位id-name
     */
    @RequestMapping(value = "/queryDeviceOversightUnit", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDeviceOversightUnit(int orgId) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceOversightUnit(orgId);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * [功能描述]：model转成device
     */
    private Device ConvertDevice(DeviceModel deviceModel, HttpSession httpsession) {
        Device device = new Device();
        if (deviceModel != null) {
            device.setDeviceId(deviceModel.getDeviceId());
            device.setDeviceCode(deviceModel.getDeviceCode());
            device.setDeviceMn(deviceModel.getDeviceMn());
            device.setDeviceName(deviceModel.getDeviceName());
            device.setDeviceIp(deviceModel.getDeviceIp());
            device.setDevicePort(deviceModel.getDevicePort());
            device.setDevicePwd(deviceModel.getDevicePwd());
            device.setDeviceX(deviceModel.getDeviceX());
            device.setDeviceY(deviceModel.getDeviceY());
            device.setSystemVersion(deviceModel.getSystemVersion());
            device.setInspectTime(DateUtil.StringToTimestamp(deviceModel.getInspectTime()));
            device.setBuildFirm(deviceModel.getBuildFirm());
            device.setDeviceAddress(deviceModel.getDeviceAddress());
            // 设置外键mfrcode
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setMfrCode(deviceModel.getMfrCode());
            device.setManufacturer(manufacturer);
            // 设置外键，statuscode
            Status status = new Status();
            status.setStatusCode(deviceModel.getStatusCode());
            device.setStatus(status);
            // 设置外键，areaid。查看区域，区域是否null || empty
            Area area = new Area();
            if (deviceModel.getAreaId() != null && !deviceModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(deviceModel.getAreaId()));
            }
            device.setArea(area);
            // 设置外键，device_principal. 查看  负责人  是否存在
            User user = new User();
            String userId = deviceModel.getUserId();
            if (userId == null || userId.isEmpty()) {//不存在直接使用默认值-1
                user.setUserId(DefaultArgument.INT_DEFAULT);
            } else {//存在直接赋值
                user.setUserId(Integer.valueOf(deviceModel.getUserId()));
            }
            device.setUser(user);
            // 设置外键，oversight_unit.查看监督单位是否存在，同上
            Oranization oranization = new Oranization();
            String orgId = deviceModel.getOrgId();
            if (orgId == null || orgId.isEmpty()) {
                oranization.setOrgId(DefaultArgument.INT_DEFAULT);
            } else {
                oranization.setOrgId(Integer.valueOf(deviceModel.getOrgId()));
            }
            device.setOranization(oranization);
            //设置外键，project_id.设备项目
            DeviceProject deviceProject = new DeviceProject();
            String projectId = deviceModel.getProjectId();
            if (!StringUtils.isEmpty(projectId)) {
                deviceProject.setProjectId(Integer.parseInt(projectId));
                deviceProject.setProjectCode(deviceModel.getProjectCode());
                deviceProject.setProjectName(deviceModel.getProjectName());
                if (!StringUtils.isEmpty(deviceModel.getProjectOrder())) {
                    deviceProject.setProjectId(Integer.parseInt(deviceModel.getProjectOrder()));
                }
            }
            device.setDeviceProject(deviceProject);
            // 设置设备辐射面积
            device.setDeviceKm(deviceModel.getDeviceKm());
            //设置平台统计
            device.setStaMinute(deviceModel.getStaMinute());
            device.setStaHour(deviceModel.getStaHour());
            device.setStaDay(deviceModel.getStaDay());
            //强制数据回复标识
            if (!DefaultArgument.NONE_DEFAULT.equals(deviceModel.getReplyFlag())) {
                device.setReplyFlag(deviceModel.getReplyFlag());
            }
            device.setForceReply(deviceModel.getForceReply());
            device.setSmsFlag(deviceModel.getSmsFlag());
            device.setStaFlow(deviceModel.getStaFlow());
            device.setPipeArea(deviceModel.getPipeArea());
            if (!StringUtils.isEmpty(deviceModel.getHourCount())) {
                device.setHourCount(Integer.valueOf(deviceModel.getHourCount()));
            }
            // 获取操作者
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                device.setOptUser(loginuser.getUserId());
            } else {
                device.setOptUser(deviceModel.getDeviceId());
            }
            device.setRowCount(deviceModel.getRows());
            device.setRowIndex((deviceModel.getPage() - 1) * deviceModel.getRows());
        }
        return device;
    }

    /**
     * [功能描述]：device转成model
     */
    private DeviceModel ConvertDeviceModel(Device device) {
        DeviceModel deviceModel = new DeviceModel();
        if (device != null) {
            deviceModel.setDeviceId(device.getDeviceId());
            deviceModel.setDeviceCode(device.getDeviceCode());
            deviceModel.setDeviceMn(device.getDeviceMn());
            deviceModel.setDeviceName(device.getDeviceName());
            deviceModel.setDeviceIp(device.getDeviceIp());
            deviceModel.setDevicePort(device.getDevicePort());
            deviceModel.setDevicePwd(device.getDevicePwd());
            deviceModel.setDeviceX(device.getDeviceX());
            deviceModel.setDeviceY(device.getDeviceY());
            deviceModel.setSystemVersion(device.getSystemVersion());
            if (device.getInspectTime() != null) {
                deviceModel.setInspectTime(String.valueOf(device.getInspectTime()));
            }
            deviceModel.setBuildFirm(device.getBuildFirm());
            deviceModel.setDeviceAddress(device.getDeviceAddress());
            // 外键mfr
            deviceModel.setMfrCode(device.getManufacturer().getMfrCode());
            deviceModel.setMfrName(device.getManufacturer().getMfrName());
            // 外键status
            if (device.getStatus() != null) {
                deviceModel.setStatusCode(device.getStatus().getStatusCode());
                deviceModel.setStatusName(device.getStatus().getStatusName());
            }
            // 外键area
            deviceModel.setAreaId(String.valueOf(device.getArea().getAreaId()));
            deviceModel.setAreaName(device.getArea().getAreaName());
            // 外键user
            int userId = device.getUser().getUserId();
            if (userId != DefaultArgument.INT_DEFAULT) {
                deviceModel.setUserId(String.valueOf(userId));
                deviceModel.setUserName(device.getUser().getUserName());
                deviceModel.setUserTel(device.getUser().getUserTel());
                deviceModel.setUserRemark(device.getUser().getUserRemark());
            }
            // 外键ORG
            int orgId = device.getOranization().getOrgId();
            if (orgId != DefaultArgument.INT_DEFAULT) {
                deviceModel.setOrgId(String.valueOf(orgId));
                deviceModel.setOrgName(device.getOranization().getOrgName());
                deviceModel.setOrgLiaison(device.getOranization().getOrgLiaison());
            }
            //外键Project
            DeviceProject deviceProject = device.getDeviceProject();
            if (deviceProject != null) {
                deviceModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                deviceModel.setProjectCode(deviceProject.getProjectCode());
                deviceModel.setProjectName(deviceProject.getProjectName());
                deviceModel.setProjectOrder(String.valueOf(deviceProject.getProjectOrder()));
            }
            // 设置设备辐射面积
            deviceModel.setDeviceKm(device.getDeviceKm());
            //设置平台统计
            deviceModel.setStaMinute(device.getStaMinute());
            deviceModel.setStaHour(device.getStaHour());
            deviceModel.setStaDay(device.getStaDay());
            //前置数据回复标识
            if (device.getReplyFlag() != null && !device.getReplyFlag().isEmpty()) {
                deviceModel.setReplyFlag(device.getReplyFlag());
            } else {
                deviceModel.setReplyFlag(DefaultArgument.NONE_DEFAULT);
            }
            deviceModel.setForceReply(device.getForceReply());
            deviceModel.setSmsFlag(device.getSmsFlag());
            deviceModel.setStaFlow(device.getStaFlow());
            deviceModel.setPipeArea(device.getPipeArea());
            deviceModel.setHourCount(String.valueOf(device.getHourCount()));
            // 操作者
            deviceModel.setOptUserName(userService.getUserNameById(device.getOptUser(), null));
            deviceModel.setOptTime(DateUtil.TimestampToString(device.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceModel;
    }

    /**
     * [功能描述]：用来得到session的sessionId
     */
    public String getSessionId(HttpSession httpSession) {
        String sessionId = null;
        if (httpSession != null) {
            sessionId = httpSession.getId();
        }
        return sessionId;
    }

    /**
     * [功能描述]：用来剔除监控map的sessionId
     */
    public void removeSessionId(String sessionId) {
        if (sessionId != null && !sessionId.isEmpty() && monitorMap.containsKey(sessionId)) {
            monitorMap.remove(sessionId);
        }
    }
}