package com.xmu.aiad.dao;

import com.xmu.aiad.model.Authorize;

public interface AuthorizeMapper {
    int deleteByPrimaryKey(Long authId);

    int insert(Authorize record);

    int insertSelective(Authorize record);

    Authorize selectByPrimaryKey(Long authId);

    int updateByPrimaryKeySelective(Authorize record);

    int updateByPrimaryKey(Authorize record);
}