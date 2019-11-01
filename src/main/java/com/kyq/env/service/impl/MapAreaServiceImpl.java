package com.kyq.env.service.impl;

import java.util.List;

import com.kyq.env.dao.IMapAreaDao;
import com.kyq.env.pojo.MapArea;
import com.kyq.env.pojo.MapAreaPoint;
import com.kyq.env.service.IMapAreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 
 * [功能描述]：百度地图覆盖区域确定 ServiceImpl
 *
 * @author	kyq
 *
 */
@Service("mapAreaService")
@Transactional(rollbackFor = Exception.class)
public class MapAreaServiceImpl implements IMapAreaService {

    @Resource
    private IMapAreaDao mapAreaDao;
	
	@Override
	public List<MapArea> getMapArea(MapArea target) {
		return this.mapAreaDao.getMapArea(target);
	}

	@Override
	public int getMapAreaPointCount(int maId) {
		return this.mapAreaDao.getMapAreaPointCount(maId);
	}

	@Override
	public List<MapAreaPoint> getMapAreaPoint(MapArea mapArea) {
		return this.mapAreaDao.getMapAreaPoint(mapArea);
	}

	@Override
	public int addMapArea(MapArea mapArea) {
		int n = this.mapAreaDao.addMapArea(mapArea);
		if(n > 0){
			return mapArea.getMaId();
		}
		return -1;
	}

	@Override
	public boolean editMapArea(MapArea mapArea) {
		int n = this.mapAreaDao.editMapArea(mapArea);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteMapArea(int maId) {
		int n = this.mapAreaDao.deleteArea(maId);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addBatchMapAreaPoint(List<MapAreaPoint> mapAreaPointList) {
		int n = this.mapAreaDao.addBatchMapAreaPoint(mapAreaPointList);
		if(n > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<MapArea> getAllMapAreaPoint() {
		return this.mapAreaDao.getAllMapAreaPoint();
	}
}
