package com.fable.industry.bussiness.model.syslogs;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author duy
 * @description 系统操作日志，包含登录和模块操作两个部分
 * @date 2018.1.26
 */
@Alias("SysOperLog")
public class SysOperLog {

    /**
     * id
     */
    @NumberGenerator(name = "SysOperLogID")
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 模块ID
     */
    private int moduleId;

    /**
     * 客户端代理
     */
    private String clientAgent;

    /**
     * 备注
     */
    private String note;

    /**
     * 页面名称
     */
    private String moduleName;

    /**
     * 操作人姓名
     */
    private String userName;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 创建时间范围（开始）
     */
    private String createTimeStart;

    /**
     * 创建时间范围（结束）
     */
    private String createTimeEnd;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(String clientAgent) {
        this.clientAgent = clientAgent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
