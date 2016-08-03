package com.fj.dao;

import com.fj.entity.SecKill;
import com.fj.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by wanghe on 3/08/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {

        long id = 1001L;
        long mobile = 64278765432L;
        int count = successKilledDao.insertSuccessKilled(id, mobile);

        System.out.println("count: " + count);
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {

        long id = 1001L;
        long mobile = 64278765432L;

        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(id, mobile);

        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());
    }

}