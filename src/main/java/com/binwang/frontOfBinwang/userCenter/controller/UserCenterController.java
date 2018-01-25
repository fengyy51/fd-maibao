package com.binwang.frontOfBinwang.userCenter.controller;

import com.binwang.frontOfBinwang.userCenter.service.UserService;
import com.binwang.frontOfBinwang.utils.ResponseUtil;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy on 17/6/22.
 */
@Controller
@RequestMapping("/user")
public class UserCenterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterController.class);

    @Resource
    private UserService userService;

    @Value("${fbinwang.user.auth.tokens}")
    private int authToken;

    @RequestMapping("/do-auth")
    @ResponseBody
    public Object doAuth(@RequestParam("code") String code) {
        RateLimiter limiter = RateLimiter.create(authToken);
        if (!limiter.tryAcquire(1, TimeUnit.SECONDS)) {
            return ResponseUtil.errorJSON("请稍后再试");
        } else {
            try {
                String openId = userService.doAuthGetOpenId(code);
                Map<String, String> m = new HashMap<>();
                m.put("openId", openId);
                return ResponseUtil.okJSON(m);
            } catch (Exception e) {
                return ResponseUtil.errorJSON("授权失败");
            }
        }
    }

    @RequestMapping("/get-info")
    @ResponseBody
    public Object getInfo(@RequestParam("openId") String openId) {
        try {
            Map<String, Object> m = userService.getInfoByOpenId(openId);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取用户信息失败");
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "hello world";
    }
}
