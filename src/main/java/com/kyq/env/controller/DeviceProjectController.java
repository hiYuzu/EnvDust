package com.kyq.env.controller;

import com.kyq.env.model.DeviceProjectModel;
import com.kyq.env.pojo.DeviceProject;
import com.kyq.env.util.DateUtil;
import com.kyq.env.util.DefaultArgument;
import com.tcb.env.model.DeviceProjectModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.pojo.DeviceProject;
import com.tcb.env.service.IDeviceProjectService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * [功能描述]：设备项目控制器
 * @author kyq
 */
@Controller
@RequestMapping("/DeviceProjectController")
public class DeviceProjectController {

    @Resource
    private IDeviceProjectService deviceProjectService;

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/queryDeviceProject", method = { RequestMethod.POST })
    @ResponseBody
    public ResultListModel<DeviceProjectModel> queryDeviceProject(HttpSession httpsession) {
        ResultListModel<DeviceProjectModel> resultListModel = new ResultListModel<DeviceProjectModel>();
        List<DeviceProjectModel> listDeviceProjectModel = new ArrayList<DeviceProjectModel>();
        List<DeviceProject> listDeviceProject = new ArrayList<DeviceProject>();
        int count = deviceProjectService.getDeviceProjectCount();
        if(count>0){
            listDeviceProject = deviceProjectService.getDeviceProject();
            for (DeviceProject temp : listDeviceProject) {
                DeviceProjectModel deviceProjectModel = ConvertDeviceProjectModel(temp);
                if (deviceProjectModel != null) {
                    listDeviceProjectModel.add(deviceProjectModel);
                }
            }
        }
        resultListModel.setRows(listDeviceProjectModel);
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * [功能描述]：DeviceProject转成DeviceProjectModel
     */
    private DeviceProjectModel ConvertDeviceProjectModel(DeviceProject deviceProject) {
        DeviceProjectModel deviceProjectModel = new DeviceProjectModel();
        if (deviceProject != null) {
            deviceProjectModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
            deviceProjectModel.setProjectCode(deviceProject.getProjectCode());
            deviceProjectModel.setProjectName(deviceProject.getProjectName());
            deviceProjectModel.setProjectOrder(String.valueOf(deviceProject.getProjectOrder()));
            // 操作者
            if(deviceProject.getOptUser() != DefaultArgument.INT_DEFAULT){
                deviceProjectModel.setOptUserName(userService.getUserNameById(deviceProject.getOptUser(), null));
            }
            deviceProjectModel.setOptTime(DateUtil.TimestampToString(deviceProject.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceProjectModel;
    }

}
