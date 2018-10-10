package com.iason.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.iason.common.config.WechatConfig;
import com.iason.common.utils.OkHttpUtil;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iason on 2018/9/29.
 */
@Slf4j
@RestController
@RequestMapping(value = "/wechat")
public class WechatController {

	@Autowired
	private WechatConfig wechatConfig;
	@Autowired
	private OkHttpUtil okHttpUtil;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam String code) {
		log.info("login入参：code = " + code);
		Map<String, Object> map = new HashMap<>();
		map.put("appid", wechatConfig.getAppid());
		map.put("secret", wechatConfig.getSecret());
		map.put("js_code", code);
		map.put("grand_type", "authorization_code");
		Response response = okHttpUtil.doGetSync(wechatConfig.getCode2sessionUrl(), map);

		try {
			String res = response.body().string();
			log.info("调用微信接口" + wechatConfig.getCode2sessionUrl() + "返回：" + res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("调用微信接口" + wechatConfig.getCode2sessionUrl() + "异常");
		}
		return null;
	}

}
