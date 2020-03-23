package com.realcan.common.util;

import com.realcan.common.api.ResultCode;
import com.realcan.common.error.ServiceException;

/**
 * 
 * @Filename: BeanUtil.java
 * @Version: 1.0
 * @Author: jian.mei
 *
 */
public final class BeanUtil {

	/**
	 * 方法中参数校验不通过调用此方法
	 */
	public static void parameterValidationError() {
		throw new ServiceException(ResultCode.PARAM_VALID_ERROR,
				Thread.currentThread().getStackTrace()[2].getFileName() + "."
						+ Thread.currentThread().getStackTrace()[2].getMethodName() + " line:"
						+ Thread.currentThread().getStackTrace()[2].getLineNumber() + " Parameter Validation Error");
	}

}