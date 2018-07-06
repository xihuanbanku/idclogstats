package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.VideoDailyHotClick;

import java.util.List;

public interface VideoDailyHotClickMapper {
    int deleteByPrimaryKey(Short id);

    int insert(VideoDailyHotClick record);

    int insertBatch(List<VideoDailyHotClick> list);

    int insertSelective(VideoDailyHotClick record);

    VideoDailyHotClick selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(VideoDailyHotClick record);

    int updateByPrimaryKey(VideoDailyHotClick record);
}