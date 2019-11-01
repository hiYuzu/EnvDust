package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.AreaLevel;
import com.tcb.env.service.IAreaService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * [功能描述]：Area控制器
 * @author kyq
 */
@Controller
@RequestMapping("/AreaController")
public class AreaController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AreaController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AreaController.class);
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * 声明Area服务
     */
    @Resource
    private IAreaService areaService;

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
     * [功能描述]：查询区域信息
     */
    @RequestMapping(value = "/queryAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AreaModel> queryAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultListModel<AreaModel> resultListModel = new ResultListModel<AreaModel>();
        List<AreaModel> listareamodel = new ArrayList<AreaModel>();
        List<Area> listarea;
        Area area = ConvertArea(areamodel, httpsession);
        boolean ignorecountry = false;
        int count = areaService.getCount(area, ignorecountry);
        if (count > 0) {
            listarea = areaService.getAreas(area, ignorecountry);
            for (Area temp : listarea) {
                AreaModel areaModel = ConvertAreaModel(temp);
                if (areaModel != null) {
                    listareamodel.add(areaModel);
                }
            }
            resultListModel.setRows(listareamodel);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    @RequestMapping(value = "insertAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (areamodel != null) {
            try {
                if (areaService.getAreaExist(areamodel.getAreaId(), areamodel.getAreaName()) == 0) {
                    Area insertArea = ConvertArea(areamodel, httpsession);
                    int intresult = areaService.insertAreas(insertArea);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增区域失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此区域名称，请使用其他区域名称！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增区域失败！");
                logger.error(LOG + "：新增区域失败，信息为:" + e.getMessage());
            }

        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    @RequestMapping(value = "/updateAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateAreas(AreaModel areamodel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (areamodel != null) {
            try {
                if (areaService.getAreaExist(areamodel.getAreaId(), areamodel.getAreaName()) == 0) {
                    List<Area> listarea = new ArrayList<Area>();
                    listarea.add(ConvertArea(areamodel, httpsession));
                    int intresult = areaService.updateAreas(listarea);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新区域失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此区域，请使用其他区域名称！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新区域失败！");
                logger.error(LOG + "：更新区域失败，信息为:" + e.getMessage());
            }
        }
        return resultModel;
    }

    @RequestMapping(value = "/deleteAreas", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteAreas(@RequestParam(value = "list[]") List<Integer> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listid = new ArrayList<Integer>();
                for (Integer areaid : list) {
                    listid.add(areaid);
                }
                int intresult = areaService.deleteAreas(listid);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除区域失败,请检查是否存在关联区域或设备！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除区域失败！");
                logger.error(LOG + "：更新区域失败，信息为:" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除区域失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * [功能描述]：获取上级区域下拉框数据
     */
    @RequestMapping(value = "/queryAreaDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaDropDown(int id, int levelFlag) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        // 获取上级区域值
        listmapmodel = mapService.getAreaMap(id, levelFlag - 1);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);

        return resultListModel;
    }

    /**
     * [功能描述]：获取区域级别下拉框数据
     */
    @RequestMapping(value = "/queryAreaLevelDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryAreaLevelDropDown(int id) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel;
        listmapmodel = mapService.getAreaLevelFlag(id);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);

        return resultListModel;
    }

    /**
     * 获取第四级区域信息
     * @return list
     */
    @RequestMapping("/queryFourthAreaInfo")
    @ResponseBody
    public List<Map<String, String>> queryFourthAreaInfo() {
        return areaService.queryFourthAreaInfo();
    }

    /**
     * [功能描述]：将Area转换成AreaModel
     */
    private Area ConvertArea(AreaModel areaModel, HttpSession httpsession) {
        Area area = new Area();
        if (areaModel != null) {
            area.setAreaId(areaModel.getAreaId());
            area.setAreaName(areaModel.getAreaName());
            AreaLevel areaLevel = new AreaLevel();
            Area parentArea = new Area();
            parentArea.setAreaId(areaModel.getParentId());
            area.setParentId(areaModel.getParentId());
            areaLevel.setLevelId(mapService.getAreaLevelId(areaModel.getLevelId(), areaModel.getLevelFlag()).get(0).getId());
            area.setAreaLevel(areaLevel);
            area.setAreaPath(areaService.getAreaPath(areaModel.getParentId(), areaModel.getAreaName()));
            area.setCityId(areaModel.getCityId());
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                area.setOptUser(loginuser.getUserId());
            }
            area.setRowCount(areaModel.getRows());
            area.setRowIndex((areaModel.getPage() - 1) * areaModel.getRows());
        }
        return area;
    }

    /**
     * [功能描述]：将AreaModel转换成Area
     */
    private AreaModel ConvertAreaModel(Area area) {
        AreaModel areaModel = new AreaModel();
        if (area != null) {
            areaModel.setAreaId(area.getAreaId());
            areaModel.setAreaName(area.getAreaName());
            areaModel.setParentId(area.getParentId());
            List<MapModel> listMapModel = mapService.getAreaMap(area.getParentId(), DefaultArgument.INT_DEFAULT);
            if (listMapModel != null && listMapModel.size() > 0) {
                areaModel.setParentName(listMapModel.get(0).getName());
            } else {
                areaModel.setParentName(null);
            }
            areaModel.setLevelId(area.getAreaLevel().getLevelId());
            areaModel.setLevelFlag(area.getAreaLevel().getLevelFlag());
            areaModel.setLevelName(area.getAreaLevel().getLevelName());
            areaModel.setAreaPath(area.getAreaPath());
            areaModel.setCityId(area.getCityId());
            areaModel.setOptUserName(userService.getUserNameById(area.getOptUser(), null));
            areaModel.setOptTime(DateUtil.TimestampToString(area.getOptTime(), DateUtil.DATA_TIME));
        }
        return areaModel;
    }

}
