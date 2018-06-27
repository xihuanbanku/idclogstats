package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.VideoDailyUAtype;

import java.util.List;

public interface VideoDailyUAtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoDailyUAtype record);

    int insertBatch(List<VideoDailyUAtype> list);

    int insertSelective(VideoDailyUAtype record);

    VideoDailyUAtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoDailyUAtype record);

    int updateByPrimaryKey(VideoDailyUAtype record);
}