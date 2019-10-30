package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.ICollectDeviceBoxVaseDao;
import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.service.ICollectDeviceBoxVaseService;
import com.tcb.env.service.ICommMainService;

/**
 * 
 * <p>[功能描述]：采样记录服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月26日下午3:52:05
 * @since	EnvDust 1.0.0
 *
 */
@Service("collectDeviceBoxVaseService")
public class CollectDeviceBoxVaseServiceImpl implements
		ICollectDeviceBoxVaseService {

	@Resource
	private ICollectDeviceBoxVaseDao collectDeviceBoxVaseDao;

	@Override
	public List<VaseDataModel> getVaseDataModel(String deviceCode, String vaseNo) {
		return collectDeviceBoxVaseDao.getVaseDataModel(deviceCode, vaseNo);
	}

}
