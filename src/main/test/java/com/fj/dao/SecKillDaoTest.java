package com.fj.dao;

import com.fj.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Config Spring and Junit. Load Spring IOC Container when starting Junit.
 * Created by wanghe on 3/08/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// Notify Junit the config file of Spring
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {
    /**
     * Dao implementation dependence Injection
     */
    @Resource
    private SecKillDao secKillDao;

    @Test
    public void queryById() throws Exception {

        long id = 1000;

        SecKill secKill = secKillDao.queryById(id);

        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void queryAll() throws Exception {

        List<SecKill> list = secKillDao.queryAll(0, 100);

        for (SecKill secKill :
                list) {

            System.out.println(secKill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {

        Date killTime = new Date();
        int updateCount = secKillDao.reduceNumber(1000L, killTime);
        System.out.println("update Record: " + updateCount);
    }
}