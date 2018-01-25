package com.binwang.frontOfBinwang.collect.service;

import com.binwang.frontOfBinwang.collect.bean.InitInfoModel;
import com.binwang.frontOfBinwang.collect.bean.PostParam;
import com.binwang.frontOfBinwang.collect.dao.ICollectDAO;
import com.binwang.frontOfBinwang.exception.ParamNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yy on 17/11/12.
 */
@Service
public class CollectServiceImpl implements CollectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectServiceImpl.class);

    @Resource
    private ICollectDAO collectDAO;

    @Override
    public InitInfoModel getInitInfo(int collectId) {
        if (collectId <= 0)
            throw new ParamNotValidException("参数不合法");
        return collectDAO.getInitInfo(collectId);
    }

    @Override
    @Transactional
    public long postInfo(PostParam postParam) {
        //TODO:字段校验
        int res = collectDAO.add(postParam);
        if (res > 0)
            return postParam.getId();
        else {
            LOGGER.error("添加征集作品失败，openId为:" + postParam.getOpenId());
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    public Boolean judgeIsPost(String openId, int collectId) {
        if (StringUtils.isEmpty(openId) || collectId <= 0)
            throw new ParamNotValidException("参数不合法");
        int res = collectDAO.judgeIsPost(openId, collectId);
        if (res > 0)
            return true;
        else
            return false;
    }

    @Override
//    @Cacheable(value = "permanent", keyGenerator = "wiselyKeyGenerator")
    public int getIdByOpenId(String openId, int collectId) {
        if (StringUtils.isEmpty(openId) || collectId <= 0)
            throw new ParamNotValidException("参数不合法");
        return collectDAO.getIdByOpenId(collectId, openId);
    }
}
