package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.User;
import com.tcb.env.pojo.UserAhr;

/**
 * [功能描述]：用户与权限组关系数据库操作接口
 * 
 * @author kyq
 */
public interface IUserAhrDao {

	/**
	 * [功能描述]：获取用户权限组
	 */
	String getUserAhrCode(@Param("userCode") String userCode);
	
	/**
	 * <p>[功能描述]：是否存在用户</p>
	 */
	int getUserAhrCount(@Param("userCode") String userCode);
	
	/**
	 * [功能描述]：获取权限组外用户数量
	 */
	int getOtherUserAhrCount(@Param("userAhr") UserAhr userAhr);

	/**
	 * [功能描述]：获取权限组外用户数据
	 */
	List<User> getOtherUserAhr(@Param("userAhr") UserAhr userAhr);

	/**
	 * [功能描述]：新增用户权限组关系数据
	 */
	int insertUserAhr(@Param("listUserAhr") List<UserAhr> listUserAhr);

	/**
	 * [功能描述]：删除用户权限组关系数据
	 */
	int deleteUserAhr(@Param("ahrCode") String ahrCode,
			@Param("listUserCode") List<String> listUserCode);

}
