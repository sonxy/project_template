package com.realcan.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 枚举验证，自定义注解
 *
 * @Author: jian.mei
 * @CreateDate: 2019-11-11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface  EnumValue {
 
	//默认错误消息
    String message() default "Parameter Validation Error";
    
    Class<?>[] target() default {};
    
    String[] strValues() default {};
    
    int[] intValues() default {};
 
    //分组
    Class<?>[] groups() default {};
 
    //负载
    Class<? extends Payload>[] payload() default {};
 
    //指定多个时使用
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
    	EnumValue[] value();
    }
}
