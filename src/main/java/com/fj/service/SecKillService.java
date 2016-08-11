package com.fj.service;

import com.fj.dto.Exposer;
import com.fj.dto.SecKillExecution;
import com.fj.entity.SecKill;
import com.fj.exception.RepeatKillException;
import com.fj.exception.SecKillCloseException;
import com.fj.exception.SecKillException;

import java.util.List;

/**
 * Business logic interface: Design it from user's view
 * Created by wanghe on 4/08/16.
 */
public interface SecKillService {

    /**
     * Query all seckilled records
     *
     * @return
     */
    List<SecKill> getSecKillList();

    /**
     * Query specific record by id
     *
     * @param secKillId
     * @return
     */
    SecKill getById(long secKillId);

    /**
     * Print interface url when starting the seckill event,
     * otherwise pring system time and seckill time
     *
     * @param secKillId
     * @return
     */
    Exposer exportSecKillUrl(long secKillId);

    /**
     * Execute seckill action
     *
     * @param secKillId
     * @param userMobile
     * @param md5
     */
    SecKillExecution executeSecKill(long secKillId, long userMobile, String md5)
            throws SecKillException, RepeatKillException, SecKillCloseException;

    /**
     * Execute seckill by stored procedure
     *
     * @param secKillId
     * @param userMobile
     * @param md5
     */
    SecKillExecution executeSecKillProcedure(long secKillId, long userMobile, String md5);
}
