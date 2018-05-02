package com.xmu.aiad.service.impl;


import com.xmu.aiad.dao.UserMapper;
import com.xmu.aiad.model.User;
import com.xmu.aiad.service.IUserLoginService;
import com.xmu.aiad.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

@Service("userLoginService")
@Transactional
public class UserLoginService implements IUserLoginService{

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;


    @Override
    public User login(long telephone,String password){
        password= MD5.getHashString(password);
        User user = userMapper.getUserByTelephoneAndPassword(telephone,password);

        System.out.println("telephone:" + telephone);
        System.out.println("password:" + password);
        System.out.println(user);

        return user;
    }

    @Override
    public boolean logout(String userId, Timestamp logoutTime){
        return true;
    }


}
