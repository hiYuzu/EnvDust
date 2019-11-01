package com.kyq.env.dao;

import java.util.List;

import com.kyq.env.pojo.UploadFile;
import org.apache.ibatis.annotations.Param;

/**
 * [功能描述]：上传文件数据库接口
 * 
 * @author	kyq
 */
public interface IUploadFileDao {
	
	/**
	 * [功能描述]：查询上传文件个数
	 */
	int getUploadFileCount(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * [功能描述]：查询上传文件明细
	 */
	List<UploadFile> getUploadFile(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * [功能描述]：新增上传文件
	 */
	int insertUploadFile(@Param("uploadFile")UploadFile uploadFile);
	
	/**
	 * [功能描述]：删除上传文件
	 */
	int deleteUploadFile(@Param("fileId")Integer fileId);
	
	/**
	 * [功能描述]：删除上传文件
	 */
	int deleteUploadFileByItem(
			@Param("fileType")String fileType,
			@Param("fileName")String fileName);
	
	/**
	 * [功能描述]：通过ID获取上传文件
	 */
	UploadFile getUploadFileById(@Param("fileId")Integer fileId);
	
	/**
	 * [功能描述]：存在上传文件个数
	 */
	int existUploadFileCount(
			@Param("fileType")String fileType,
			@Param("fileName")String fileName);

}
