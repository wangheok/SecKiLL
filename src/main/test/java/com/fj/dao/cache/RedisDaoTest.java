package com.fj.dao.cache;

import com.fj.dao.SecKillDao;
import com.fj.entity.SecKill;
import junit.runner.BaseTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by wanghe on 10/08/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;

    @Autowired
    RedisDao redisDao;
    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void testSecKill() throws Exception {
        // get & put
        SecKill secKill = redisDao.getSecKill(id);

        if (secKill == null) {
            secKill = secKillDao.queryById(id);

            if ((secKill != null)) {
                String result = redisDao.putSecKill(secKill);
                System.out.println("****" + result);
                secKill = redisDao.getSecKill(id);
                System.out.println("****" + secKill.toString());
            }
        }
    }
}