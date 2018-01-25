package com.binwang.frontOfBinwang.utils;

import java.util.UUID;

/**
 * 唯一标识验证码签到
 * Created by yy on 17/11/22.
 */
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //去掉小o，小l，大O，共59
    public static final String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "m", "n", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String getShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            //16进制转为10进制
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3b]);
        }
        return shortBuffer.toString();
    }

    public static String getNativeUUID() {
        return UUID.randomUUID().toString();
    }
}
