package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String paymentInfoOK(Integer id) {
        return "线程池" + Thread.currentThread().getName() + "：paymentInfo_OK， id：" + id;
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3500")
    })
    public String paymentInfoTimeOut(Integer id) {

        int i = new Random().nextInt(4000);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "线程池" + Thread.currentThread().getName() + "：paymentInfo_TimeOut， id：" + id + "耗时" + i;
    }

    public String paymentInfoTimeOutHandler(Integer id) {

        return "线程池" + Thread.currentThread().getName() + "：paymentInfoTimeOutHandler";
    }


    @Override
    @HystrixCommand(fallbackMethod = "paymentBreakerFallback", commandProperties = {
            //开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //10秒内请求失败次数，大于此次数，断路
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //断路多久后开始尝试恢复，毫秒
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "15000"),
            //失败达到一定百分比后打开断路器
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
            //在10秒内10个请求有60%以上失败，打开断路器
    })
    public String paymentBreaker(Integer id) {

        System.out.println(id);
        if (id > 10) {
            throw new RuntimeException("随机数太大！");
        }

        String s = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + " 调用成功，流水号为" + s;
    }


    public String paymentBreakerFallback(Integer id) {

        return "随机数不能太大！";
    }

}
