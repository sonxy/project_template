package com.realcan.x.gateway.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realcan.common.api.DtoResponse;
import com.realcan.common.api.ResultCode;
import com.realcan.common.util.IpUtil;
import com.realcan.common.validation.PhoneNumber;
import com.realcan.x.gateway.model.JwtModel;
import com.realcan.x.gateway.util.JwtUtil;
import com.realcan.x.user.UserConstant;
import com.realcan.x.user.api.UserApi;
import com.realcan.x.user.dto.LoginRequest;
import com.realcan.x.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: fei.wu
 * @Email
 * @CreateDate: 2019-11-12
 * @Version: 1.0
 */

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private ObjectMapper objectMapper;

    @Value("${maiyata.jwt.effective-time}")
    private String effectiveTime;

    @Reference
    private UserApi userApi;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/login")
    public DtoResponse<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        String userId = null;
        DtoResponse<String> dto = new DtoResponse<>();

        DtoResponse<UserDto> user = userApi.verifyPassword(loginRequest.getPhone(), loginRequest.getPassword());
        if (ResultCode.SUCCESS.equals(user.getCode()) && user.getData() != null) {
            userId = user.getData().getId();

            JwtModel jwtModel = new JwtModel(user.getData().getId());
            String jwt = null;
            try {
                jwt = JwtUtil.generateToken(mapper.writeValueAsString(jwtModel), Integer.parseInt(effectiveTime));
            } catch (JsonProcessingException e) {
                log.error("[AuthController][login] 异常！参数" + e.getMessage(), e);
            }
            //生产 token 加入 redis 登出时删除
            stringRedisTemplate.opsForValue().set(jwt, userId, Integer.parseInt(effectiveTime), TimeUnit.MINUTES);
            dto.setData(jwt);
        } else {
            dto.setCode(ResultCode.UN_AUTHORIZED);
            dto.setMessage(UserConstant.LOGIN_ERROR_1);
        }
        return dto;
    }


    @RequestMapping(path = "/sendSmsCaptcha")
    DtoResponse<String> sendSmsCaptcha(ServerHttpRequest request, @RequestParam @PhoneNumber String phone, @RequestParam String imgCaptcha) {
        return userApi.sendSmsCaptcha(phone, IpUtil.getIpAddress(request), imgCaptcha);
    }


    @RequestMapping("/logout")
    public DtoResponse<String> logout(@RequestHeader(name = "Authorization") String token) {
        stringRedisTemplate.delete(token);
        DtoResponse<String> dto = new DtoResponse<>();
        dto.setData("success");
        return dto;
    }


}
