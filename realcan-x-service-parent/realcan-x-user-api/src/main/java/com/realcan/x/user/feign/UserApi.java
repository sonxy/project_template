package com.realcan.x.user.feign;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", path = "/v1/user")
public class UserApi {


}
