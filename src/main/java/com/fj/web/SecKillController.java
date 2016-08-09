package com.fj.web;

import com.fj.dto.Exposer;
import com.fj.dto.SecKillExecution;
import com.fj.dto.SecKillResult;
import com.fj.entity.SecKill;
import com.fj.enums.SecKillStateEnum;
import com.fj.exception.RepeatKillException;
import com.fj.exception.SecKillCloseException;
import com.fj.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghe on 8/08/16.
 */
@Controller
@RequestMapping("/seckill") // Module
public class SecKillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        // list.jsp + model = ModelAndView

        // Get list page
        List<SecKill> secKillList = secKillService.getSecKillList();

        model.addAttribute("secKillList", secKillList);

        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long secKillId, Model model) {

        if (secKillId == null) {

            return "redirect: /seckill/list";
        }
        SecKill secKill = secKillService.getById(secKillId);
        if (secKill == null) {

            return "forward: /seckill/list";
        }
        model.addAttribute("secKill", secKill);
        return "detail";
    }

    /**
     * @param secKillId
     * @return Json
     */
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable("seckillId") Long secKillId) {

        SecKillResult<Exposer> result;
        try {
            Exposer exposer = secKillService.exportSecKillUrl(secKillId);
            result = new SecKillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SecKillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    /**
     * Seckill execution
     *
     * @param secKillId
     * @param md5
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable("seckillId") Long secKillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killMobile", required = false) Long mobile) {

        if (mobile == null) {

            return new SecKillResult<SecKillExecution>(false, "Please Sign in!");
        }
        SecKillResult<SecKillExecution> result;

        try {
            SecKillExecution execution = secKillService.executeSecKill(secKillId, mobile, md5);
            return new SecKillResult<SecKillExecution>(true, execution);

        } catch (RepeatKillException e1) {
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStateEnum.REPEAT_PURCHASE);
            return new SecKillResult<SecKillExecution>(true, execution);
        } catch (SecKillCloseException e2) {
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStateEnum.END);
            return new SecKillResult<SecKillExecution>(true, execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStateEnum.INNER_ERROR);
            return new SecKillResult<SecKillExecution>(true, execution);
        }
    }

    /**
     * Get current time
     *
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SecKillResult<Long> time() {

        Date now = new Date();
        return new SecKillResult<Long>(true, now.getTime());
    }
}
