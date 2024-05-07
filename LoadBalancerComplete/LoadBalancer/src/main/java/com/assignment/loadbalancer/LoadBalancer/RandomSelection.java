package com.assignment.loadbalancer.LoadBalancer;

import com.assignment.loadbalancer.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("RandomSelection")
public class RandomSelection implements LoadBalancer {

    @Autowired
    ServerConfig serverConfig;

    @Autowired
    DiscoveryClient client;

    @Override
    public String selectServer() {
        List servers = serverConfig.getServerList();
        Random random = new Random();
        int index = random.nextInt(servers.size());
        return (String) servers.get(index);
    }
}
