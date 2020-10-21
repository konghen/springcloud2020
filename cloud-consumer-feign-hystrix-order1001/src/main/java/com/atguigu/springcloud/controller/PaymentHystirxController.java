package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
@DefaultProperties(defaultFallback = "defaultException", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
})
public class PaymentHystirxController {

    @Resource
    PaymentHystrixService paymentHystrixService;


    @GetMapping(value = "/payment/hystrix/ok/{id}")
    @HystrixCommand
    public String paymentInfoOK(@PathVariable Integer id) {
        int i = new Random().nextInt(4000);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paymentHystrixService.paymentInfoOK(id) + ";" + i;
    }


    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutException", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfoTimeOut(@PathVariable Integer id) {
        return paymentHystrixService.paymentInfoTimeOut(id);
    }


    @GetMapping(value = "/payment/hystrix/normal/{id}")
    public String paymentInfoNormal(@PathVariable Integer id) {
        return paymentHystrixService.paymentInfoOK(id) + ";";
    }


    public String defaultException() {
        return "defaultException";
    }

    public String paymentInfoTimeoutException(Integer id) {
        return "paymentInfoTimeoutException：" + id;
    }

}
