package com.imooc.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private  String placeOrder;

    private   String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder)  throws  Exception{

        new Thread(()->{


            try {
                logger.info("questOpen Start ");
                Thread.sleep(1000);

                completeOrder = placeOrder;
                logger.info("questOpen finished");
            }catch (Exception ex)
            {

            }
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder){

        this.completeOrder = completeOrder;

    }
}
