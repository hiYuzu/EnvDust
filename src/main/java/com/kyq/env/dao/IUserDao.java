package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.User;
import org.apache.ibatis.annotations.Param;



import com.tcb.env.pojo.User;

/**
 * [功能描述]：用户数据库操作接口
 * 
 * @author	kyq
 */
public interface IUserDao {
	
	/**
	 * [功能描述]：查询结果个数
	 */
	int getCount(@Param("user")User user, @Param("ignoredel")boolean ignoredel);
	
	/**
	 * [功能描述]：查询User数据
	 */
	List<User> getUser(@Param("user")User user,@Param("ignoredel")boolean ignoredel);
	
	/**
	 * [功能描述]：新增User数据
	 */
	int insertUser(@Param("listuser")List<User> listuser);
	
	/**
	 * [功能描述]：更新User数据
	 */
	int updateUser(@Param("listuser")List<User> listuser);
	
	/**
	 * [功能描述]：物理删除User数据
	 */
	int deleteUser(@Param("idList")List<String> idList);
	
	/**
	 * [功能描述]：更新用户删除标识
	 */
	int updateUserDelete(@Param("userId")String userId,@Param("userDelete")String userDelete);
	
	/**
	 * [功能描述]：通过条件获取名称
	 */
	String getUserNameById(@Param("userid")int userid,@Param("usercode")String usercode);
	
	/**
	 * [功能描述]：通过ID获取用户编码
	 */
	String getUserCodeById(@Param("userId")String userId);
	
	/**
	 * [功能描述]：通过条件（非ID的）获取符合结果个数
	 */
	int getUserExist(@Param("userid")int userid,@Param("usercode")String usercode);
	
	/**
	 * [功能描述]：更新用户密码
	 */
	int updateUserPwd(@Param("userid")int userid,@Param("userpwd")String userpwd);
	
}
