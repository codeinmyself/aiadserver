package com.xmu.aiad.dao;

import com.xmu.aiad.model.OperationLog;

public interface OperationLogMapper {
    int deleteByPrimaryKey(Long operationId);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(Long operationId);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKey(OperationLog record);
}