package com.fj.dao;

import com.fj.entity.SecKill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghe on 3/08/16.
 */
public interface SecKillDao {

    /**
     * In stock Reduction
     *
     * @param secKillId
     * @param killTime
     * @return if affected rows > 1, return value represents the record rows updated
     */
    int reduceNumber(@Param("secKillId") long secKillId, @Param("killTime") Date killTime);

    /**
     * Query stock by Id
     *
     * @param secKillId
     * @return
     */
    SecKill queryById(long secKillId);

    /**
     * Query killed product list by offset
     *
     * @param offset
     * @param limit
     * @return
     * @Param annoation enable the bundle of "offset" and "limit"
     */
    List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
