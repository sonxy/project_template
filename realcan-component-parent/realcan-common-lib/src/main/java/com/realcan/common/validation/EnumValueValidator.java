package com.realcan.common.validation;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.realcan.common.util.RoUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 枚举验证
 *
 * @Author: jian.mei
 * @CreateDate: 2019-11-11
 */
@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

	Class<?>[] cls; // 枚举类
	private String[] strValues;
	private int[] intValues;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		strValues = constraintAnnotation.strValues();
		intValues = constraintAnnotation.intValues();
		cls = constraintAnnotation.target();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(RoUtil.isEmpty(value)) {
			return true;
		}
		if (cls.length > 0) {
			for (Class<?> cl : cls) {
				try {
					if (cl.isEnum()) {
						Method method = cl.getMethod("values");
						EnumBase<?> enumBases[] = (EnumBase[]) method.invoke(null, null);
						for (EnumBase<?> enumBase : enumBases) {
							if (value.equals(enumBase.getCode())) {
								return true;
							}
						}
					}
				} catch (Exception e) {
					log.error("[EnumValueValidator][isValid]:", e);
				}
			}
		} else if (value instanceof String) {
			for (String s : strValues) {
				if (s.equals(value)) {
					return true;
				}
			}
		} else if (value instanceof Integer) {
			for (Integer s : intValues) {
				if (s == value) {
					return true;
				}
			}
		}
		return false;

	}

}
