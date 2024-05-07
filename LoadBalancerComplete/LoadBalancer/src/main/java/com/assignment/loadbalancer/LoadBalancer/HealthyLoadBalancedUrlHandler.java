package com.assignment.loadbalancer.LoadBalancer;

import com.assignment.loadbalancer.factory.LoadBalancedFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthyLoadBalancedUrlHandler {
    @Autowired
    LoadBalancedFactory loadBalancedFactory;
    @Autowired
    RestTemplate restTemplate;
    public String getHealthyUrl() {
        String serverUrl;

        LoadBalancer loadBalancer = loadBalancedFactory.getLoadBalancer();

        while (true) {
            serverUrl = loadBalancer.selectServer();
            String healthCheckUrl = serverUrl + "/health";

            if (isServerHealthy(healthCheckUrl)) {
                break;
            }
        }

        return serverUrl;
    }

    private boolean isServerHealthy(String healthCheckUrl) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(healthCheckUrl, HttpMethod.GET, null, String.class);
            return response.getBody().equals("Healthy");
        } catch (Exception ex) {
            // Handle exception and log error message
            return false;
        }
    }
}
