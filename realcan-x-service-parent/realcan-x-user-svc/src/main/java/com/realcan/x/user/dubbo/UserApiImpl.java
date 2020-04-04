package com.realcan.x.user.dubbo;

import com.realcan.common.api.DtoResponse;
import com.realcan.common.api.ResultCode;
import com.realcan.x.user.api.UserApi;
import com.realcan.x.user.dto.CreateUserRequest;
import com.realcan.x.user.dto.UserDto;
import com.realcan.x.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2019-11-09
 * @Version: 1.0
 */

@Service
@Slf4j
@Api(value = "用户接口")
public class UserApiImpl implements UserApi {

    @Autowired
    UserService userService;

    public DtoResponse<UserDto> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserDto userDto = userService.createUser(createUserRequest);
        DtoResponse<UserDto> dtoDtoResponse = new DtoResponse(userDto);
        return dtoDtoResponse;
    }

    public DtoResponse<UserDto> findUserByPhone(@RequestParam String phone) {

        UserDto userDto = userService.findUserByPhone(phone);
        DtoResponse<UserDto> dtoDtoResponse = new DtoResponse(userDto);
        return dtoDtoResponse;
    }

    @Override
    public DtoResponse<UserDto> verifyPassword(String phone,String password) {
        DtoResponse<UserDto> dtoResponse = new DtoResponse<>();
        UserDto userDto = userService.verifyPassword(phone, password);
        if (userDto == null) {
            dtoResponse.setCode(ResultCode.FAILURE);
            dtoResponse.setMessage("账号或者密码错误");
        } else {
            dtoResponse.setCode(ResultCode.SUCCESS);
            dtoResponse.setData(userDto);
        }
        return dtoResponse;
    }

    @Override
    public DtoResponse<String> sendSmsCaptcha(@RequestParam String phone, @RequestParam String ip, @RequestParam String imgCaptcha) {
        return null;
    }

}
