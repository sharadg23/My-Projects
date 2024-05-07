package com.assignment.loadbalancer.LoadBalancer;

import com.assignment.loadbalancer.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component("RoundRobin")
public class RoundRobin implements LoadBalancer{
    @Autowired
    ServerConfig serverConfig;
    private final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public String selectServer() {
        List<String> servers = serverConfig.getServerList();
        int index = counter.incrementAndGet() % servers.size();
        return servers.get(index);
    }
}
