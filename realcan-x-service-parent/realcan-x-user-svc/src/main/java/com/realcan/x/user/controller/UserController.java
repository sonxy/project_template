package com.realcan.x.user.controller;

import javax.validation.Valid;

import com.realcan.common.api.DtoResponse;
import com.realcan.common.api.ResultCode;
import com.realcan.x.user.dto.CreateUserRequest;
import com.realcan.x.user.dto.UserDto;
import com.realcan.x.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2019-11-09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/user")
@Validated
@Slf4j
@Api(value = "用户接口")
public class UserController {


    @Autowired
    UserService userService;

    @ApiOperation(value = "创建用户", notes = "创建用户", httpMethod = "POST", response = String.class)
    @PostMapping(path = "/create")
    public DtoResponse<UserDto> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserDto userDto = userService.createUser(createUserRequest);
        DtoResponse<UserDto> dtoDtoResponse = new DtoResponse(userDto);
        return dtoDtoResponse;
    }

    @ApiOperation(value = "查询用户", notes = "根据手机号查询", httpMethod = "GET", response = String.class)
    @RequestMapping(path = "/findUserByPhone")
    public DtoResponse<UserDto> findUserByPhone(@RequestParam String phone) {

        UserDto userDto = userService.findUserByPhone(phone);
        DtoResponse<UserDto> dtoDtoResponse = new DtoResponse(userDto);
        return dtoDtoResponse;
    }

    @ApiOperation(value = "验证密码", notes = "验证密码", httpMethod = "GET")
    @GetMapping(path = "/verifyPassword")
    DtoResponse<UserDto> verifyPassword(@RequestParam String phone, @RequestParam String password) {
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

    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码", httpMethod = "GET")
    @GetMapping(path = "/sendSmsCaptcha")
    public DtoResponse<String> sendSmsCaptcha(@RequestParam String phone, @RequestParam String ip, @RequestParam String imgCaptcha) {

        return null;
    }

}
