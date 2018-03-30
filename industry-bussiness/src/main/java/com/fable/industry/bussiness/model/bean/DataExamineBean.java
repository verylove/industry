package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

import java.sql.Timestamp;

/**
 * @auther jiangmingjun
 * @create 2018/1/26
 * @description 资源编目审核实体
 */
public class DataExamineBean {

    @NumberGenerator(name = "DataExaminePK")
    private int id;
    /** 审核步骤 */
    private String examineStep;
    /** 审核状态 */
    private String examineState;
    /** 审核人 */
    private int userId;
    /** 审核意见 */
    private String examineContent;
    /** 保存状态 */
    private String saveState;
    /** 审核时间 */
    private Timestamp updateTime;
    /** 编目ID */
    private Integer cataUEId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamineStep() {
        return examineStep;
    }

    public void setExamineStep(String examineStep) {
        this.examineStep = examineStep;
    }

    public String getExamineState() {
        return examineState;
    }

    public void setExamineState(String examineState) {
        this.examineState = examineState;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExamineContent() {
        return examineContent;
    }

    public void setExamineContent(String examineContent) {
        this.examineContent = examineContent;
    }

    public String getSaveState() {
        return saveState;
    }

    public void setSaveState(String saveState) {
        this.saveState = saveState;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCataUEId() {
        return cataUEId;
    }

    public void setCataUEId(Integer cataUEId) {
        this.cataUEId = cataUEId;
    }
}
