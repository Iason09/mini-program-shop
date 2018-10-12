package com.iason.product.utils;

import feign.form.multipart.AbstractWriter;
import feign.form.multipart.Output;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Iason on 2018/10/12.
 */
public class FeignSpringSingleMultipartFileWriter extends AbstractWriter {

	public FeignSpringSingleMultipartFileWriter() {
	}

	public boolean isApplicable(Object value) {
		return value != null && value instanceof MultipartFile;
	}

	protected void write(Output output, String key, Object value) throws Exception {
		MultipartFile file = (MultipartFile)value;
		this.writeFileMetadata(output, "file", file.getOriginalFilename(), file.getContentType());
		output.write(file.getBytes());
	}
}
