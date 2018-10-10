package com.iason.common.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Iason on 2018/9/29.
 */
@Slf4j
@Component
public class OkHttpUtil {

	public Response doGetSync (String url, Map<String, Object> param) {
		OkHttpClient client = new OkHttpClient();
		if (param != null && !param.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("?");
			for (String key: param.keySet()) {
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(param.get(key));
				stringBuilder.append("&");
			}
			url = url + stringBuilder.subSequence(0,stringBuilder.length()-1).toString();
		}
		Request request = new Request.Builder()
				.get()
				.url(url)
				.build();
		try {
			return client.newCall(request).execute();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("doGetSync方法异常");
			return null;
		}
	}
}
