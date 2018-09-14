package com.iason.product.data.model;

/**
 * Created by Iason on 2018/9/13.
 */
public class CateQueryResp {

	private Integer id;

	private String cateName;

	private String cateImg;

	private String description;

	private String jumpLink;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateImg() {
		return cateImg;
	}

	public void setCateImg(String cateImg) {
		this.cateImg = cateImg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJumpLink() {
		return jumpLink;
	}

	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}
}
