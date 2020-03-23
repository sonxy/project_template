package com.realcan.x.user.service;

import java.util.Date;

import javax.annotation.Resource;

import com.realcan.common.util.PasswordSaltUtil;
import com.realcan.x.user.dao.UserDao;
import com.realcan.x.user.dto.CreateUserRequest;
import com.realcan.x.user.dto.UserDto;
import com.realcan.x.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: fei.wu
 * @Email: fei.wu@rograndec.com
 * @CreateDate: 2020/3/21
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Resource
    ModelMapper modelMapper;

    @Resource
    UserDao userDao;

    public UserDto createUser(CreateUserRequest createUserRequest) {
        User map = modelMapper.map(createUserRequest, User.class);
        userDao.insert(map);
        return findUserByPhone(createUserRequest.getPhone());
    }

    public UserDto findUserByPhone(String phone) {
        return convertToDto(userDao.getOneByPhone(phone));
    }

    /**
     * 验证码密码正确
     *
     * @param phone
     * @param password
     * @return 成功返回用户对象, 失败返回 null
     */
    public UserDto verifyPassword(String phone, String password) {
        User user = userDao.getOneByPhone(phone);
        if (user.getPassword().equals(PasswordSaltUtil.saltPassword(password, user.getSalt()))) {
            //更新用户登录时间
            userDao.updateLoginTimeById(new Date(), user.getId());
            return convertToDto(user);
        }
        return null;
    }


    private UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }
}

