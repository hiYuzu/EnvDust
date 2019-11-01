package com.kyq.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kyq.env.dao.IUploadFileDao;
import com.kyq.env.model.ResultModel;
import com.kyq.env.pojo.UploadFile;
import com.kyq.env.service.IUploadFileService;
import com.kyq.env.util.FunctionUtil;
import org.springframework.stereotype.Service;

/**
 * [功能描述]：上传文件服务接口实现类
 *
 * @author kyq
 */
@Service("uploadFileService")
public class UploadFileServiceImpl implements IUploadFileService {

    @Resource
    private IUploadFileDao uploadFileDao;

    @Override
    public int getUploadFileCount(UploadFile uploadFile) {
        return uploadFileDao.getUploadFileCount(uploadFile);
    }

    @Override
    public List<UploadFile> getUploadFile(UploadFile uploadFile) {
        return uploadFileDao.getUploadFile(uploadFile);
    }

    @Override
    public ResultModel insertUploadFile(UploadFile uploadFile) throws Exception {
        ResultModel resultModel = new ResultModel();
        if (uploadFile != null && !uploadFile.getFileName().isEmpty()) {
            //先删除相关数据
            uploadFileDao.deleteUploadFileByItem(uploadFile.getFileType(), uploadFile.getFileName());
            if (uploadFileDao.insertUploadFile(uploadFile) > 0) {
                resultModel.setResult(true);
                resultModel.setDetail("新增文件成功！");
            } else {
                resultModel.setResult(false);
                resultModel.setDetail("新增文件失败，请重新尝试！");
                throw new Exception("新增文件失败");
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("新增故障上传文件失败，数据不能未空！");
        }
        return resultModel;
    }

    @Override
    public ResultModel deleteUploadFile(Integer fileId) throws Exception {
        ResultModel resultModel = new ResultModel();
        if (fileId != null) {
            UploadFile uploadFile = uploadFileDao.getUploadFileById(fileId);
            if (uploadFile != null && uploadFile.getFileId() != null) {
                String fileName = uploadFile.getFileName();
                String filePath = uploadFile.getFilePath();
                if (uploadFileDao.deleteUploadFile(fileId) > 0) {
                    //删除
                    if (filePath != null && !filePath.isEmpty()
                            && fileName != null && !fileName.isEmpty()) {
                        FunctionUtil.deleteFile(uploadFile.getFilePath() + "\\" + uploadFile.getFileName());
                    }
                    resultModel.setResult(true);
                    resultModel.setDetail("删除文件成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除文件失败，请重新尝试！");
                }
            } else {
                resultModel.setResult(false);
                resultModel.setDetail("删除文件失败，请重新尝试！");
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除文件失败，未传递删除数据！");
        }
        return resultModel;
    }

    @Override
    public UploadFile getUploadFileById(Integer fileId) {
        return uploadFileDao.getUploadFileById(fileId);
    }

    @Override
    public int existUploadFileCount(String fileType, String fileName) {
        return uploadFileDao.existUploadFileCount(fileType, fileName);
    }

}
