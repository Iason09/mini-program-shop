package com.iason.product.utils;

import com.alibaba.fastjson.JSON;
import com.iason.product.data.constant.ErrorEnum;
import com.iason.product.data.model.UnifyRespModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Iason on 2018/9/11.
 */
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class RespUtil {

	public UnifyRespModel respOk (Object Object) {
		UnifyRespModel respModel = new UnifyRespModel();
		respModel.setSuccess(true);
		respModel.setBody(Object);
		log.info(JSON.toJSONString(respModel));
		return respModel;
	}

	public UnifyRespModel respFail (ErrorEnum error) {
		UnifyRespModel respModel = new UnifyRespModel();
		respModel.setSuccess(false);
		respModel.setErrorMsg(error.getErrorMsg());
		respModel.setErrorCode(error.getErrorCode());
		log.info(JSON.toJSONString(respModel));
		return respModel;
	}
}
