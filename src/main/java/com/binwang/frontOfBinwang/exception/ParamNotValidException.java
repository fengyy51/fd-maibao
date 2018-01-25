package com.binwang.frontOfBinwang.exception;

/**
 * Created by owen on 17/6/23.
 */
public class ParamNotValidException extends RuntimeException{
    public ParamNotValidException() {
    }

    public ParamNotValidException(String msg) {
        super(msg);
    }
}
