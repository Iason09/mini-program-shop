package com.iason.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iason.product.data.constant.ErrorEnum;
import com.iason.product.data.entity.ImgManage;
import com.iason.product.data.model.ImgQueryResp;
import com.iason.product.data.model.UnifyRespModel;
import com.iason.product.feign.CommonFeignClient;
import com.iason.product.service.ImgService;
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
 * Created by Iason on 2018/7/4.
 */
@Slf4j
@RestController
@RequestMapping(value = "/index")
public class IndexController {

	@Autowired
	private ImgService imgService;
	@Autowired
	private CommonFeignClient commonFeignClient;
	@Autowired
	private RespUtil respUtil;

	/**
	 * 添加图片
	 */
	@PostMapping(value = "/addImg")
	public UnifyRespModel addImg(String param, MultipartFile file) {
		log.info("addImg入参：" + param);
		ImgManage imgManage = JSON.parseObject(param, ImgManage.class);
		if (imgManage == null) {
			return respUtil.respFail(PARAM_PARSE_ERROR);
		}
		ErrorEnum result = inputCheck(imgManage);
		if (result == null) {
			//上传图片
			String uploadRes = commonFeignClient.upload(file);
			if (JSON.parseObject(uploadRes).getBoolean("success")) {
				Date date = new Date();
				imgManage.setCreateTime(date);
				imgManage.setUpdateTime(date);
				imgManage.setDeleteFlag((short) 0);
				String body = JSON.parseObject(uploadRes).getString("body");
				JSONObject jsonObject = JSON.parseObject(body);
				imgManage.setImgUrl(jsonObject.getString("imgUrl"));
				int r = imgService.insert(imgManage);
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
	 * 更新图片
	 */
	@RequestMapping(value = "/updateImg", method = RequestMethod.POST)
	public UnifyRespModel updateImg(@RequestBody ImgManage imgManage) {
		log.info("updateImg入参：" + JSON.toJSONString(imgManage));
		ErrorEnum result;
		if (imgManage.getId() == null) {
			result = ID_IS_NULL;
		} else {
			ImgManage oldImg = imgService.getOneImgByPrimaryKey(imgManage.getId());
			if (oldImg != null) {
				Date date = new Date();
				imgManage.setUpdateTime(date);
				int r = imgService.update(imgManage);
				if (r == 1) {
					if (imgManage.getDeleteFlag() != null && imgManage.getDeleteFlag() == 1) {
						//删除服务器图片
						String uploadRes = commonFeignClient.delete(oldImg.getImgUrl());
						if (JSON.parseObject(uploadRes).getBoolean("success")) {
							log.info("删除图片成功，url= " + oldImg.getImgUrl());
						} else {
							log.error("删除图片失败，url= " + oldImg.getImgUrl());
						}
					}
					return respUtil.respOk("更新图片成功");
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
	@RequestMapping(value = "/getImgs", method = RequestMethod.GET)
	public UnifyRespModel getImgs(@RequestParam Short pageSort,
									  @RequestParam Short moduleSort) {
		log.info("入参：pageSort={},moduleSort=" + pageSort, moduleSort);
		Example example = new Example(ImgManage.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleteFlag", 0);
		criteria.andEqualTo("pageSort", pageSort);
		criteria.andEqualTo("moduleSort", moduleSort);
		example.setOrderByClause("priority asc");
		List<ImgManage> r = imgService.getImgListByExample(example);
		List<ImgQueryResp> list = new ArrayList<>();
		for (ImgManage i : r) {
			ImgQueryResp item = new ImgQueryResp();
			BeanUtils.copyProperties(i, item);
			list.add(item);
		}
		return respUtil.respOk(list);
	}

	private ErrorEnum inputCheck(ImgManage imgManage) {
		String link = imgManage.getJumpLink();
		Short module = imgManage.getModuleSort();
		Short type = imgManage.getImgType();
		Long priority = imgManage.getPriority();
		Short sort = imgManage.getPageSort();
		if (link == null || link.isEmpty()) {
			return JUMP_LINK_IS_NULL;
		} else if (module == null) {
			return MODULE_SORT_IS_NULL;
		} else if (type == null) {
			return IMG_TYPE_IS_NULL;
		} else if (priority == null) {
			return PRIORITY_IS_NULL;
		} else if (sort == null) {
			return PAGE_SORT_IS_NULL;
		}
		return null;
	}

}