package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IManufacturerDao;
import com.kyq.env.pojo.Manufacturer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IManufacturerDao;
import com.tcb.env.pojo.Manufacturer;
import com.tcb.env.service.IManufacturerService;

/**
 * @author kyq
 */
@Service("manufacturerService")
@Transactional(rollbackFor = Exception.class)
public class ManufacturerServiceImpl implements IManufacturerService {
    @Resource
    private IManufacturerDao manufacturerDao;

    @Override
    public int getCount(Manufacturer manufacturer, boolean isLike) {

        return manufacturerDao.getCount(manufacturer, isLike);
    }

    @Override
    public List<Manufacturer> getManufacturer(Manufacturer manufacturer) {

        return manufacturerDao.getManufacturer(manufacturer);
    }

    @Override
    public int insertManufacturer(List<Manufacturer> listmanufacturer) throws Exception {

        return manufacturerDao.insertManufacturer(listmanufacturer);
    }

    @Override
    public int deleteManufacturer(List<Integer> manufacturerid) throws Exception {

        return manufacturerDao.deleteManufacturer(manufacturerid);
    }

    @Override
    public int updateManufacturer(List<Manufacturer> listmanufacturer) throws Exception {

        return manufacturerDao.updateManufacturer(listmanufacturer);
    }

    @Override
    public int getManufacturerExist(int manufacturerid, String manufacturercode) {

        return manufacturerDao.getManufacturerExist(manufacturerid,
                manufacturercode);
    }

}
