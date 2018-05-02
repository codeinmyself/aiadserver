package com.xmu.aiad.dao;

import com.xmu.aiad.model.User;

import java.math.BigInteger;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserByTelephoneAndPassword(long telephone, String password);

    int updateByTelephone(User user);
}