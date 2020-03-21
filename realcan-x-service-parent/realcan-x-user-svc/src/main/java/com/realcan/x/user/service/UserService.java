package com.realcan.x.user.service;

import javax.annotation.Resource;

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

    private UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }
}

