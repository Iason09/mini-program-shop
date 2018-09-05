package com.iason.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Iason on 2018/8/28.
 */
@RestController
@RefreshScope
@Slf4j
public class TestController {

	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String userName;
	@Value("${jdbc.password}")
	private String passWord;

	@Value("${druid.initialSize}")
	private String initialSize;
	@Value("${druid.filters}")
	private String filters;

	@GetMapping("/hello")
	public String hello() {
		System.out.println("url==="+ this.url);
		System.out.println("userName==="+ this.userName);
		System.out.println("password==="+ this.passWord);
		System.out.println("initialSize==="+ this.initialSize);
		System.out.println("filters==="+ this.filters);
		return "hello";
	}
}
