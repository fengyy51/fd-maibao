package com.binwang.frontOfBinwang.collect.dao;

import com.binwang.frontOfBinwang.collect.bean.InitInfoModel;
import com.binwang.frontOfBinwang.collect.bean.PostParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by yy
 */
@Repository
@Mapper
public interface ICollectDAO {
    @Select("select name,description from collect where id = #{collectId}")
    InitInfoModel getInitInfo(@Param("collectId") int collectId);

    int add(PostParam postParam);

    @Select("select count(id) from f_collect where open_id = #{openId} and collect_id = #{collectId}")
    int judgeIsPost(@Param("openId") String openId, @Param("collectId") int collectId);

    @Select("select id from f_collect where open_id = #{openId} and collect_id = #{collectId} limit 1")
    int getIdByOpenId(@Param("collectId") int collectId,@Param("openId") String openId);

}
