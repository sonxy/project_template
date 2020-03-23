package com.realcan.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 条形码验证类
 *
 * @Author: wanfei.zhang
 * @CreateDate: 2019-11-11
 * @Version: 1.0
 */
public class BarcodeValidator implements ConstraintValidator<Barcode, String> {

    @Override
    public boolean isValid(String barcode, ConstraintValidatorContext context) {
        // can be null
        if (barcode == null) {
            return true;
        }
        return barcode != null && barcode.matches("[0-9]+")
                && (barcode.length() == 13);
    }
}
