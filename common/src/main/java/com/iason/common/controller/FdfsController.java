package com.iason.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.iason.common.service.FdfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Iason on 2018/9/11.
 */
@Slf4j
@RestController
@RequestMapping(value = "/fdfs")
public class FdfsController {

	@Autowired
	private FdfsService fdfsService;

	@PostMapping(value = "/upload")
	public String upload (MultipartFile file) {
		String imgUrl = fdfsService.uploadFile(file);
		JSONObject res = new JSONObject();
		JSONObject body = new JSONObject();
		log.info("imgUrl=" + imgUrl);
		if (StringUtils.isNotBlank(imgUrl)) {
			body.put("imgUrl", imgUrl);
			res.put("success", true);
			res.put("body", body);
		} else {
			res.put("success", false);
		}
		return JSONObject.toJSONString(res);
	}

	@GetMapping(value = "/delete")
	public String delete (@RequestParam String path) {
		Boolean r = fdfsService.deleteFile(path);
		JSONObject res = new JSONObject();
		res.put("success", r);
		return JSONObject.toJSONString(res);
	}
}
