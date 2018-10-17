package com.iason.product.controller;

import com.alibaba.fastjson.JSON;
import com.iason.product.data.constant.ErrorEnum;
import com.iason.product.data.entity.ProductManage;
import com.iason.product.data.model.UnifyRespModel;
import com.iason.product.feign.CommonFeignClient;
import com.iason.product.service.GoodService;
import com.iason.product.utils.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

import static com.iason.product.data.constant.ErrorEnum.*;

/**
 * Created by Iason on 2018/9/21.
 */
@Slf4j
@RestController
@RequestMapping(value = "/good")
public class GoodController {

	@Autowired
	private RespUtil respUtil;
	@Autowired
	private CommonFeignClient commonFeignClient;
	@Autowired
	private GoodService goodService;

	@PostMapping(value = "/addGood")
	public UnifyRespModel addGood(@RequestParam String param,
								  @RequestPart MultipartFile icon,
								  @RequestPart MultipartFile desc,
								  @RequestPart MultipartFile... fileList) {
		if (fileList.length < 1) {
			log.error("应至少上传1张图片！");
			return respUtil.respFail(PARAM_ERROR_DEFAULT);
		}
		log.info("addGood入参：" + param);
		ProductManage productManage = JSON.parseObject(param, ProductManage.class);
		if (productManage == null) {
			return respUtil.respFail(PARAM_PARSE_ERROR);
		}
		ErrorEnum result = inputCheck(productManage);
		if (result == null) {
			String uploadIconRes = commonFeignClient.upload(icon);
			String uploadDescRes = commonFeignClient.upload(desc);
			String uploadRes = commonFeignClient.uploadBatch(fileList);
			log.info("上传图片list返回：" + uploadRes);
			if (JSON.parseObject(uploadIconRes).getBoolean("success") &&
					JSON.parseObject(uploadDescRes).getBoolean("success") &&
					JSON.parseObject(uploadRes).getBoolean("success")) {
				String iconUrl = JSON.parseObject(JSON.parseObject(uploadIconRes).getString("body")).getString("imgUrl");
				String desUrl = JSON.parseObject(JSON.parseObject(uploadDescRes).getString("body")).getString("imgUrl");
				String albumUrl = JSON.parseObject(JSON.parseObject(uploadRes).getString("body")).getString("imgUrls");

				Date date = new Date();
				if (productManage.getSaleNum() == null) {
					productManage.setSaleNum(0);
				}
				productManage.setCreateTime(date);
				productManage.setUpdateTime(date);
				productManage.setDeleteFlag((short) 0);
				productManage.setDescriptionUrl(desUrl);
				productManage.setIconUrl(iconUrl);
				productManage.setAlbumUrl(albumUrl);
				int r = goodService.insert(productManage);
				if (r == 1) {
					return respUtil.respOk("添加商品成功");
				} else {
					result = INSERT_FAIL;
				}
			} else {
				result = FEIGN_RETURN_FAIL;
			}
		}
		return respUtil.respFail(result);
	}

	@PostMapping(value = "/updateGood")
	public UnifyRespModel updateGood(@RequestParam ProductManage productManage) {
		log.info("updateGood入参：" + productManage);
		if (productManage.getId() == null) {
			return respUtil.respFail(ID_IS_NULL);
		} else {
			//删除图片
			if (productManage.getDeleteFlag() != null && productManage.getDeleteFlag() == 1) {
				String path = productManage.getIconUrl() + ","
						+ productManage.getDescriptionUrl() + ","
						+ productManage.getAlbumUrl();
				String deleteRes = commonFeignClient.deleteBatch(path);
				if (JSON.parseObject(deleteRes).getBoolean("success")) {
					log.info("删除图片成功，url= " + path);
				} else {
					log.error("删除图片失败，url= " + path);
				}
			}
			//图片删除成功与否不影响产品删除
			Date date = new Date();
			productManage.setUpdateTime(date);
			int r = goodService.updateByPrimaryKey(productManage);
			if (r == 1) {
				return respUtil.respOk("更新产品成功");
			} else {
				return respUtil.respFail(UPDATE_FAIL);
			}
		}
	}

	private ErrorEnum inputCheck(ProductManage productManage) {
		String productName = productManage.getProductName();
		Integer cateId = productManage.getCateId();
		BigDecimal marketPrice = productManage.getMarketPrice();
		BigDecimal salePrice = productManage.getSalePrice();
		Integer stockNum = productManage.getStockNum();

		if (productName.isEmpty()) {
			return NAME_IS_NULL;
		} else if (cateId == null) {
			return CATE_ID_IS_NULL;
		} else if (marketPrice == null) {
			return PRICE_IS_NULL;
		} else if (salePrice == null) {
			return PRICE_IS_NULL;
		} else if (stockNum == null) {
			return STOCK_NUM_IS_NULL;
		}
		return null;
	}

}
