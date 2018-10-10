package com.iason.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.iason.account.data.model.UnifyRespModel;
import com.iason.account.feign.CommonFeignClient;
import com.iason.account.utils.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.iason.account.data.constant.ErrorEnum.FEIGN_RETURN_FAIL;

/**
 * Created by Iason on 2018/9/26.
 */
@Slf4j
@RestController
@RequestMapping(value = "/wechat")
public class WeChatController {

	@Autowired
	private CommonFeignClient commonFeignClient;
	@Autowired
	private RespUtil respUtil;

	@GetMapping(value = "/login")
	public UnifyRespModel login(@RequestParam String code) {
		String res = commonFeignClient.login(code);
		//{"session_key":"94awc0Z6XhtGErzS437WAA==","openid":"ow9Eb5J1dPhlF0bYjlD_QS99Bw8Y"}
		if (StringUtils.isNotBlank(res)) {
			JSONObject jsonObject = JSONObject.parseObject(res);
			if (jsonObject != null) {
				String sessionKey = jsonObject.getString("session_key");
				String openId = jsonObject.getString("openid");


/*				if (StringUtils.isNotBlank(jsonObject.getString("openid"))) {

				}
				if (StringUtils.isNotBlank(jsonObject.getString("session_key"))) {

				}*/

			}
			//暂时返回
			return respUtil.respFail(FEIGN_RETURN_FAIL);
		} else {
			return respUtil.respFail(FEIGN_RETURN_FAIL);
		}
	}
}
