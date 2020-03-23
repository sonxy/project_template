package com.realcan.x.user.feign;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */

import com.realcan.common.api.DtoResponse;
import com.realcan.x.user.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-svc", path = "/v1/user")
public interface UserApi {

    @RequestMapping(path = "/verifyPassword")
    DtoResponse<UserDto> verifyPassword(@RequestParam String phone, @RequestParam String password);

    @RequestMapping(path = "/sendSmsCaptcha")
    DtoResponse<String> sendSmsCaptcha(@RequestParam String phone, @RequestParam String ip, @RequestParam String imgCaptcha);

}
