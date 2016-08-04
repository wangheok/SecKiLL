package com.fj.exception;

/**
 * Repeat seckill product Exception (RuntimeException)
 * Created by wanghe on 4/08/16.
 */
public class RepeatKillException extends SecKillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
