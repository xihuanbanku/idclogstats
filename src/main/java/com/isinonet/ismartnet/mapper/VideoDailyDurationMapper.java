package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.VideoDailyDuration;

import java.util.List;

public interface VideoDailyDurationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoDailyDuration record);

    int insertBatch(List<VideoDailyDuration> list);

    int insertSelective(VideoDailyDuration record);

    VideoDailyDuration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoDailyDuration record);

    int updateByPrimaryKey(VideoDailyDuration record);
}