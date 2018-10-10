package com.iason.account.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Created by Iason on 2018/9/11.
 */
@FeignClient(name = "common")
public interface CommonFeignClient {

	@RequestMapping(value = "/wechat/login", method = RequestMethod.GET)
	String login(@RequestParam("code") String code);
}
