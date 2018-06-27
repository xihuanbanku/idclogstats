package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.StaticUAtype;

import java.util.List;

public interface StaticUAtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaticUAtype record);

    int insertBatch(List<StaticUAtype> list);

    int insertSelective(StaticUAtype record);

    StaticUAtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaticUAtype record);

    int updateByPrimaryKey(StaticUAtype record);
}