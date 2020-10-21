package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfoOK(Integer id) {
        return "paymentInfoOK fallback";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "paymentInfoTimeOut fallback";
    }
}
