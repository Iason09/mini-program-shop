package com.iason.product.utils;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.ContentType;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Iason on 2018/10/10.
 */
public class FeignSpringFormEncoder extends FormEncoder {
	/**
	 * Constructor with the default Feign's encoder as a delegate.
	 */
	public FeignSpringFormEncoder() {
		this(new Default());
	}


	/**
	 * Constructor with specified delegate encoder.
	 *
	 * @param delegate delegate encoder, if this encoder couldn't encode object.
	 */
	public FeignSpringFormEncoder(Encoder delegate) {
		super(delegate);

		MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(ContentType.MULTIPART);
//		processor.addWriter(new SpringSingleMultipartFileWriter());
		processor.addWriter(new FeignSpringSingleMultipartFileWriter());
		processor.addWriter(new SpringManyMultipartFilesWriter());
	}


	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
		if (bodyType.equals(MultipartFile.class)) {
			MultipartFile file = (MultipartFile) object;
			Map data = Collections.singletonMap(file.getName(), object);
			super.encode(data, MAP_STRING_WILDCARD, template);
			return;
		} else if (bodyType.equals(MultipartFile[].class)) {
			MultipartFile[] file = (MultipartFile[]) object;
			if(file != null) {
				Map data = Collections.singletonMap(file.length == 0 ? "" : file[0].getName(), object);
				super.encode(data, MAP_STRING_WILDCARD, template);
				return;
			}
		}
		super.encode(object, bodyType, template);
	}
}
