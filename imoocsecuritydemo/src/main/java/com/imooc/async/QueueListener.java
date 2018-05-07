package com.imooc.async;

import org.apache.commons.lang.StringUtils;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//@Component
public class QueueListener   implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)  {

        new Thread(()->{
        while (true){
            if(StringUtils.isNotBlank(mockQueue.getCompleteOrder()))
            {
                String orderNumber = mockQueue.getCompleteOrder();
                logger.info("返回订单处理结果"+orderNumber);
                deferredResultHolder.getMap().get(orderNumber).setResult("plece order sucess ");
                mockQueue.setCompleteOrder(null);



            }else {
                try {
                    //logger.info("blank");
                    Thread.sleep(100);
                }
                catch (Exception ex)
                {

                }
            }
        }
        }).start();
    }
}
