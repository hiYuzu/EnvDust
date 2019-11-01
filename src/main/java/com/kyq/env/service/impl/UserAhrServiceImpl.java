package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IUserAhrDao;
import com.kyq.env.pojo.User;
import com.kyq.env.pojo.UserAhr;
import com.kyq.env.service.IUserAhrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [功能描述]：用户与权限组关系查询服务类接口实现类
 *
 * @author kyq
 */
@Service("userAhrService")
@Transactional(rollbackFor = Exception.class)
public class UserAhrServiceImpl implements IUserAhrService {

    @Resource
    private IUserAhrDao userAhrDao;

    @Override
    public String getUserAhrCode(String userCode) {
        return userAhrDao.getUserAhrCode(userCode);
    }

    @Override
    public int getUserAhrCount(String userCode) {
        return userAhrDao.getUserAhrCount(userCode);
    }

    @Override
    public int getOtherUserAhrCount(UserAhr userAhr) {
        return userAhrDao.getOtherUserAhrCount(userAhr);
    }

    @Override
    public List<User> getOtherUserAhr(UserAhr userAhr) {
        return userAhrDao.getOtherUserAhr(userAhr);
    }

    @Override
    public int insertUserAhr(List<UserAhr> listUserAhr) throws Exception {
        return userAhrDao.insertUserAhr(listUserAhr);
    }

    @Override
    public int deleteUserAhr(String ahrCode, List<String> listUserCode)
            throws Exception {
        return userAhrDao.deleteUserAhr(ahrCode, listUserCode);
    }

}
