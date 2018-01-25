package com.binwang.frontOfBinwang.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created by owen on 17/6/28.
 */
public class SMSUtil {
    public static Boolean sendVerifyCode(String appKey, String appSecret, String endPoint, String signName, String templateCode, String code, String mobile) throws RuntimeException {
        try {
            IClientProfile profile = DefaultProfile.getProfile(endPoint, appKey, appSecret);
            DefaultProfile.addEndpoint(endPoint, endPoint, "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient client = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(signName);//控制台创建的签名名称
            request.setTemplateCode(templateCode);//控制台创建的模板CODE
            request.setTemplateParam("{\"verifyCode\":\"" + code + "\"}");//短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。"
            request.setAcceptFormat(FormatType.JSON); // 格式为json
            request.setPhoneNumbers(mobile);//接收号码
            SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
            return true;
        } catch (ServerException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
