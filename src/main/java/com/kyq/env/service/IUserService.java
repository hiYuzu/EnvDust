package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.User;

/**
 * [功能描述]：User操作服务类接口
 */
public interface IUserService {

    /**
     * [功能描述]：根据查询条件查询结果个数
     */
    int getCount(User user, boolean ignoredel);

    /**
     * [功能描述]：根据条件查询User数据
     */
    List<User> getUser(User user, boolean ignoredel);

    /**
     * [功能描述]：新增User数据
     */
    int insertUser(List<User> listuser) throws Exception;

    /**
     * [功能描述]：更新user数据
     */
    int updateUser(List<User> listuser) throws Exception;

    /**
     * [功能描述]：物理删除User数据
     */
    int deleteUser(List<String> listid) throws Exception;

    /**
     * [功能描述]：更新用户删除标识
     */
    int updateUserDelete(String userId, String userDelete) throws Exception;

    /**
     * [功能描述]：根据id获取用户名
     */
    String getUserNameById(int userid, String usercode);

    /**
     * [功能描述]：根据id获取用编码
     */
    String getUserCodeById(String userId);

    /**
     * [功能描述]：通过条件（非ID的）获取符合结果个数
     */
    int getUserExist(int userid, String usercode);

    /**
     * [功能描述]：更新用户密码
     */
    int updateUserPwd(int userid, String userpwd);

}
