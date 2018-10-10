package com.iason.product.config;

import com.iason.product.utils.FeignSpringFormEncoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
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
	public Encoder feignFormEncoder() {
		return new FeignSpringFormEncoder();
	}
}
