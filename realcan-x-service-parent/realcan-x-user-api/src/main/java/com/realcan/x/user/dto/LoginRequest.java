package com.realcan.x.user.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.realcan.common.validation.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2019-11-29
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    private static final long   serialVersionUID = 6802286295256614212L;
    @NotEmpty(message = "手机号码不能为空")
    @PhoneNumber(message = "手机号码有误")
    @ApiModelProperty(name = "phone", notes = "手机号", dataType = "String", required = true)
    private              String phone;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

}
