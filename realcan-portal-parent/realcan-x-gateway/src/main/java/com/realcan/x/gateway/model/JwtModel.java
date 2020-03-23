package com.realcan.x.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: fei.wu
 * @Email
 * @CreateDate: 2019-11-20
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtModel {

    private String userId;

}
