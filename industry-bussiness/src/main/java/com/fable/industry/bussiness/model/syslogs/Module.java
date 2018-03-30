package com.fable.industry.bussiness.model.syslogs;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @description 模块方法定义
 * @author duy
 * @date 2018.1.26
 */
@Alias("Module")
public class Module {
    /**
     * 主键
     */
    @NumberGenerator(name="ModuleId")
    private int id;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块代码
     */
    private String moduleCode;
    /**
     * 排序
     */
    private String order;

    /**
     * 备注
     */
    private String note;
    /**
     * 创建者
     */
    private int creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改者
     */
    private int updator;
    /**
     * 修改时间
     */
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
