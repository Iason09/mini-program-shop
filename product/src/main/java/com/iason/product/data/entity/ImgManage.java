package com.iason.product.data.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "IMG_MANAGE")
public class ImgManage {
    @Id
    private Integer id;

    private String imgUrl;

    private String jumpLink;

    private Short imgType;

    private Long priority;

    private Short pageSort;

    private Short moduleSort;

    private Short deleteFlag;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getJumpLink() {
        return jumpLink;
    }

    public void setJumpLink(String jumpLink) {
        this.jumpLink = jumpLink == null ? null : jumpLink.trim();
    }

    public Short getImgType() {
        return imgType;
    }

    public void setImgType(Short imgType) {
        this.imgType = imgType;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Short getPageSort() {
        return pageSort;
    }

    public void setPageSort(Short pageSort) {
        this.pageSort = pageSort;
    }

    public Short getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(Short moduleSort) {
        this.moduleSort = moduleSort;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}