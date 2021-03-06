package com.iason.account.data.constant;

/**
 * Created by Iason on 2018/9/12.
 */
public enum ErrorEnum {

	//入参错误
	PARAM_ERROR_DEFAULT("001", "入参错误"),
	JUMP_LINK_IS_NULL("002", "跳转链接不能为空"),
	PARENT_ID_IS_NULL("003", "父分类id不能为空"),
	TYPE_IS_NULL("004", "类型不能为空"),
	PRIORITY_IS_NULL("005", "优先级不能为空"),
	NAME_IS_NULL("006", "名称不能为空"),
	PARAM_PARSE_ERROR("007", "入参解析错误"),
	ID_IS_NULL("008", "id不能为空"),
	URL_IS_NULL("009", "url不能为空"),
	CATE_ID_IS_NULL("010", "分类id不能为空"),
	PRICE_IS_NULL("011", "价格不能为空"),
	STOCK_NUM_IS_NULL("012", "库存不能为空"),
	SALE_NUM_IS_NULL("013", "销量不能为空"),

	//数据库错误
	INSERT_FAIL("101", "插入数据失败"),
	UPDATE_FAIL("102" , "更新数据失败"),
	DELETE_FAIL("103" , "删除数据失败"),
	DATA_NON_EXISTENT("104" , "数据不存在"),

	//系统错误
	FEIGN_RETURN_FAIL("901", "feign调用返回失败")
	;
	private String errorCode;

	private String errorMsg;

	ErrorEnum(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
