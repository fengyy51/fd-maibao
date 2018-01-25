package com.binwang.frontOfBinwang.common.service;

/**
 * Created by owen on 17/6/28.
 */
public interface VerifyCode {
    String sendVerifyCode(String mobile);

    Boolean checkVerifyCode(String uuid, String ans);
}
