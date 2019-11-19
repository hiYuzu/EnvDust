package com.kyq.env.model;


import com.kyq.env.util.DefaultArgument;

/**
 * [功能描述]：页面厂商model
 *
 * @author kyq
 */
public class ManufacturerModel extends BaseModel {
    private int mfrId = DefaultArgument.INT_DEFAULT;//递增ID
    private String mfrCode;//厂商编号
    private String mfrName;//厂商名称
    private String mfrAddress;//厂商地址
    private String mfrRemark;//厂商备注
    private int optUser = DefaultArgument.INT_DEFAULT;//操作者

    /**
     * @return the mfrId
     */
    public int getMfrId() {
        return mfrId;
    }

    /**
     * @param mfrId the mfrId to set
     */
    public void setMfrId(int mfrId) {
        this.mfrId = mfrId;
    }

    /**
     * @return the mfrCode
     */
    public String getMfrCode() {
        return mfrCode;
    }

    /**
     * @param mfrCode the mfrCode to set
     */
    public void setMfrCode(String mfrCode) {
        this.mfrCode = mfrCode;
    }

    /**
     * @return the mfrName
     */
    public String getMfrName() {
        return mfrName;
    }

    /**
     * @param mfrName the mfrName to set
     */
    public void setMfrName(String mfrName) {
        this.mfrName = mfrName;
    }

    /**
     * @return the mfrAddress
     */
    public String getMfrAddress() {
        return mfrAddress;
    }

    /**
     * @param mfrAddress the mfrAddress to set
     */
    public void setMfrAddress(String mfrAddress) {
        this.mfrAddress = mfrAddress;
    }

    /**
     * @return the mfrRemark
     */
    public String getMfrRemark() {
        return mfrRemark;
    }

    /**
     * @param mfrRemark the mfrRemark to set
     */
    public void setMfrRemark(String mfrRemark) {
        this.mfrRemark = mfrRemark;
    }

    /**
     * @return the optUser
     */
    public int getOptUser() {
        return optUser;
    }

    /**
     * @param optUser the optUser to set
     */
    public void setOptUser(int optUser) {
        this.optUser = optUser;
    }


}
