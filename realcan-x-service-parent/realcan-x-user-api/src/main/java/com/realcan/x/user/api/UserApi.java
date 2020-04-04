package com.realcan.x.user.api;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */

import com.realcan.common.api.DtoResponse;
import com.realcan.x.user.dto.UserDto;

public interface UserApi {

    DtoResponse<UserDto> verifyPassword(String phone,String password);

    DtoResponse<String> sendSmsCaptcha(String phone, String ip, String imgCaptcha);

}
