package com.kyq.env.model;

/**
 * 
 * <p>[功能描述]：页面传递File</p>
 *
 */
public class UploadFileModel extends BaseModel {

	private String fileId;
	private String fileType;
	private String fileName;
	private String filePath;
	private String fileRelativePath;
	private String uploadTime;
	
	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the fileRelativePath
	 */
	public String getFileRelativePath() {
		return fileRelativePath;
	}
	/**
	 * @param fileRelativePath the fileRelativePath to set
	 */
	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}
	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}
	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}
