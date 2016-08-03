package com.fj.dao;

import com.fj.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wanghe on 3/08/16.
 */
public interface SuccessKilledDao {

    /**
     * Insert detail (Enable filter duplication)
     *
     * @param secKillId
     * @param userMobile
     * @return
     */
    int insertSuccessKilled(@Param("secKillId") long secKillId, @Param("userMobile") long userMobile);

    /**
     * Query SuccessKilled by id, and bundle product object entity
     *
     * @param secKillId
     * @return
     */
    SuccessKilled queryByIdWithSecKill(@Param("secKillId") long secKillId, @Param("userMobile") long userMobile);

}
