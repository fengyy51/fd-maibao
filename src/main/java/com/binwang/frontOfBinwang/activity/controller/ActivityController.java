package com.binwang.frontOfBinwang.activity.controller;

import com.binwang.frontOfBinwang.activity.bean.*;
import com.binwang.frontOfBinwang.activity.service.ActService;
import com.binwang.frontOfBinwang.utils.ResponseUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yy on 17/6/26.
 */
@Controller
@RequestMapping("/act")
public class ActivityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Resource
    private ActService actService;
    //获取公告通知
    @RequestMapping("/notice")
    @ResponseBody
    public Object getNotice(){
        try {
            List<NoticeModel> list = actService.getNotice();
            return ResponseUtil.okJSON(list);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取最新的五个公告通知出错");
        }
    }
    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(@RequestParam("id") long actId) {
        try {
            ActModel a = actService.getDetail(actId);
            return ResponseUtil.okJSON(a);
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + "actId为:" + actId);
            return ResponseUtil.errorJSON("获取活动详情出错");
        }
    }

    @RequestMapping("/detail-html")
    @ResponseBody
    public String getHtml(@RequestParam("id") long actId) {
        try {
            return actService.getHtml(actId);
        } catch (Exception e) {
            return "出错";
        }
    }

    @RequestMapping("/detail-signed-num")
    @ResponseBody
    public Object getSignedNum(@RequestParam("id") long actId) {
        try {
            Map<String, Integer> m = new HashMap<>();
            int res = actService.getSignedNum(actId);
            if (res == -1)
                return ResponseUtil.errorJSON("获取报名人数出错");
            m.put("signedNumber", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取报名人数出错");
        }
    }

    @RequestMapping("/get-reg-item")
    @ResponseBody
    public Object getRegItem(@RequestParam("actId") long actId) {
        try {
            RegItemModel a = actService.getRegItem(actId);
            return ResponseUtil.okJSON(a);
        } catch (Exception e) {
            LOGGER.error(e.getMessage() + "actId为:" + actId);
            return ResponseUtil.errorJSON("获取活动报名项出错");
        }
    }
    @RequestMapping("/get-detail-reg")
    @ResponseBody
    public Object getDetailReg(@RequestParam("username") String username){
        try{
            List<DetailItemModel> a=actService.getDetailReg(username);
            return ResponseUtil.okJSON(a);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseUtil.errorJSON("获取自定义报名项详细内容出错");
        }
    }

    @RequestMapping("/judge-signed-num")
    @ResponseBody
    public Object judgeSignedNum(@RequestParam("limit") int limit,
                                 @RequestParam("actId") long actId) {
        try {
            Boolean res = actService.isLockSign(actId, limit);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("判断是否超出限额出错");
        }
    }

    @RequestMapping("/submit/free")
    @ResponseBody
    public Object submitFree(SubFreeParam subFreeParam) {
        try {
            Boolean result= actService.addFree(subFreeParam);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", result);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            actService.isLockSignMinus(subFreeParam.getActId());
            return ResponseUtil.errorJSON("活动报名失败");
        }
    }
    @RequestMapping("/cancel-reg")
    @ResponseBody
    public Object CancelReg(@Param("actId")long actId,@Param("openId")String openId) {
        try {
            Boolean result= actService.cancelReg(actId,openId);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", result);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("取消报名失败");
        }
    }
    @RequestMapping("/submit/not-free")
    @ResponseBody
    public Object submitNotFree() {
        return null;
    }

    @RequestMapping("/is-sign-up")
    @ResponseBody
    public Object isSignUp(@RequestParam("openId") String openId,
                           @RequestParam("actId") long actId) {
        try {
            Boolean res = actService.isSignUp(openId, actId);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("判断是否报名接口出错");
        }
    }

    @RequestMapping("/get/credential")
    @ResponseBody
    public Object getCredential(@RequestParam("openId") String openId,
                                @RequestParam("actId") long actId) {
        try {
            CredModel res = actService.getCredDetail(openId, actId);
            return ResponseUtil.okJSON(res);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取活动凭证信息出错");
        }
    }

    @RequestMapping("/personal-list")
    @ResponseBody
    public Object getPersonalList(@RequestParam("openId") String openId) {
        try {
            List<ListModel> list = actService.getPersonalList(openId);
            return ResponseUtil.okJSON(list);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取我的活动列表出错");
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getList() {
        try {
            List<ListModel> list = actService.getList();
            return ResponseUtil.okJSON(list);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取活动列表页出错");
        }
    }


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello activity";
    }
}