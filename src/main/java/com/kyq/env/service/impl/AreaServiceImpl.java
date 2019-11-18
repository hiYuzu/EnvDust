package com.kyq.env.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kyq.env.config.Dom4jConfig;
import com.kyq.env.dao.IAreaDao;
import com.kyq.env.pojo.Area;
import com.kyq.env.pojo.HeWeather;
import com.kyq.env.service.IAreaService;
import com.kyq.env.util.DefaultArgument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [功能描述]：Area操作服务类实现
 *
 * @author kyq
 */
@Service("areaService")
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements IAreaService {

    @Resource
    private IAreaDao areaDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public int getCount(Area area, boolean ignorecountry) {
        if (ignorecountry) {
            return areaDao.getCount(area);
        } else {
            return (areaDao.getCount(area) + areaDao.getCountryCount(area));
        }
    }

    @Override
    public List<Area> getAreas(Area area, boolean ignorecountry) {
        return areaDao.getAreas(area, ignorecountry);
    }

    @Override
    public int insertAreas(Area area) throws Exception {
        return areaDao.insertAreas(area);
    }

    @Override
    public int updateAreas(List<Area> listarea) throws Exception {
        return areaDao.updateAreas(listarea);
    }

    @Override
    public int deleteAreas(List<Integer> listid) throws Exception {
        for (Integer temp : listid) {
            if (areaDao.getRelationCount(temp) > 0) {
                return 0;
            }
        }
        return areaDao.deleteAreas(listid);
    }

    @Override
    public String getAreaPath(int areaid, String areaName) {
        if (areaName == null || areaName.isEmpty()) {
            return areaDao.getAreaPath(areaid);
        } else if (areaid == DefaultArgument.INT_DEFAULT) {
            return areaName;
        } else {
            return areaDao.getAreaPath(areaid).isEmpty() ? areaName : (areaDao
                    .getAreaPath(areaid) + "/" + areaName);
        }
    }

    @Override
    public int getAreaExist(int areaid, String areaname) {
        return areaDao.getAreaExist(areaid, areaname);
    }

    @Override
    public int insertAreaWeather(HeWeather weather) {
        try {
            return areaDao.insertAreaWeather(weather);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getAreaWeatherCount(HeWeather weather) {
        return areaDao.getAreaWeatherCount(weather);
    }

    @Override
    public List<String> getAreaCityId() {
        return areaDao.getAreaCityId();
    }

}
