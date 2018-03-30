package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;
import com.fable.industry.bussiness.service.idgenerate.StringGenerator;

/**
 * @auther jiangmingjun
 * @create 2018/1/29
 * @description 字典类型实体
 */
public class DictionaryClassBean {
    @NumberGenerator(name = "DictionaryClassPK")
    private int id;
    /** 序号 */
    private Integer index;
    /** 字典名称 */
    private String dictionarName;
    /** 字典编码 */
   @StringGenerator(name = "DictionaryCodeSK",format = "ZDBM%X{6}")
    private String dictionarCode;
    /** 描述 */
    private String dictionarContent;
    /** 字典类型 */
    private String dictionarType;
    /** 上级字典 */
    private Integer parentId;
    /** 字典类别名 */
    private String dictionarAlias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDictionarName() {
        return dictionarName;
    }

    public void setDictionarName(String dictionarName) {
        this.dictionarName = dictionarName;
    }

    public String getDictionarCode() {
        return dictionarCode;
    }

    public void setDictionarCode(String dictionarCode) {
        this.dictionarCode = dictionarCode;
    }

    public String getDictionarContent() {
        return dictionarContent;
    }

    public void setDictionarContent(String dictionarContent) {
        this.dictionarContent = dictionarContent;
    }

    public String getDictionarType() {
        return dictionarType;
    }

    public void setDictionarType(String dictionarType) {
        this.dictionarType = dictionarType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDictionarAlias() {
        return dictionarAlias;
    }

    public void setDictionarAlias(String dictionarAlias) {
        this.dictionarAlias = dictionarAlias;
    }
}
