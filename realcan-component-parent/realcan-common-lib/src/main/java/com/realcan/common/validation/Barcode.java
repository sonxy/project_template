package com.realcan.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

/**
 * 条形码验证类
 *
 * @Author: fei.wu
 * @Email:
 * @CreateDate: 2019-11-11
 * @Version: 1.0
 */
@Documented
@Constraint(validatedBy = BarcodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Barcode {
    String message() default "条形码格式不正确，为13位数字";

    Class[] groups() default {};

    Class[] payload() default {};
}
