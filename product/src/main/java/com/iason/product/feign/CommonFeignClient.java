package com.iason.product.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Created by Iason on 2018/9/11.
 */
@FeignClient(name = "mini-program-shop-common")
public interface CommonFeignClient {

	@RequestMapping(value = "/fdfs/upload", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
	String upload (@RequestPart("file") MultipartFile file);

	@RequestMapping(value = "/fdfs/delete", method = RequestMethod.POST)
	String delete (@RequestParam("path") String path);
}
