package com.realcan.x.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */
@ApiModel(value = "com-realcan-x-user-model-User")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty(value = "")
    private String id;

    @TableField(value = "phone")
    @ApiModelProperty(value = "")
    private String phone;

    @TableField(value = "password")
    @ApiModelProperty(value = "")
    private String password;

    @TableField(value = "salt")
    @ApiModelProperty(value = "")
    private String salt;

    @TableField(value = "real_name")
    @ApiModelProperty(value = "")
    private String realName;

    @TableField(value = "login_time")
    @ApiModelProperty(value = "")
    private Date loginTime;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PHONE = "phone";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SALT = "salt";

    public static final String COL_REAL_NAME = "real_name";

    public static final String COL_LOGIN_TIME = "login_time";

    public static final String COL_CREATE_TIME = "create_time";

}