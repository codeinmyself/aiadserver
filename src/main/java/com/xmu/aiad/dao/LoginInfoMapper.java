package com.xmu.aiad.dao;

import com.xmu.aiad.model.LoginInfo;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(Long loginId);

    int insert(LoginInfo record);

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long loginId);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByPrimaryKey(LoginInfo record);
}