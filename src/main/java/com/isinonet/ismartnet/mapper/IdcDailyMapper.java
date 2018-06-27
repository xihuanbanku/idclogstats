package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.IdcDaily;
import com.isinonet.ismartnet.beans.IdcDailyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IdcDailyMapper {
    int countByExample(IdcDailyExample example);

    int deleteByExample(IdcDailyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IdcDaily record);

    int insertBatch(List<IdcDaily> list);

    int insertSelective(IdcDaily record);

    List<IdcDaily> selectByExample(IdcDailyExample example);

    IdcDaily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IdcDaily record, @Param("example") IdcDailyExample example);

    int updateByExample(@Param("record") IdcDaily record, @Param("example") IdcDailyExample example);

    int updateByPrimaryKeySelective(IdcDaily record);

    int updateByPrimaryKey(IdcDaily record);
}