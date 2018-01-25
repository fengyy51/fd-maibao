package com.binwang.frontOfBinwang.userCenter.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by yy on 17/6/26.
 */
@Repository
@Mapper
public interface IUserDAO {
    @Insert("insert ignore into f_user (open_id) values (#{openId})")
    int addUser(@Param("openId") String openId);
}
