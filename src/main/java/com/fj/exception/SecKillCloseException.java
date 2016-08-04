package com.fj.exception;

/**
 * Exception when the seckill closed
 * Created by wanghe on 4/08/16.
 */
public class SecKillCloseException extends SecKillException {

    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
