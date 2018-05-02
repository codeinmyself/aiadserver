package com.xmu.aiad.dao;

import com.xmu.aiad.model.Material;

public interface MaterialMapper {
    int deleteByPrimaryKey(Long materialId);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Long materialId);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKey(Material record);
}