package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.Website;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WebsiteMapper {
    int deleteByPrimaryKey(Integer websiteId);

    int insert(Website record);

    int insertBatch(List<Website> record);

    int insertSelective(Website record);

    Website selectByPrimaryKey(Integer websiteId);

    List<Website> findAll();

    int updateByPrimaryKeySelective(Website record);

    int updateByPrimaryKey(Website record);
}