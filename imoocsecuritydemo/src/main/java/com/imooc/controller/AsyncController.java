package com.imooc.controller;


import com.imooc.async.DeferredResultHolder;
import com.imooc.async.MockQueue;
import com.imooc.async.QueueListener;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;
import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/order")
    public String order() throws Exception{
        logger.info("主线程开始");
        Thread.sleep(1000);
        logger.info("主线程返回");
        return "Sucess";
    }

    @RequestMapping("/asyncorder")
    public Callable<String> asyncorder()throws Exception{

        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "sucess";}
        };
        logger.info("主线程返回");
        return  result;

    }


    @Autowired
    MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;




    @RequestMapping("/async")
    public DeferredResult<String> o()throws Exception{
        logger.info("main process start");

        String orderNumber = RandomStringUtils.randomNumeric(8);//生成8位随机数

        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber,result);

        logger.info("main process finished");
        return result;
    }
}
