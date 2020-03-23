package com.realcan.x.gateway.filter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realcan.common.api.BaseResponse;
import com.realcan.common.api.ResultCode;
import com.realcan.common.util.IpUtil;
import com.realcan.x.gateway.model.JwtModel;
import com.realcan.x.gateway.util.JwtUtil;
import com.realcan.x.user.UserConstant;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: fei.wu
 * @Email
 * @CreateDate: 2019-11-12
 * @Version: 1.0
 */
@Component
//读取 yml 文件下的 org.my.jwt
@ConfigurationProperties("maiyata.jwt")
@Setter
@Getter
@Slf4j
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            String url = exchange.getRequest().getURI().getPath();
            //跳过不需要认证的 url
            if (null != skipAuthUrls && Arrays.asList(skipAuthUrls).contains(url)) {
                return chain.filter(exchange);
            }

            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            ServerHttpResponse resp = exchange.getResponse();
            if (StringUtils.isBlank(token)) {
                return authError(resp, "未登录");
            } else if (StringUtils.isBlank(stringRedisTemplate.opsForValue().get(token))) {
                return authError(resp, "未登录");
            } else {
                Claims claimByToken = JwtUtil.getClaimByToken(token);
                if (claimByToken == null || JwtUtil.isTokenExpired(claimByToken.getExpiration())) {
                    return authError(resp, "认证失败");
                }

                JwtModel jwtModel = objectMapper.readValue(claimByToken.getSubject(), JwtModel.class);
                //校验权限
                URI uri = exchange.getRequest().getURI();


                String ipAddress = IpUtil.getIpAddress(exchange.getRequest());
                ServerHttpRequest pass = exchange.getRequest().mutate()
                        .header(UserConstant.USER_IP, ipAddress)
                        .header(UserConstant.CURRENT_USER_ID, jwtModel.getUserId()).build();
                ServerWebExchange build = exchange.mutate().request(pass).build();
                return chain.filter(build);
            }
        } catch (Exception e) {
            log.error("[JwtTokenFilter][filter] 异常！" + e.getMessage(), e);
            return authError(exchange.getResponse(), "系统异常");
        }

    }

    private Mono<Void> authError(ServerHttpResponse resp, String message) {
        //没有token
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        BaseResponse ba = BaseResponse.builder().code(ResultCode.UN_AUTHORIZED).message(message).build();
        String resultStr = "";
        try {
            resultStr = objectMapper.writeValueAsString(ba);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer buffer = resp.bufferFactory().wrap(resultStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
