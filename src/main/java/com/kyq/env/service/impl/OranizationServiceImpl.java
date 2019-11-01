package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IOranizationDao;
import com.kyq.env.pojo.Oranization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IOranizationDao;
import com.tcb.env.pojo.Oranization;
import com.tcb.env.service.IOranizationService;

/**
 * [功能描述]：组织服务接口实现类
 *
 * @author kyq
 */
@Service("oranizationService")
@Transactional(rollbackFor = Exception.class)
public class OranizationServiceImpl implements IOranizationService {
    @Resource
    private IOranizationDao oranizationDao;

    @Override
    public int insertOranization(Oranization oranization)
            throws Exception {
        return oranizationDao.insertOranization(oranization);
    }

    @Override
    public int deleteOranization(List<Integer> oranizationid) throws Exception {
        return oranizationDao.deleteOranization(oranizationid);
    }

    @Override
    public int updateOranization(List<Oranization> listoranization)
            throws Exception {
        return oranizationDao.updateOranization(listoranization);
    }

    @Override
    public int getCount(Oranization oranization, boolean isLike) {
        return oranizationDao.getCount(oranization, isLike);
    }

    @Override
    public List<Oranization> getOranization(Oranization oranization) {
        return oranizationDao.getOranization(oranization);
    }

    @Override
    public int getOranizationExist(int oranizationid, String oranizationname) {
        return oranizationDao.getOranizationExist(oranizationid,
                oranizationname);
    }

    @Override
    public String getOranizationPathById(int orgid) {
        return oranizationDao.getOranizationPathById(orgid);
    }

}
