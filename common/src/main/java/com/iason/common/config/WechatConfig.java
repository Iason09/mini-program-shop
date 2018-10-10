package com.iason.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Iason on 2018/9/29.
 */
@Configuration
public class WechatConfig {

	@Value("${wechat.appid}")
	private String appid;

	@Value("${wechat.secret}")
	private String secret;

	@Value("${wechat.code2session.url}")
	private String code2sessionUrl;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getCode2sessionUrl() {
		return code2sessionUrl;
	}

	public void setCode2sessionUrl(String code2sessionUrl) {
		this.code2sessionUrl = code2sessionUrl;
	}
}
