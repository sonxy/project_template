package com.realcan.x.user.controller;

import javax.validation.Valid;

import com.realcan.common.api.DtoResponse;
import com.realcan.x.user.dto.CreateUserRequest;
import com.realcan.x.user.dto.UserDto;
import com.realcan.x.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

}
