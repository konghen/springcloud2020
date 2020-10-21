package com.atguigu.springcloud;

import com.atguigu.springcloud.myrule.MyselfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@ComponentScan(basePackages = {"com.atguigu.springcloud"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MyselfRule.class)})
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MyselfRule.class)
public class OrderMain1001 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain1001.class);
    }

}
