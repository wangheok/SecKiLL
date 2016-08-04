package com.fj.service;

import com.fj.dto.Exposer;
import com.fj.dto.SecKillExecution;
import com.fj.entity.SecKill;
import com.fj.exception.RepeatKillException;
import com.fj.exception.SecKillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wanghe on 4/08/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SecKillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillService secKillService;

    @Test
    public void getSecKillList() throws Exception {

        List<SecKill> list = secKillService.getSecKillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillService.getById(id);
        logger.info("secKill={}", secKill);
    }

    @Test
    public void testSecKillLogic() throws Exception {

        long id = 1001;
        Exposer exposer = secKillService.exportSecKillUrl(id);

        if (exposer.isExposed()) {

            logger.info("exposer={}", exposer);
            long mobile = 64279998889L;
            String md5 = exposer.getMd5();
            try {
                SecKillExecution secKillExecution = secKillService.executeSecKill(id, mobile, md5);
                logger.info("result={}", secKillExecution);

            } catch (RepeatKillException e) {
                logger.error(e.getMessage(), e);
            } catch (SecKillCloseException e1) {
                logger.error(e1.getMessage(), e1);
            }
        } else {
            // Seckill has not started yet
            logger.warn("exposer={}", exposer);
        }
    }
}