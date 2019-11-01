package com.kyq.env.model;

/**
 * [功能描述]：计划任务结果model
 *
 * @author kyq
 */
public class CommResultModel extends BaseModel {

    private String resultId;
    private String resultQn;
    private String resultCp;
    private String mainCn;
    private String mainCnName;
    private String mainDeviceCode;
    private String mainDeviceName;

    /**
     * @return the resultId
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * @param resultId the resultId to set
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    /**
     * @return the resultQn
     */
    public String getResultQn() {
        return resultQn;
    }

    /**
     * @param resultQn the resultQn to set
     */
    public void setResultQn(String resultQn) {
        this.resultQn = resultQn;
    }

    /**
     * @return the resultCp
     */
    public String getResultCp() {
        return resultCp;
    }

    /**
     * @param resultCp the resultCp to set
     */
    public void setResultCp(String resultCp) {
        this.resultCp = resultCp;
    }

    /**
     * @return the mainCn
     */
    public String getMainCn() {
        return mainCn;
    }

    /**
     * @param mainCn the mainCn to set
     */
    public void setMainCn(String mainCn) {
        this.mainCn = mainCn;
    }

    /**
     * @return the mainCnName
     */
    public String getMainCnName() {
        return mainCnName;
    }

    /**
     * @param mainCnName the mainCnName to set
     */
    public void setMainCnName(String mainCnName) {
        this.mainCnName = mainCnName;
    }

    /**
     * @return the mainDeviceCode
     */
    public String getMainDeviceCode() {
        return mainDeviceCode;
    }

    /**
     * @param mainDeviceCode the mainDeviceCode to set
     */
    public void setMainDeviceCode(String mainDeviceCode) {
        this.mainDeviceCode = mainDeviceCode;
    }

    /**
     * @return the mainDeviceName
     */
    public String getMainDeviceName() {
        return mainDeviceName;
    }

    /**
     * @param mainDeviceName the mainDeviceName to set
     */
    public void setMainDeviceName(String mainDeviceName) {
        this.mainDeviceName = mainDeviceName;
    }

}
