package com.kyq.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.model.TreeModel;
import org.springframework.stereotype.Service;

import com.tcb.env.dao.IAreaDao;
import com.tcb.env.dao.ITreeDao;
import com.tcb.env.model.TreeModel;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.AreaLevel;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DefaultArgument;

/**
 * [功能描述]：Tree操作服务类实现
 *
 * @author kyq
 */
@Service("treeService")
public class TreeServiceImpl implements ITreeService {

    @Resource
    private ITreeDao treeDao;

    @Resource
    private IAreaDao areaDao;

    @Override
    public ArrayList<TreeModel> getAreaChildrenTree(String parentid) {
        return treeDao.getAreaChildrenTree(parentid);
    }

    @Override
    public int getSubItemCount(String parentid) {
        return treeDao.getSubItemCount(parentid);
    }

    @Override
    public int getAuthorityDeviceCount(String usercode, String projectid, int areaid,
                                       String devicename, String status, String nostatus) {
        return treeDao.getAuthorityDeviceCount(usercode, projectid, areaid, devicename, status, nostatus);
    }

    @Override
    public ArrayList<TreeModel> getAuthorityDevices(String usercode, String projectid,
                                                    int areaid, String devicename, String status, String nostatus) {
        return treeDao.getAuthorityDevice(usercode, projectid, areaid, devicename, status, nostatus);
    }

    @Override
    public int getAuthorityDeviceCountByAhrCode(String ahrCode, int areaid,
                                                String devicename) {
        return treeDao.getAuthorityDeviceCountByAhrCode(ahrCode, areaid,
                devicename);
    }

    @Override
    public ArrayList<TreeModel> getAuthorityDeviceByAhrCode(String ahrCode,
                                                            int areaid, String devicename) {
        return treeDao.getAuthorityDeviceByAhrCode(ahrCode, areaid, devicename);
    }

    @Override
    public ArrayList<TreeModel> getAllMonitor(String ahrCode) {
        return treeDao.getAllMonitor(ahrCode);
    }

    @Override
    public ArrayList<TreeModel> getAllAuthority(String ahrcode) {
        return treeDao.getAllAuthority(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getUserByAhrCode(String ahrcode) {
        return treeDao.getUserByAhrCode(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getModule(String ahrcode) {
        return treeDao.getModule(ahrcode);
    }

    @Override
    public ArrayList<TreeModel> getPage(String ahrcode, String moduleid) {
        return treeDao.getPage(ahrcode, moduleid);
    }

    @Override
    public ArrayList<TreeModel> getControl(String ahrcode, String pageid) {
        return treeDao.getControl(ahrcode, pageid);
    }

    @Override
    public List<Integer> getAuthorityBottomArea(String projectId, List<Integer> listAreaId,
                                                int parentAreaId, String usercode) {
        Area area = new Area();
        AreaLevel areaLevel = new AreaLevel();
        area.setAreaLevel(areaLevel);
        area.setAreaId(parentAreaId);
        List<Area> listarea = areaDao.getAreas(area, false);
        if (listarea != null && listarea.size() > 0) {
            for (Area areaTemp : listarea) {
                String levelFlag = String.valueOf(areaTemp.getAreaLevel()
                        .getLevelFlag());
                if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(levelFlag)) {
                    int count = getAuthorityDeviceCount(usercode, projectId,
                            areaTemp.getAreaId(), null, null, null);
                    if (count > 0) {
                        listAreaId.add(areaTemp.getAreaId());
                    }
                } else {
                    getAuthorityBottomChildArea(projectId, listAreaId, areaTemp.getAreaId(), usercode);
                }
            }
        }
        return listAreaId;
    }

    private List<Integer> getAuthorityBottomChildArea(String projectId, List<Integer> listAreaId,
                                                      int parentAreaId, String usercode) {
        Area area = new Area();
        AreaLevel areaLevel = new AreaLevel();
        area.setAreaLevel(areaLevel);
        area.setParentId(parentAreaId);
        List<Area> listarea = areaDao.getAreas(area, true);
        if (listarea != null && listarea.size() > 0) {
            for (Area areaTemp : listarea) {
                String levelFlag = String.valueOf(areaTemp.getAreaLevel()
                        .getLevelFlag());
                if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(levelFlag)) {
                    int count = getAuthorityDeviceCount(usercode, projectId,
                            areaTemp.getAreaId(), null, null, null);
                    if (count > 0) {
                        listAreaId.add(areaTemp.getAreaId());
                    }
                } else {
                    getAuthorityBottomChildArea(projectId, listAreaId, areaTemp.getAreaId(), usercode);
                }
            }
        }
        return listAreaId;
    }
}
