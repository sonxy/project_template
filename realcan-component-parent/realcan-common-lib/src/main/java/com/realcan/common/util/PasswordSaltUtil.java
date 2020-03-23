package com.realcan.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: fei.wu
 * @Email:
 * @CreateDate: 2019-11-19
 * @Version: 1.0
 */
public class PasswordSaltUtil {
    /**
     * 生成32的随机盐值
     */
    public static String createSalt() {
        long num = System.currentTimeMillis() % 999999;
        return num + "";
    }

    /**
     * 加盐加密
     *
     * @param srcPwd    原始密码
     * @param saltValue 盐值
     */
    public static String saltPassword(String srcPwd, String saltValue) {
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(saltValue);
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, srcPwd, byteSalt, hashIterations);
        return result.toString();
    }


}
