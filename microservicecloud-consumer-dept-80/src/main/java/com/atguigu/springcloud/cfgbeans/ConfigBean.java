package com.atguigu.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * boot -->spring   applicationContext.xml ----  @Configuration配置   ConfigBean = applicationContext.xml
 */
@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端  负载均衡的工具
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }

    @Bean
    public IRule myRule(){
        //return new RoundRobinRule();
        return new RandomRule();//达到的目的用我们重新选择的随机算法代替默认轮询
    }
}
