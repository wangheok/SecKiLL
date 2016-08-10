package com.fj.service.impl;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import com.fj.dao.SecKillDao;
import com.fj.dao.SuccessKilledDao;
import com.fj.dao.cache.RedisDao;
import com.fj.dto.Exposer;
import com.fj.dto.SecKillExecution;
import com.fj.entity.SecKill;
import com.fj.entity.SuccessKilled;
import com.fj.enums.SecKillStateEnum;
import com.fj.exception.RepeatKillException;
import com.fj.exception.SecKillCloseException;
import com.fj.exception.SecKillException;
import com.fj.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghe on 4/08/16.
 */
@Service
public class SecKillServiceImpl implements SecKillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    // Slat value for encryption
    public final String slat = "nelson#street#Auckland#NZ";

    public List<SecKill> getSecKillList() {

        return secKillDao.queryAll(0, 5);
    }

    public SecKill getById(long secKillId) {

        return secKillDao.queryById(secKillId);
    }

    public Exposer exportSecKillUrl(long secKillId) {

        // --- Cache Optimization start ---
        // access redis
        SecKill secKill = redisDao.getSecKill(secKillId);
        if (secKill == null) {
            // 2. Access database
            secKill = secKillDao.queryById(secKillId);
            if (secKill == null) {
                // false: the seckill does not exist
                return new Exposer(false, secKillId);
            } else {
                // 3. put into redis
                redisDao.putSecKill(secKill);
            }
        }
        // --- Cache Optimization end ---
        /*
        SecKill secKill = secKillDao.queryById(secKillId);
        if (secKill == null) {
            return new Exposer(false, secKillId);
        }
        */

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime()
                || currentTime.getTime() > endTime.getTime()) {

            return new Exposer(false, secKillId, currentTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(secKillId);
        return new Exposer(true, md5, secKillId);
    }

    /**
     * Transaction Method
     *
     * @param secKillId
     * @param userMobile
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillCloseException
     */
    @Transactional
    public SecKillExecution executeSecKill(long secKillId, long userMobile, String md5)
            throws SecKillException, RepeatKillException, SecKillCloseException {
        // verify the url with md5 encryption
        if (md5 == null || !md5.equals(getMD5(secKillId))) {

            throw new SecKillException("SecKill data rewrite!");
        }
        // Execute seckill action: reduce stock and insert purchase record
        // 1. Reduce stock
        Date purchaseTime = new Date();

        try {
            int updateCount = secKillDao.reduceNumber(secKillId, purchaseTime);

            if (updateCount <= 0) {
                // No update after execution, seckill closed
                throw new SecKillCloseException("SecKill have been closed!");

            } else {
                // 2. Insert purchase record
                int insertCount = successKilledDao.insertSuccessKilled(secKillId, userMobile);

                if (insertCount <= 0) {
                    // verify if repeat the seckill purchase
                    throw new RepeatKillException("Repeat purchase!");
                } else {
                    // purchase successfully
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(secKillId, userMobile);
                    return new SecKillExecution(secKillId, SecKillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SecKillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SecKillException("SecKill inner Error!" + e.getMessage());
        }
    }

    /**
     * encrypt
     *
     * @param secKillId
     * @return
     */
    private String getMD5(long secKillId) {

        String base = secKillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
