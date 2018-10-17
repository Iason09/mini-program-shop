package com.iason.product.service;

import com.iason.product.data.entity.ProductManage;
import com.iason.product.mapper.ProductManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Iason on 2018/10/9.
 */
@Service
public class GoodService {

	@Autowired
	private ProductManageMapper productManageMapper;

	public int insert (ProductManage productManage) {
		return productManageMapper.insertSelective(productManage);
	}
	public int updateByPrimaryKey (ProductManage productManage) {
		return productManageMapper.updateByPrimaryKeySelective(productManage);
	}
}
