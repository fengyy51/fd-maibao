package com.binwang.frontOfBinwang.activity.dao;

import com.binwang.frontOfBinwang.activity.bean.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by owen on 17/6/26.
 */
@Repository
@Mapper
public interface IActDAO {
    List<NoticeModel> getNotice();
    ActModel getDetail(@Param("actId") long actId);
    RegItemModel getRegItem(@Param("actId") long actId);
    List<DetailItemModel> getDetailReg(@Param("username")String username);
    @Select("select content from activity_resources where type = 1 and activity_id = #{actId}")
    String getHtml(@Param("actId") long actId);

    int addFree(SubFreeParam subFreeParam);
    @Update("UPDATE f_user_act SET charge_type=#{chargeType},status= #{status},is_sign=#{isSign},reg_item=#{postReg},credential_code=#{credentialCode},mod_time=unix_timestamp(),add_time=unix_timestamp()WHERE open_id=#{openId} AND act_id=#{actId}")
    int updateFree(SubFreeParam subFreeParam);

    @Select("select count(id) from f_user_act where open_id = #{openId} and act_id = #{actId}")
    int isSignUp(@Param("openId") String openId, @Param("actId") long actId);

    @Delete("delete from f_user_act where open_id=#{openId} and act_id=#{actId}")
    int cancelReg(@Param("openId") String openId, @Param("actId") long actId);

    @Select("select a.status as status,a.credential_code as credCode,b.name as name, " +
            "b.begin as startActivityTime,b.end as endActivityTime,b.loc_about as address,a.is_sign as sign " +
            "from f_user_act a left join activity b on a.act_id = b.id where a.open_id = #{openId} " +
            "and a.act_id = #{actId}")
    CredModel getCredInfo(@Param("openId") String openId, @Param("actId") long actId);

    @Select("select id as actId,broad_cast_img as img,name,begin as startActivityTime," +
            "end as endActivityTime,loc_about as address,judge_charge as priceType " +
            "from activity where id in (select act_id from f_user_act where open_id = #{openId})")
    List<ListModel> getPersonalList(@Param("openId") String openId);


    @Select("select id as actId,broad_cast_img as img,name,begin as startActivityTime," +
            "end as endActivityTime,reg_dead_line as endSignTime,loc_about as address,judge_charge as priceType " +
            "from activity where reg_dead_line > now()")
    List<ListModel> getList();

}
