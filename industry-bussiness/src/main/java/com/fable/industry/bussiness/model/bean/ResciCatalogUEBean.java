package com.fable.industry.bussiness.model.bean;

import com.fable.industry.bussiness.service.idgenerate.NumberGenerator;

import java.sql.Timestamp;

/**
 * @auther jiangmingjun
 * @create 2018/2/3
 * @description 资源编目实体
 */
public class ResciCatalogUEBean {
    @NumberGenerator(name = "ResciPK")
    private int id;
    /** 联系人ID */
    private Integer userId;
    /** 联系人电话 */
    private String telephone;
    /** 数据来源 */
    private String dataSource;
    /** 数据类型 */
    private String resciType;
    /** 资源目录ID */
    private Integer catalogId;
    /** 资源编目状态 */
    private Integer catalogUEState;
    /** 资源名称 */
    private String resciName;
    /** 资源英文名称 */
    private String resciEname;
    /** 资源编码 */
    private String resciCode;
    /** 资源摘要 */
    private String resciContent;
    /** 资源图片名称 */
    private String image;
    /** 资源图片路径 */
    private String imagePath;
    /** OID */
    private String oid;
    /** 主题分类 */
    private Integer topicId;
    /** 行业分类 */
    private Integer industryId;
    /** 资源等级 */
    private String resciLevel;
    /** 资源状态 */
    private Integer resciState;
    /** 提交时间 */
    private Timestamp submitTime;
    /** 发布时间 */
    private Timestamp releaseTime;
    /** 撤销时间 */
    private Timestamp revokeTime;

    /** 额外字段 */
    /** 单位名称 */
    private String comName;
    /** 主题分类名称 */
    private String topicName;
    /** 行业分类名称 */
    private String industryName;
    /** 资源目录名称 */
    private String catalogName;
    /** 公司Id */
    private Integer comId;
    /** 角色Id**/
    private Integer roleId;
    /** 资源登记时间 */
    private String createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getResciType() {
        return resciType;
    }

    public void setResciType(String resciType) {
        this.resciType = resciType;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getCatalogUEState() {
        return catalogUEState;
    }

    public void setCatalogUEState(Integer catalogUEState) {
        this.catalogUEState = catalogUEState;
    }

    public String getResciName() {
        return resciName;
    }

    public void setResciName(String resciName) {
        this.resciName = resciName;
    }

    public String getResciEname() {
        return resciEname;
    }

    public void setResciEname(String resciEname) {
        this.resciEname = resciEname;
    }

    public String getResciCode() {
        return resciCode;
    }

    public void setResciCode(String resciCode) {
        this.resciCode = resciCode;
    }

    public String getResciContent() {
        return resciContent;
    }

    public void setResciContent(String resciContent) {
        this.resciContent = resciContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getResciLevel() {
        return resciLevel;
    }

    public void setResciLevel(String resciLevel) {
        this.resciLevel = resciLevel;
    }

    public Integer getResciState() {
        return resciState;
    }

    public void setResciState(Integer resciState) {
        this.resciState = resciState;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Timestamp getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Timestamp revokeTime) {
        this.revokeTime = revokeTime;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
