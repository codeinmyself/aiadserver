package com.xmu.aiad.service.impl;


import com.xmu.aiad.dao.UserMapper;
import com.xmu.aiad.model.User;
import com.xmu.aiad.service.IUserService;
import com.xmu.aiad.util.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;


@Service("userService")
@Transactional
public class UserService implements IUserService {

    private  Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id){
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public int updateUser(User user){
        int flag=0;
        try{
            flag=userMapper.updateByTelephone(user);
        }catch (Exception e){
            logger.info(e.toString());
        }
        return flag;
    }

    @Override
    public boolean addUser(long telephone, String password){
        User user = new User();
        user.setTelephone(telephone);
        user.setPassword(MD5.getHashString(password));
        //return um.insert_register(username,password);
        int flag = 0;
        try {
            flag = userMapper.insert(user);
        }catch(Exception e){
            logger.info(e.toString());
            flag = 0;
        }
        return flag!=0;
    }
}
