package com.iason.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.iason.common.service.FdfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Created by Iason on 2018/9/11.
 */
@Slf4j
@RestController
@RequestMapping(value = "/fdfs")
public class FdfsController {

	@Autowired
	private FdfsService fdfsService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
	public String upload (@RequestPart("file") MultipartFile file) {
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

	@RequestMapping(value = "/uploadBatch", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
	public String uploadBatch (@RequestPart MultipartFile... fileList) {
		int count = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (MultipartFile file : fileList) {
			String imgUrl = fdfsService.uploadFile(file);
			log.info("imgUrl=" + imgUrl);
			if (StringUtils.isNotBlank(imgUrl)) {
				count += 1;
				stringBuilder.append(imgUrl);
				stringBuilder.append(",");
			}
		}
		JSONObject res = new JSONObject();
		JSONObject body = new JSONObject();
		if (fileList.length == count) {
			body.put("imgUrls", stringBuilder.substring(0, stringBuilder.length()-1));
			res.put("success", true);
			res.put("body", body);
		} else {
			res.put("success", false);
		}
		return JSONObject.toJSONString(res);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete (@RequestParam("path") String path) {
		Boolean r = fdfsService.deleteFile(path);
		JSONObject res = new JSONObject();
		res.put("success", r);
		return JSONObject.toJSONString(res);
	}
}
