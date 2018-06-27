package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.VideoDailyGender;

import java.util.List;

public interface VideoDailyGenderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoDailyGender record);

    int insertBatch(List<VideoDailyGender> list);

    int insertSelective(VideoDailyGender record);

    VideoDailyGender selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoDailyGender record);

    int updateByPrimaryKey(VideoDailyGender record);
}