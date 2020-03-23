package com.realcan.x.user.dto;

import java.io.Serializable;

import com.realcan.common.api.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2019-11-11
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseResponse implements Serializable{

    private static final long serialVersionUID = 4897288355949239654L;

    private String id;
    private String name;
    private String phone;

}
