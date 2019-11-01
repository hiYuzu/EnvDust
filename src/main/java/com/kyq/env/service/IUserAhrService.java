package com.kyq.env.service;

import java.util.List;

import com.kyq.env.pojo.User;
import com.kyq.env.pojo.UserAhr;

/**
 * [功能描述]：用户与权限组关系查询服务类接口
 * 
 * @author kyq
 */
public interface IUserAhrService {

	/**
	 * [功能描述]：获取用户权限组
	 */
	String getUserAhrCode(String userCode);

	/**
	 * [功能描述]：是否存在用户
	 */
	int getUserAhrCount(String userCode);

	/**
	 * [功能描述]：获取权限组内用户数量
	 */
	int getOtherUserAhrCount(UserAhr userAhr);

	/**
	 * [功能描述]：获取权限组内用户数据
	 */
	List<User> getOtherUserAhr(UserAhr userAhr);

	/**
	 * [功能描述]：新增用户权限组关系数据
	 */
	int insertUserAhr(List<UserAhr> listUserAhr) throws Exception;

	/**
	 * [功能描述]：删除用户权限组关系数据
	 */
	int deleteUserAhr(String ahrCode, List<String> listUserCode)
			throws Exception;

}
