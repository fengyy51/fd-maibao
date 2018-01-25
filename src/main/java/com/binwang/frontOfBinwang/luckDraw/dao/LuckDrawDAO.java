package com.binwang.frontOfBinwang.luckDraw.dao;

import com.binwang.frontOfBinwang.luckDraw.bean.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yy on 17/7/20.
 */
@Repository
@Mapper
public interface LuckDrawDAO {

    @Select("select count(id) from f_user_prize where relation_id=#{actId} and open_id=#{openId} and "+
            "add_time >unix_timestamp(CURDATE()) and add_time<unix_timestamp(CURDATE())+86400")
    int getPrizeNum(@Param("actId")long actId,@Param("openId")String openId);
    @Select("select name,begin,end,prize_num as prizeNum,share_num as shareNum,prize_max_num as prizeMaxNum,prize_decoration as prizeDecoration,top_img as topImg,prizelist_img as prizelistImg,color from prize_params where id=#{id}")
    PrizeParam getPrizeParam(@Param("id")long id);

    @Select("select distinct type from prize where act_name=#{actName}")
    List<String> getPrizeInfo(@Param("actName")String actName);

    @Select("select id as prizeId,ratio,num,type from prize where act_name = #{actName} and num > 0")
    List<WinCalDO> getCalList(@Param("actName")String actName);

    @Update("update prize set num = num -1 where id = #{prizeId} and act_name = #{actName} and num > 0 ")
    int updateNum(@Param("prizeId") long prizeId, @Param("actName") String actName);


    int insertWinInfo(WinUserDO winUserDO);

    @Select("select code from prize_params where id=#{id}")
    String getVeriCode(@Param("id") long id);

    @Select("select b.relation_id as relationId,b.prize_id as prizeId,a.name as name,a.info as info,a.type as type,a.duijiang_time as duijiangTime," +
            "a.duijiang_loc as duijiangLoc,b.code as code,b.is_use as isUse from prize a " +
            "right join f_user_prize b on a.id = b.prize_id where b.id = #{id} limit 1")
    WinModel getWinDetail(@Param("id") long id);

    @Select("select b.id as id,b.prize_id as prizeId,a.name as name,a.info as info,a.type as type,a.duijiang_time as duijiangTime," +
            "a.duijiang_loc as duijiangLoc,b.code as code,b.is_use as isUse from prize a " +
            "right join f_user_prize b on a.id = b.prize_id where b.open_id = #{openId}")
    List<WinModel> getWinList(@Param("openId") String openId);

    @Update("update f_user_prize set is_use = 1 where id = #{id} and is_use = 0")
    int handleUse(@Param("id") long id);
}
