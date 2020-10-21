package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/hystrix/payment")
public class PaymentController {

    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    String serverPort;

    @GetMapping(value = "/ok/{id}")
    public String paymentInfoOK(@PathVariable Integer id) {
        logger.info("paymentInfoOK，端口号为" + serverPort + "，参数为" + id);
        return paymentService.paymentInfoOK(id);
    }


    @GetMapping(value = "/timeout/{id}")
    public String paymentInfoTimeOut(@PathVariable Integer id) {
        logger.info("paymentInfoTimeOut，端口号为" + serverPort + "，参数为" + id);
        return paymentService.paymentInfoTimeOut(id);
    }


    @GetMapping(value = "/breaker/{id}")
    public String paymentBreaker(@PathVariable Integer id) {
        logger.info("paymentInfoTimeOut，端口号为" + serverPort + "，参数为" + id);
        return paymentService.paymentBreaker(id);
    }

}
