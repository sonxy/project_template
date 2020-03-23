package com.realcan.x.user.dao;
import java.util.Date;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realcan.x.user.model.User;

/** *
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */
public interface UserDao extends BaseMapper<User> {

    User getOneByPhone(@Param("phone")String phone);

    int updateLoginTimeById(@Param("updatedLoginTime")Date updatedLoginTime,@Param("id")String id);



}