package com.iason.product.service;

import com.iason.product.data.entity.CateManage;
import com.iason.product.mapper.CateManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by Iason on 2018/9/13.
 */
@Service
public class CateService {
	@Autowired
	private CateManageMapper cateManageMapper;

	public CateManage getOneCateByPrimaryKey (Integer id) {
		return cateManageMapper.selectByPrimaryKey(id);
	}

	public List<CateManage> getCateListByExample (Example example) {
		return cateManageMapper.selectByExample(example);
	}

	public List<CateManage> getCateListByObj (CateManage cateManage) {
		return cateManageMapper.select(cateManage);
	}

	public int insert (CateManage cateManage) {
		return cateManageMapper.insertSelective(cateManage);
	}

	public int update (CateManage cateManage) {
		return cateManageMapper.updateByPrimaryKeySelective(cateManage);
	}
}
