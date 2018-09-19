package com.iason.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iason.product.data.constant.ErrorEnum;
import com.iason.product.data.entity.CateManage;
import com.iason.product.data.model.CateQueryResp;
import com.iason.product.data.model.UnifyRespModel;
import com.iason.product.feign.CommonFeignClient;
import com.iason.product.service.CateService;
import com.iason.product.utils.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.iason.product.data.constant.ErrorEnum.*;

/**
 * Created by Iason on 2018/9/13.
 */
@Slf4j
@RestController
@RequestMapping(value = "/cate")
public class CateController {

	@Autowired
	private CateService cateService;
	@Autowired
	private RespUtil respUtil;
	@Autowired
	private CommonFeignClient commonFeignClient;

	/**
	 * 添加分类
	 */
	@PostMapping(value = "/addCate")
	public UnifyRespModel addCate(String param, MultipartFile file) {
		log.info("addCate入参：" + param);
		CateManage cateManage = JSON.parseObject(param, CateManage.class);
		if (cateManage == null) {
			return respUtil.respFail(PARAM_PARSE_ERROR);
		}
		ErrorEnum result = inputCheck(cateManage);
		if (result == null) {
			//上传图片
			String uploadRes = commonFeignClient.upload(file);
			if (JSON.parseObject(uploadRes).getBoolean("success")) {
				Date date = new Date();
				cateManage.setCreateTime(date);
				cateManage.setUpdateTime(date);
				cateManage.setDeleteFlag((short) 0);
				String body = JSON.parseObject(uploadRes).getString("body");
				JSONObject jsonObject = JSON.parseObject(body);
				cateManage.setCateImg(jsonObject.getString("imgUrl"));
				int r = cateService.insert(cateManage);
				if (r == 1) {
					return respUtil.respOk("添加图片成功");
				} else {
					result = INSERT_FAIL;
				}
			} else {
				result = FEIGN_RETURN_FAIL;
			}
		}
		return respUtil.respFail(result);
	}

	/**
	 * 更新分类
	 */
	@PostMapping(value = "/updateCate")
	public UnifyRespModel updateCate(@RequestBody CateManage cateManage) {
		log.info("updateCate入参：" + JSON.toJSONString(cateManage));
		ErrorEnum result;
		if (cateManage.getId() == null) {
			result = ID_IS_NULL;
		} else {
			CateManage oldCate = cateService.getOneCateByPrimaryKey(cateManage.getId());
			if (oldCate != null) {
				Date date = new Date();
				cateManage.setUpdateTime(date);
				int r = cateService.update(cateManage);
				if (r == 1) {
					if (cateManage.getDeleteFlag() != null && cateManage.getDeleteFlag() == 1) {
						//删除服务器图片
						String uploadRes = commonFeignClient.delete(oldCate.getCateImg());
						if (JSON.parseObject(uploadRes).getBoolean("success")) {
							log.info("删除图片成功，url= " + oldCate.getCateImg());
						} else {
							log.error("删除图片失败，url= " + oldCate.getCateImg());
						}
					}
					return respUtil.respOk("更新分类成功");
				} else {
					result = UPDATE_FAIL;
				}
			} else {
				result = DATA_NON_EXISTENT;
			}
		}
		return respUtil.respFail(result);
	}

	/**
	 * 获取图片数组
	 */
	@GetMapping(value = "/getCates")
	public UnifyRespModel getCates(@RequestParam Short parentId) {
		log.info("getCates入参：parentId = " + parentId);
		Example example = new Example(CateManage.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleteFlag", 0);
		criteria.andEqualTo("parentId", parentId);
		example.setOrderByClause("priority asc");
		List<CateManage> r = cateService.getCateListByExample(example);
		List<CateQueryResp> list = new ArrayList<>();
		for (CateManage i : r) {
			CateQueryResp item = new CateQueryResp();
			BeanUtils.copyProperties(i, item);
			list.add(item);
		}
		return respUtil.respOk(list);
	}

	private ErrorEnum inputCheck(CateManage cateManage) {
		String cateImg = cateManage.getCateImg();
		Short parentId = cateManage.getParentId();
		Integer priority = cateManage.getPriority();
		String cateName = cateManage.getCateName();
		if (cateImg == null || cateImg.isEmpty()) {
			return URL_IS_NULL;
		} else if (parentId == null) {
			return PARENT_ID_IS_NULL;
		} else if (priority == null) {
			return PRIORITY_IS_NULL;
		} else if (cateName == null) {
			return NAMR_IS_NULL;
		}
		return null;
	}
}
