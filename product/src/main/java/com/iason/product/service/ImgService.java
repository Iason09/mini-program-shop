package com.iason.product.service;

import com.iason.product.data.entity.ImgManage;
import com.iason.product.mapper.ImgManageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Iason on 2018/9/4.
 */
@Slf4j
@Service
public class ImgService {
	@Autowired
	private ImgManageMapper imgManageMapper;

	public ImgManage getOneImgByObj (ImgManage imgManage) {
		return imgManageMapper.selectOne(imgManage);
	}

	public List<ImgManage> getImgListByObj (ImgManage imgManage) {
		return imgManageMapper.select(imgManage);
	}


}
