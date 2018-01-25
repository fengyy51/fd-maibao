package com.binwang.frontOfBinwang.collect.controller;

import com.binwang.frontOfBinwang.collect.bean.InitInfoModel;
import com.binwang.frontOfBinwang.collect.bean.PostParam;
import com.binwang.frontOfBinwang.collect.service.CollectService;
import com.binwang.frontOfBinwang.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yy on 17/11/11.
 */
@Controller
@RequestMapping("/collect")
public class CollectController {
//    使用指定类初始化日志对象  在日志输出的时候，可以打印出日志信息所在类
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectController.class);

//    按名称进行装配
    @Resource
    private CollectService collectService;

    @RequestMapping("/get-init-info")
    @ResponseBody
    public Object getInitInfo(@RequestParam("collectId") int collectId) {
        try {
            InitInfoModel res = collectService.getInitInfo(collectId);
            return ResponseUtil.okJSON(res);
        } catch (Exception e) {
            LOGGER.error("获取collect初始数据出错，collectId为：" + collectId);
            return ResponseUtil.errorJSON("获取基本信息失败");
        }
    }

    @RequestMapping("/is-has-post")
    @ResponseBody
    public Object isHasPost(@RequestParam("collectId") int collectId,
                            @RequestParam("openId") String openId) {
        try {
            Boolean res = collectService.judgeIsPost(openId, collectId);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            LOGGER.error("判断是否提交作品失败，openId为:" + openId);
            return ResponseUtil.errorJSON("判断是否重复提交失败");
        }
    }


    @RequestMapping(value = "/post-info", method = RequestMethod.POST)
    @ResponseBody
    public Object postInfo(PostParam postParam) {
        try {
            long id = collectService.postInfo(postParam);
            Map<String, Long> m = new HashMap<>();
            m.put("id", id);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("作品提交失败");
        }
    }

    @RequestMapping("/personal-submit-result")
    @ResponseBody
    public Object submitRes(@RequestParam("collectId") int collectId,
                            @RequestParam("openId") String openId) {
        try {
            Map<String, Integer> m = new HashMap<>();
            m.put("id", collectService.getIdByOpenId(openId, collectId));
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            LOGGER.error("获取作品征集结果失败，openId为：" + openId);
            return ResponseUtil.errorJSON("获取作品征集结果失败");
        }
    }


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "hello collect";
    }
}
