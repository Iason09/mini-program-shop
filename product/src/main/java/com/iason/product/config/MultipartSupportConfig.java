package com.iason.product.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Iason on 2018/9/12.
 */
@Component
public class MultipartSupportConfig {

	@Bean
	@Primary
	@Scope("prototype")
	public Encoder feignEncoder() {
		return new SpringFormEncoder();

	}
}
