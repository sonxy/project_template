package com.realcan.x.gateway.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: fei.wu
 * @Email
 * @CreateDate: 2019-11-12
 * @Version: 1.0
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥 -- 根据实际项目，这里可以做成配置
     */
    public static final String secret = "la23h4kl2j34lk23j4l2j34lkj23nsadfiaiosdus090as9c098sfjo23jlk2n0u";

    /**
     * 生成jwt token
     */
    public static String generateToken(String userId,int effectiveTime) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + effectiveTime * 60 * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
