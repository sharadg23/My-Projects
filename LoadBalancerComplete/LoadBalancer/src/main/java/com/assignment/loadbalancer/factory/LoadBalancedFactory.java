package com.assignment.loadbalancer.factory;

import com.assignment.loadbalancer.LoadBalancer.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoadBalancedFactory {

    @Value("${loadBalancedStrategy}")
    private String loadBalancedStrategy;

    @Autowired
    private ApplicationContext context;

    public LoadBalancer getLoadBalancer() {
        return context.getBean(loadBalancedStrategy, LoadBalancer.class);
    }

}
