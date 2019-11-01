package com.kyq.env.service;

import java.util.List;

import com.kyq.env.model.ResultModel;
import com.kyq.env.pojo.UploadFile;

/**
 * [功能描述]：上传文件服务接口
 * 
 * @author	kyq
 */
public interface IUploadFileService {

	/**
	 * [功能描述]：查询上传文件个数
	 */
	int getUploadFileCount(UploadFile uploadFile);
	
	/**
	 * [功能描述]：查询上传文件明细
	 */
	List<UploadFile> getUploadFile(UploadFile uploadFile);
	
	/**
	 * [功能描述]：新增上传文件
	 */
	ResultModel insertUploadFile(UploadFile uploadFile) throws Exception;
	
	/**
	 * [功能描述]：删除上传文件
	 */
	ResultModel deleteUploadFile(Integer fileId) throws Exception;
	
	/**
	 * [功能描述]：通过ID获取上传文件
	 */
	UploadFile getUploadFileById(Integer fileId);
	
	/**
	 * [功能描述]：存在上传文件个数
	 */
	int existUploadFileCount(String fileType,String fileName);
	
}
