package com.tcb.env.model;

/**
 * <p>[功能描述]：DeviceProjectModel</p>
 *
 * @author kyq
 */
public class DeviceProjectModel extends BaseModel {

    private String projectId;
    private String projectCode;
    private String projectName;
    private String projectOrder;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectOrder() {
        return projectOrder;
    }

    public void setProjectOrder(String projectOrder) {
        this.projectOrder = projectOrder;
    }

}
