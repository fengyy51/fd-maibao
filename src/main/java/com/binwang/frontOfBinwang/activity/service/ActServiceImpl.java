package com.binwang.frontOfBinwang.activity.service;

import com.binwang.frontOfBinwang.activity.bean.*;
import com.binwang.frontOfBinwang.activity.dao.IActDAO;
import com.binwang.frontOfBinwang.activity.redis.ActRAO;
import com.binwang.frontOfBinwang.common.redis.CommonRAO;
import com.binwang.frontOfBinwang.common.redis.CommonRAOImpl;
import com.binwang.frontOfBinwang.exception.ParamNotValidException;
import com.binwang.frontOfBinwang.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy on 17/6/26.
 */
@Service
public class ActServiceImpl implements ActService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActServiceImpl.class);
    @Resource
    private IActDAO actDAO;

    @Resource
    private ActRAO actRAO;

    @Resource
    private CommonRAO commonRAO;

    @Override
    public List<NoticeModel> getNotice(){
        LOGGER.info("notice");
        return actDAO.getNotice();
    }

    @Override
//    @Cacheable(value = "thirtyMinutes", keyGenerator = "wiselyKeyGenerator")
    public ActModel getDetail(long actId) {
        if (actId <= 0)
            throw new ParamNotValidException("actId不合法");
        return actDAO.getDetail(actId);
    }

    @Override
//    @Cacheable(value = "thirtyMinutes", keyGenerator = "wiselyKeyGenerator")
    public String getHtml(long actId) {
        if (actId <= 0)
            throw new ParamNotValidException("actId不合法");
        return actDAO.getHtml(actId);
    }

    @Override
    public int getSignedNum(long actId) {
        if (actId <= 0)
            throw new ParamNotValidException("actId不合法");
        int res = actRAO.getSignedNum(actId);
        if (res == -1) {
            if (actRAO.writeSignedNum(actId))
                return 0;
            else
                return -1;
        } else
            return res;
    }

    @Override
    public RegItemModel getRegItem(long actId){
        if (actId <= 0)
            throw new ParamNotValidException("actId不合法");
        return actDAO.getRegItem(actId);
    }
    @Override
    public List<DetailItemModel> getDetailReg(String username){
        if(username.equals("")){
            throw new ParamNotValidException("username为空");
        }
        return actDAO.getDetailReg(username);
    }
    @Override
    public Boolean isLockSign(long actId, int limit) {
        if (actId <= 0 || limit <= 0)
            throw new ParamNotValidException("参数不合法");
        try {
            Boolean flag = false;
            int cur = actRAO.getSignedNum(actId);
            System.out.println(cur);
            System.out.println(limit);
            if (cur >= limit)
                return false;
            String lock = "fbinwang158:act:lock:sign-num";
            if (commonRAO.setConcurrentLock(lock, 2000)) {
                if (actRAO.addSignedNum(actId, limit))
                    flag = true;
                else
                    flag = false;
            }
            commonRAO.deleteConcurrentLock(lock);
            return flag;
        } catch (InterruptedException e) {
            LOGGER.error("获取分布式锁失败,活动id为:" + actId);
            throw new RuntimeException("出错");
        }
    }

    @Override
    public Boolean isLockSignMinus(long actId) {
        try {
            Boolean flag = false;
            String lock = "fbinwang158:act:lock:sign-num";
            if (commonRAO.setConcurrentLock(lock, 2000)) {
                if (actRAO.minusSignedNum(actId))
                    flag = true;
                else
                    flag = false;
            }
            commonRAO.deleteConcurrentLock(lock);
            return flag;
        } catch (InterruptedException e) {
            LOGGER.error("减已报名人数失败,活动id为:" + actId);
            throw new RuntimeException("出错!");
        }
    }

    @Override
    @Transactional
    public Boolean addFree(SubFreeParam subFreeParam) {
        String openId = subFreeParam.getOpenId();
        long actId = subFreeParam.getActId();
        if (StringUtils.isEmpty(openId) || actId <= 0)
            throw new ParamNotValidException("参数不合法");
        if(isSignUp(openId, actId)==true){
            String credential = UUIDUtil.getShortUUID();
            subFreeParam.setCredentialCode(credential);
            System.out.print(credential);
            if(actDAO.updateFree(subFreeParam)>0) {
                return true;
            }
            else
                throw new RuntimeException("更新免费报名记录失败,openID："+subFreeParam.getOpenId() );
        }else {
            String credential = UUIDUtil.getShortUUID();
            subFreeParam.setCredentialCode(credential);
            System.out.print(credential);
            if (actDAO.addFree(subFreeParam) > 0)
                return true;
            else
                throw new RuntimeException("插入免费报名记录失败,openID：" + subFreeParam.getOpenId());
        }
    }

    @Override
    public Boolean isSignUp(String openId, long actId) {
        if (StringUtils.isEmpty(openId) || actId <= 0)
            throw new ParamNotValidException("参数不合法");
        int res = actDAO.isSignUp(openId, actId);
        if (res > 0)
            return true;
        else
            return false;
    }
    @Override
    @Transactional
    public Boolean cancelReg(long actId,String openId){
        if (StringUtils.isEmpty(openId) || actId <= 0)
            throw new ParamNotValidException("参数不合法");
        try {
            int res = actDAO.cancelReg(openId, actId);
            if (res > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            LOGGER.error("取消报名出错，openId为:" + openId + ",actId为:" + actId);
            throw new RuntimeException("出错");
        }
    }
    @Override
//    @Cacheable(value = "thirtyMinutes", keyGenerator = "wiselyKeyGenerator")
    public CredModel getCredDetail(String openId, long actId) {
        if (StringUtils.isEmpty(openId) || actId <= 0)
            throw new ParamNotValidException("参数不合法");
        try {
            CredModel res = actDAO.getCredInfo(openId, actId);
            return res;
        } catch (Exception e) {
            LOGGER.error("查询凭证详情出错，openId为:" + openId + ",actId为:" + actId);
            throw new RuntimeException("出错");
        }
    }

    @Override
    public List<ListModel> getPersonalList(String openId) {
        if (StringUtils.isEmpty(openId))
            throw new ParamNotValidException("参数不合法");
        return actDAO.getPersonalList(openId);
    }

    @Override
    public List<ListModel> getList() {
        LOGGER.info("list");
        return actDAO.getList();
    }
}
