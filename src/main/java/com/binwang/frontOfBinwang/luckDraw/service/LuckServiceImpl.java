package com.binwang.frontOfBinwang.luckDraw.service;

import com.binwang.frontOfBinwang.exception.ParamNotValidException;
import com.binwang.frontOfBinwang.luckDraw.bean.*;
import com.binwang.frontOfBinwang.luckDraw.dao.LuckDrawDAO;
import com.binwang.frontOfBinwang.luckDraw.redis.LuckRAO;
import com.binwang.frontOfBinwang.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yy on 17/7/20.
 */
@Service
public class LuckServiceImpl implements LuckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuckServiceImpl.class);

    @Resource
    private LuckDrawDAO luckDrawDAO;

    @Resource
    private LuckRAO luckRAO;

    @Value("${fbinwang.luckDraw.time}")
    private int time;

    class IntervalBean {
        private int front;
        private int end;
        private String type;

        public IntervalBean(int front, int end, String type) {
            this.front = front;
            this.end = end;
            this.type = type;
        }

        public int getFront() {
            return front;
        }

        public void setFront(int front) {
            this.front = front;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    @Override
    @Transactional
    public int getPrizeNum(long actId,String openId){return luckDrawDAO.getPrizeNum(actId,openId);}

    @Override
    @Transactional
    public PrizeParam getPrizeParam(long id){return luckDrawDAO.getPrizeParam(id);}

    @Override
    @Transactional
    public List<String> getPrizeInfo(String actName){
        List<String>res=luckDrawDAO.getPrizeInfo(actName);
        return res;
    }
    @Override
    public Map<String, Object> getWinInfo(String actName) {
        if (StringUtils.isEmpty(actName))
            throw new ParamNotValidException("参数不合法");
        try {
            List<WinCalDO> l = luckDrawDAO.getCalList(actName);
            int totalWeight = 0;
            Map<Long, IntervalBean> interval = new HashMap<>();
            int prev = 0;
            for (WinCalDO item : l) {
                int spacing = item.getNum() * item.getRatio();
                totalWeight += spacing;
                interval.put(item.getPrizeId(), new IntervalBean(prev, prev + spacing, item.getType()));
                prev = prev + spacing;
            }
            int randNum = new Random().nextInt(totalWeight);
            long prizeId = -1;
            String type="";
            for (Map.Entry<Long, IntervalBean> m : interval.entrySet()) {
                int front = m.getValue().getFront();
                int end = m.getValue().getEnd();
                if (randNum >= front && randNum < end) {
                    prizeId = m.getKey();
                    type = m.getValue().getType();
                    break;
                }
            }
            Map<String, Object> res = new HashMap<>();
            res.put("prizeId", prizeId);
            res.put("type", type);
            return res;
        } catch (Exception e) {
            LOGGER.error("计算转盘获奖逻辑出错");
            throw new RuntimeException("出错");
        }
    }

    @Override
    @Transactional
    public long handleWin(String openId, long prizeId, int collectId,String actName) {
        if (StringUtils.isEmpty(openId) || prizeId <= 0 || collectId <= 0||StringUtils.isEmpty(actName))
            throw new ParamNotValidException("参数不合法");
        //库存减一
        int updateRes = luckDrawDAO.updateNum(prizeId, actName);
        if (updateRes <= 0) {
            LOGGER.error("减获奖并发导致库存为0失败，collectId为" + collectId + ";actName为" + actName);
            throw new RuntimeException("出错");
        }
        //写入中奖信息至库
        String code = UUIDUtil.getShortUUID();
        WinUserDO winUserDO = new WinUserDO(openId, collectId,actName, prizeId, code, 0);
        int insertRes = luckDrawDAO.insertWinInfo(winUserDO);
        if (insertRes <= 0) {
            LOGGER.error("写用户获奖信息失败，openId为" + openId + ";prizeId为" + prizeId);
            throw new RuntimeException("出错");
        }
        //转盘次数减一
        luckRAO.decrDrawTime(openId);
        return winUserDO.getId();
    }

    @Override
    public Boolean judgeDraw(String openId) {
        return luckRAO.judgeDraw(openId, time);
    }
    //获取兑奖码
    @Override
    public String getVeriCode(long id){
        return luckDrawDAO.getVeriCode(id);
    }
    @Override
    public WinModel getDetail(long id) {
        if (id <= 0)
            throw new ParamNotValidException("参数不合法");
        return luckDrawDAO.getWinDetail(id);
    }

    @Override
    public List<WinModel> getList(String openId) {
        if (StringUtils.isEmpty(openId))
            throw new ParamNotValidException("参数不合法");
        return luckDrawDAO.getWinList(openId);
    }

    @Override
    @Transactional
    public Boolean handleUse(long id) {
        if (id <= 0)
            throw new ParamNotValidException("参数不合法");
        int res = luckDrawDAO.handleUse(id);
        if (res > 0)
            return true;
        else
            return false;
    }
}
