package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.VideoDailyVtype;

import java.util.List;

public interface VideoDailyVtypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoDailyVtype record);

    int insertBatch(List<VideoDailyVtype> list);

    int insertSelective(VideoDailyVtype record);

    VideoDailyVtype selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoDailyVtype record);

    int updateByPrimaryKey(VideoDailyVtype record);
}