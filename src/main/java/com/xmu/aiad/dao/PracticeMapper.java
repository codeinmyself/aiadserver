package com.xmu.aiad.dao;

import com.xmu.aiad.model.Practice;

public interface PracticeMapper {
    int deleteByPrimaryKey(Long practiceId);

    int insert(Practice record);

    int insertSelective(Practice record);

    Practice selectByPrimaryKey(Long practiceId);

    int updateByPrimaryKeySelective(Practice record);

    int updateByPrimaryKey(Practice record);
}