package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.UAType;

import java.util.List;

public interface UATypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UAType record);

    List<UAType> findAll();

    int insertSelective(UAType record);

    UAType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UAType record);

    int updateByPrimaryKey(UAType record);
}