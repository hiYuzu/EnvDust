package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IAccessDeviceDao;
import com.tcb.env.dao.IAccessMonitorDao;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.IAccessOperatorService;
import com.tcb.env.util.DefaultArgument;

/**
 * [功能描述]：权限组访问数据操作接口实现类
 *
 * @author kyq
 */
@Service("accessOperator")
@Transactional(rollbackFor = Exception.class)
public class AccessOperatorServiceImpl implements IAccessOperatorService {

	@Resource
	private IAccessDeviceDao accessDeviceDao;
	@Resource
	private IAccessMonitorDao accessMonitorDao;

	@Override
	public int insertAccessDevice(String ahrCode, List<String> listDevCode,
			int optUser) throws Exception {
		return accessDeviceDao
				.insertAccessDevice(ahrCode, listDevCode, optUser);
	}

	@Override
	public int deleteAccessDevice(List<String> listAhrCode) throws Exception {
		return accessDeviceDao.deleteAccessDevice(listAhrCode);
	}

	@Override
	public void updateAccessMonitor(String ahrCode, List<String> listMonCode,
			int optUser) throws Exception {
		List<String> listAhrCode = new ArrayList<String>();
		listAhrCode.add(ahrCode);
		accessMonitorDao.deleteAccessMonitor(listAhrCode);
		if (listMonCode != null && listMonCode.size() > 0) {
			accessMonitorDao.insertAccessMonitor(ahrCode, listMonCode, optUser);
		}
	}

	@Override
	public int getJudgeAhrDeviceCount(Device device, String ahrCode) {
		int count = DefaultArgument.INT_DEFAULT;
		if (device != null) {
			String flag = device.getHaveAhr();
			if (flag == null || flag.isEmpty()) {
				count = accessDeviceDao.getAhrDeviceCount(device, ahrCode,
						DefaultArgument.IS_AHR_DEVICE)
						+ accessDeviceDao.getAhrDeviceCount(device, ahrCode,
								DefaultArgument.NOT_AHR_DEVICE);
			} else {
				count = accessDeviceDao
						.getAhrDeviceCount(device, ahrCode, flag);
			}
		}
		return count;
	}

	@Override
	public List<Device> getJudgeAhrDevice(Device device, String ahrCode) {
		List<Device> listAll = new ArrayList<Device>();
		if (device != null) {
			String flag = device.getHaveAhr();
			List<Device> listNotAhr = new ArrayList<Device>();
			List<Device> listAhr = new ArrayList<Device>();
			if (flag == null || flag.isEmpty()) {
				listNotAhr = accessDeviceDao.getAhrDevice(device, ahrCode,
						DefaultArgument.IS_AHR_DEVICE);
				if (device.getRowCount() > listNotAhr.size()) {
					device.setRowIndex(0);
					device.setRowCount(device.getRowCount() - listNotAhr.size());
					listAhr = accessDeviceDao.getAhrDevice(device, ahrCode,
							DefaultArgument.NOT_AHR_DEVICE);
				}
			} else {
				listNotAhr = accessDeviceDao
						.getAhrDevice(device, ahrCode, flag);
			}
			if (listNotAhr != null && listNotAhr.size() > 0) {
				listAll.addAll(listNotAhr);
			}
			if (listAhr != null && listAhr.size() > 0) {
				listAll.addAll(listAhr);
			}
		}
		return listAll;
	}

	@Override
	public int deleteAccessDeviceSingle(String ahrCode, List<String> listDevCode) {
		return accessDeviceDao.deleteAccessDeviceSingle(ahrCode, listDevCode);
	}

}
