package com.fable.industry.bussiness.model.syslogs;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author duy
 * @description 服务日志, 记录三方接口调用情况
 * @date 2018.1.26
 */
@Alias("ServiceLog")
public class ServiceLog {
    /**
     * 主键
     */
    @NumberGenerator(name = "ServiceLogId")
    private int id;

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 请求详情
     */
    private String requestDetail;
    /**
     * 系统维护人
     */
    private String maintenance;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 响应时间
     */
    private String responseTime;

    /**
     * 操作结果 1:成功,2:失败
     */
    private String result;

    /**
     * url地址
     */
    private String url;

    /**
     * 备注
     */
    private String note;

    /**
     * IP
     */
    private String ip;

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
