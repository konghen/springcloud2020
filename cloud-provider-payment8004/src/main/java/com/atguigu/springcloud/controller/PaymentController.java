package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment) {

        int i = paymentService.create(payment);
        logger.info("新增一条记录，结果为 [{}]", i);

        return i > 0 ? new CommonResult<>(200, "新增成功") : new CommonResult<>(400, "新增失败", i);

    }


    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPaymentById(id);
        logger.info("查询结果为[{}]", payment);

        return payment != null ? new CommonResult<>(200, "查询成功", payment) : new CommonResult<>(400, "无此记录", null);
    }

}
