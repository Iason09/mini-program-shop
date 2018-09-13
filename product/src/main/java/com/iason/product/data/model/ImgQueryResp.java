package com.iason.product.data.model;

/**
 * Created by Iason on 2018/9/13.
 */
public class ImgQueryResp {

	private Integer id;

	private String imgUrl;

	private String jumpLink;

	private Short imgType;

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
		this.imgUrl = imgUrl;
	}

	public String getJumpLink() {
		return jumpLink;
	}

	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}

	public Short getImgType() {
		return imgType;
	}

	public void setImgType(Short imgType) {
		this.imgType = imgType;
	}
}
