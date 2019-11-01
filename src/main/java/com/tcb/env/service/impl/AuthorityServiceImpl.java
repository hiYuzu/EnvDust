package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IAuthorityDao;
import com.tcb.env.pojo.Authority;
import com.tcb.env.service.IAuthorityService;

/**
 * [功能描述]：权限组操作服务类接口实现
 *
 * @author kyq
 */
@Service("authorityService")
@Transactional(rollbackFor = Exception.class)
public class AuthorityServiceImpl implements IAuthorityService {

    @Resource
    private IAuthorityDao authorityDao;

    @Override
    public int getAuthorityCount(Authority authority) {
        return authorityDao.getAuthorityCount(authority);
    }

    @Override
    public List<Authority> getAuthority(Authority authority) {
        return authorityDao.getAuthority(authority);
    }

    @Override
    public int insertAuthority(List<Authority> listAuthority) throws Exception {
        return authorityDao.insertAuthority(listAuthority);
    }

    @Override
    public int updateAuthority(List<Authority> listAuthority) throws Exception {
        return authorityDao.updateAuthority(listAuthority);
    }

    @Override
    public int deleteAuthority(List<String> listid) throws Exception {
        return authorityDao.deleteAuthority(listid);
    }

    @Override
    public int getAuthorityExist(String authotiryCode, int authotiryId) {
        return authorityDao.getAuthorityExist(authotiryCode, authotiryId);
    }

    @Override
    public String getAuthorityCodeById(String authorityId) {
        return authorityDao.getAuthorityCodeById(authorityId);
    }

}
