package com.atguigu.springcloud.service;

import com.atguigu.springcloud.service.impl.PaymentFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-HYSTRIX-PAYMENT-SERVICE", fallback = PaymentFallbackServiceImpl.class)
public interface PaymentHystrixService {

    @GetMapping(value = "/hystrix/payment/ok/{id}")
    String paymentInfoOK(@PathVariable(value = "id") Integer id);


    @GetMapping(value = "/hystrix/payment/timeout/{id}")
    String paymentInfoTimeOut(@PathVariable(value = "id") Integer id);

}