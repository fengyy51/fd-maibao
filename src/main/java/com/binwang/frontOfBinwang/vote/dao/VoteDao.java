package com.binwang.frontOfBinwang.vote.dao;

import com.binwang.frontOfBinwang.vote.bean.VoteInfo;
import com.binwang.frontOfBinwang.vote.bean.ProductInfo;
import com.binwang.frontOfBinwang.vote.bean.VoteParam;
import com.binwang.frontOfBinwang.vote.bean.VoteRecord;
import org.apache.ibatis.annotations.*;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by yy on 2017/7/13.
 */
@Repository
@Mapper
public interface VoteDao {
    @Select("select count(id) from f_user_vote where act_id=#{actId} and open_id=#{openId} and "+
    "addtime >unix_timestamp(CURDATE()) and addtime<unix_timestamp(CURDATE())+86400")
    int getVoteNum(@Param("actId")long actId,@Param("openId")String openId);
    @Select("select act_name as actName,begin,end,pro_num as proNum,vote_num as voteNum,share_num as shareNum,vote_max_num as voteMaxNum,vote_decoration as voteDecoration,pro_approved as proApproved from vote_params where act_id=#{actId}")
    VoteParam getVoteParam(@Param("actId")long actId);
    @Select("select a.vote_num as voteNum,a.item_id as itemId,b.reg_item as productInfo from f_vote a,f_user_act b where  a.item_id=b.id and a.act_id=#{actId} and b.is_ok = 1 order by a.vote_num DESC")
    List<VoteInfo> getVoteInfo(@Param("actId")long actId);

    @Select("select id,reg_item as productInfo from f_user_act where act_id=#{actId} and is_ok=1")
    List<ProductInfo> getProductInfo(@Param("actId")long actId);

    @Insert("INSERT INTO f_vote (item_id,act_id,vote_num) VALUES(#{itemId},#{actId},1) ON DUPLICATE KEY UPDATE " +
            "vote_num=vote_num+1")
    int setVoteNum(@Param("itemId") int itemId,@Param("actId")long actId);

    @Update("UPDATE f_collect SET product_first=#{productFirst} WHERE id=#{id}")
    int addProductFirst(@Param("id") long id, @Param("productFirst") String productFirst);

    @Insert("INSERT INTO f_user_vote (ip,addtime,record,user_agent) VALUES(#{ip},unix_timestamp(),#{record},#{userAgent})")
    void insertVoteRecord(VoteRecord voteRecord);
    @Insert("INSERT INTO f_user_vote (act_id,open_id,ip,addtime,record,user_agent) VALUES(#{actId},#{openId},#{ip},unix_timestamp(),#{record},#{userAgent})")
    void insertUserVote(VoteRecord voteRecord);
}
