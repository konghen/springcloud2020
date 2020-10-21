package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/consumer")
public class PaymentFeignController {

    @Resource
    PaymentFeignService paymentFeignService;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }


    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment) {
        return paymentFeignService.create(payment);
    }


    @GetMapping(value = "/payment/slowRequest")
    public String slowRequest() {
        return paymentFeignService.slowRequest();
    }
}
