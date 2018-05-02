package com.xmu.aiad.dao;

import com.xmu.aiad.model.UserExt;

public interface UserExtMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserExt record);

    int insertSelective(UserExt record);

    UserExt selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserExt record);

    int updateByPrimaryKey(UserExt record);
}