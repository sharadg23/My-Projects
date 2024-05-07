package com.assignment.loadbalancer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerConfig {

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${loadBalancedService}")
    String loadBalancedService;

    public List<String> getServerList() {
        List<String> serverList = new ArrayList<>();
        boolean retry = true;
        while (retry) {
            List<ServiceInstance> instances = discoveryClient.getInstances(loadBalancedService); // Replace "serviceName" with the name of your service
            if (instances.isEmpty()) {
                try {
                    Thread.sleep(3000); // Sleep for 3 second before retrying
                } catch (InterruptedException e) {
                    // Handle InterruptedException
                }
            } else {
                retry = false;
                for (ServiceInstance instance : instances) {
                    String serverUrl = "http://" + instance.getHost() + ":" + instance.getPort();
                    serverList.add(serverUrl);
                }
            }
        }
        return serverList;
    }
}
