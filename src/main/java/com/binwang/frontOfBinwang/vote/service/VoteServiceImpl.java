package com.binwang.frontOfBinwang.vote.service;


import com.binwang.frontOfBinwang.utils.HandleDateUtil;
import com.binwang.frontOfBinwang.vote.bean.*;
import com.binwang.frontOfBinwang.vote.dao.VoteDao;
import com.binwang.frontOfBinwang.vote.redis.VoteRAO;
import com.binwang.frontOfBinwang.utils.AddressUtils;
import com.sun.jndi.cosnaming.IiopUrl;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 * Created by yy .
 */
@Service
public class VoteServiceImpl implements VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);

    @Resource
    private VoteDao voteDAO;

    @Resource
    private VoteRAO voteRAO;

    private AddressUtils addressUtils;
    //写记录线程 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    private final ExecutorService writeVoteRecordPool = Executors.newFixedThreadPool(8);

    private class WriteVoteRecord implements Runnable {
        private VoteRecord voteRecord;

        public WriteVoteRecord(VoteRecord voteRecord) {
            this.voteRecord = voteRecord;
        }

        @Override
        //插入到f_user_vote中
        public void run() {
            voteDAO.insertUserVote(voteRecord);
        }
//        public void run() {
//            voteDAO.insertVoteRecord(voteRecord);
//        }
    }

    @Override
    public int add(long id, String str) {
        return voteDAO.addProductFirst(id, str);
    }

    @Override
    @Transactional
    public VoteParam getVoteParam(long actId){return voteDAO.getVoteParam(actId);}
    @Override
    @Transactional
    public int getVoteNum(long actId,String openId){
        System.out.println(actId);
        return voteRAO.getVoteNum(openId,actId);
//        return voteDAO.getVoteNum(actId,openId);
    }
    @Override
    @Transactional
    public long getVoteIndex(long actId){
        return voteDAO.getVoteIndex(actId);
    }
//    @Override
//    public List<ProductInfo> getProductInfo(long actId) {
//        return voteDAO.getProductInfo(actId);
//    }
    //麦宝修改
//    @Override
//    public List<MaiBaoInfo> getProductInfo(long actId) {
//        return voteDAO.getProductInfo(actId);
//    }
    @Override
    public List<ProCommonInfo> getProductInfo(long actId) {
        return voteDAO.getProductInfo(actId);
    }

    @Override
    @Transactional
    public Map<String, Object> postInfo(String str, long actId,String ip,String address,String userAgent) {
        String[] s = str.split("@@@");
        long jsCurTime = Long.parseLong(s[1]);
        String openId = s[2];
        Long countShareFriend=Long.parseLong(s[3]);
        Long countShareCircle=Long.parseLong(s[4]);
        Map<String, Object> m = new HashMap<>();
        //授权每日限制
        if(StringUtils.isEmpty(openId)){
            m.put("result",false);
            m.put("msg","投票失败，微信授权码无效");
            return m;
        }
        //ip限制开始
        VoteParam voteParam=voteDAO.getVoteParam(actId);
        int voteMaxNum=voteParam.getVoteMaxNum();
        System.out.println(voteMaxNum+"maxnum");
        System.out.println(ip);
        if(!voteRAO.judgeIpVote(ip,voteMaxNum,actId)) {
            m.put("result", false);
            m.put("msg", "已投票");
            LOGGER.info("ip"+m.toString());
            return m;
        }
        LOGGER.info("openid限制开始");
        //ip限制结束
        //openId限制开始
        if(!voteRAO.judgeIsVote(openId,voteMaxNum,actId)) {
            m.put("result", false);
            m.put("msg", "已投票");
            LOGGER.info("openid"+m.toString());
            return m;
        }
        //openid限制结束
//        if(!voteRAO.judgeAuthOpenId(openId,actId)){
//            m.put("result", false);
//            m.put("msg", "openid不是有效值");
//            return m;
//        }
        LOGGER.info("判断时间开始");
        if (jsCurTime < HandleDateUtil.getTimesmorning() || jsCurTime > HandleDateUtil.getTimesnight()) {
            m.put("result", false);
            m.put("msg", "请确保当日投票");
            return m;
        }

        LOGGER.info("判断时间结束");
        VoteRecord vr = new VoteRecord(actId,countShareFriend,countShareCircle,ip,"," + s[0],userAgent,address,openId);
        System.out.println(address);
        writeVoteRecordPool.execute(new WriteVoteRecord(vr));
        System.out.println("投票分别增加个数");
        String[] a = s[0].split(",");
        LOGGER.info(a.toString());
        List<String> b = java.util.Arrays.asList(a);
        List<Integer> list = new ArrayList<Integer>();
        for (Iterator<String> it = b.iterator(); it.hasNext(); ) {
            list.add(Integer.parseInt(it.next()));
        }
        for (Integer i : list) {
            LOGGER.info("i"+String.valueOf(i));
            LOGGER.info("actId"+String.valueOf(actId));
            int res = voteDAO.setVoteNum(i,actId);
            LOGGER.info("res="+String.valueOf(res));
            if (res <= 0) {
                LOGGER.error("投票计数出错，itemId：" + i);
                throw new RuntimeException("出错");
            }
        }
        LOGGER.info("给openid增加票数");
        voteRAO.addVoteTime(openId,actId);
        LOGGER.info("给ip增加票数");
        voteRAO.addVoteNum(ip,actId);
        m.put("result", true);
        m.put("msg", "投票成功");
        LOGGER.info(m.toString());
        return m;
    }

    @Override
    @Transactional
    public List<MaiBaoVoteInfo> getVoteInfo( long actId) {
        List<MaiBaoVoteInfo> l = voteDAO.getVoteInfo(actId);
//        List<VoteInfo> newL = new ArrayList<>();
//        for (VoteInfo v : l) {
//            if (StringUtils.isEmpty(v.getProductFirst())) {
//                v.setProductFirst(v.getProductImgUrls().split("@@@")[0]);
//            }
//            newL.add(v);
//        }
        return l;
    }
}
