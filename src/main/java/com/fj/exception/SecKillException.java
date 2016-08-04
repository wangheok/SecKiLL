package com.fj.exception;

/**
 * Seckill business logic related Exception
 * Created by wanghe on 4/08/16.
 */
public class SecKillException extends RuntimeException {

    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
