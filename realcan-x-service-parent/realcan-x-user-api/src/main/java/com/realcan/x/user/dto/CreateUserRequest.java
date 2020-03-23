package com.realcan.x.user.dto;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import com.realcan.common.validation.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2019-11-11
 * @Version: 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest implements Serializable {


    private static final long serialVersionUID = 1292034882590918683L;

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(name = "name", notes = "用户名", dataType = "String", required = true)
    private String name;

    @PhoneNumber(message = "手机号码有误")
    @ApiModelProperty(name = "phone", notes = "手机号", dataType = "String", required = true)
    private String phone;

    @AssertTrue(message = "请求参数不能为空")
    private boolean isValidRequest() {
        return StringUtils.hasText(name) || StringUtils.hasText(phone);
    }
}
