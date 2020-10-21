package com.atguigu.springcloud.service;

public interface PaymentService {

    String paymentInfoOK(Integer id);

    String paymentInfoTimeOut(Integer id);

    String paymentBreaker(Integer id);

}
