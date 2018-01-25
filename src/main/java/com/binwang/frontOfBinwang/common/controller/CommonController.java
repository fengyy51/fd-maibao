package com.binwang.frontOfBinwang.common.controller;

import com.binwang.frontOfBinwang.common.bean.WechatSdkConfig;
import com.binwang.frontOfBinwang.common.redis.CommonRAO;
import com.binwang.frontOfBinwang.common.service.VerifyCode;
import com.binwang.frontOfBinwang.common.service.WechatSDK;
import com.binwang.frontOfBinwang.config.OssBean;
import com.binwang.frontOfBinwang.utils.OssUtil;
import com.binwang.frontOfBinwang.utils.ResponseUtil;
import com.binwang.frontOfBinwang.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yy on 18/6/28.
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Resource
    private VerifyCode verifyCodeService;

    /**
     * 手机短信验证
     **/

    @RequestMapping(value = "/get-verify-code", method = RequestMethod.GET)
    @ResponseBody
    public Object getVerifyCode(@RequestParam("mobile") String mobile) {
        try {
            String uuid = verifyCodeService.sendVerifyCode(mobile);
            Map<String, String> m = new HashMap<>();
            m.put("verifyKey", uuid);
            System.out.print(m);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("发送手机短信出错");
        }
    }

    @RequestMapping(value = "/check-verify-code", method = RequestMethod.POST)
    @ResponseBody
    public Object checkVerifyCode(@RequestParam("answer") String answer,
                                  @RequestParam("verifyKey") String key) {
        try {
            Boolean res = verifyCodeService.checkVerifyCode(key, answer);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("校验手机验证码出错");
        }
    }

    /********************************************/

    /**
     * 图片上传
     **/
    @Resource(name = "ossBean")
    private OssBean ossBean;

    private final String prePath = "fimg/";

    @RequestMapping(value = "/upload/img", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImg(@RequestParam(value = "file") MultipartFile file) {
        try {
            String name = URLDecoder.decode(file.getOriginalFilename().replace("-", ""), "UTF-8");
            String filePath = prePath + UUIDUtil.getUUID() + "---" + name;
            OssUtil.objectUp(ossBean, file, filePath);
            Map<String, String> m = new HashMap<>();
            m.put("url", ossBean.getBaseUrl() + filePath);
            return ResponseUtil.okJSON(m);
        } catch (IOException e) {
            LOGGER.error("上传文件接口失败");
            return ResponseUtil.errorJSON("上传文件接口失败！");
        }
    }

    @RequestMapping(value = "/delete/img", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteImg(@RequestParam(value = "path") String path) {
        try {
            String name = path.replace(ossBean.getBaseUrl(), "");
            OssUtil.deleteObject(ossBean, name);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", true);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            LOGGER.error("删除文件失败！");
            return ResponseUtil.errorJSON("删除文件失败！");
        }
    }

    /******************************************/

    /**
     * 微信JS-SDK
     **/
    @Resource
    private WechatSDK wechatSDK;

    @RequestMapping("/js-sdk-config")
    @ResponseBody
    public Object getConfig(@RequestParam("url") String url) {
        try {
            WechatSdkConfig res = wechatSDK.getConfig(url);
            return ResponseUtil.okJSON(res);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("获取配置失败");
        }
    }

    //判断是否关注公众号
    @RequestMapping(value = "/is-subscribe", method = RequestMethod.POST)
    @ResponseBody
    public Object judgeIsSubScribe(@RequestParam("openId") String openId) {
        try {
            Boolean res = wechatSDK.judgeSubscribe(openId);
            Map<String, Boolean> m = new HashMap<>();
            m.put("result", res);
            return ResponseUtil.okJSON(m);
        } catch (Exception e) {
            return ResponseUtil.errorJSON("是否关注判断失败");
        }
    }

    /******************************************/


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "hello common";
    }


}
