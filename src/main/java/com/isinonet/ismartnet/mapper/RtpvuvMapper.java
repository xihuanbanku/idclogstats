package com.isinonet.ismartnet.mapper;

import com.isinonet.ismartnet.beans.Rtpvuv;

import java.util.List;

public interface RtpvuvMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rtpvuv record);

    int insertBatch(List<Rtpvuv> list);

    int insertSelective(Rtpvuv record);

    Rtpvuv selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rtpvuv record);

    int updateByPrimaryKey(Rtpvuv record);
}