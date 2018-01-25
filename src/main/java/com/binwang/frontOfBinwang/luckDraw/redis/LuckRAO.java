package com.binwang.frontOfBinwang.luckDraw.redis;

/**
 * Created by owen on 17/7/20.
 */
public interface LuckRAO {
    Boolean judgeDraw(String openId, int time);

    void decrDrawTime(String openId);
}
