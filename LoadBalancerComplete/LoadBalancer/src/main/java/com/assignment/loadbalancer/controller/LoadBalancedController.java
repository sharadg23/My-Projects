package com.assignment.loadbalancer.controller;

import com.assignment.loadbalancer.LoadBalancer.HealthyLoadBalancedUrlHandler;
import com.assignment.loadbalancer.LoadBalancer.LoadBalancer;
import com.assignment.loadbalancer.factory.LoadBalancedFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class LoadBalancedController {
    Logger logger = LoggerFactory.getLogger(LoadBalancedController.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HealthyLoadBalancedUrlHandler healthyLoadBalancedUrlHandler;
    @RequestMapping("/**")
    public ResponseEntity<String> getResponse(HttpServletRequest request, @RequestBody(required = false) Object requestBody) {

        String serverUrl = healthyLoadBalancedUrlHandler.getHealthyUrl();

        serverUrl = serverUrl+request.getRequestURI();

        // check for requestparams
        Map<String, String[]> map = request.getParameterMap();
        if(!map.isEmpty()) {
            serverUrl = serverUrl +"?"+buildParams(map);
        }

        HttpEntity<Object> entity = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        // Preparing the request entity
        if (requestMethod == HttpMethod.POST || requestMethod == HttpMethod.PUT) {
            // Create HttpEntity with the request body
            entity = new HttpEntity<>(requestBody, headers);
        } else if (requestMethod == HttpMethod.GET || requestMethod == HttpMethod.DELETE) {
            // Create HttpEntity without the request body
            entity = new HttpEntity<>(headers);
        }

        logger.info(serverUrl);

        ResponseEntity<String> response = restTemplate.exchange(serverUrl, requestMethod, entity, String.class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    private String buildParams(Map<String, String[]> map) {
        String requestParams = "";
        for(Map.Entry<String, String[]> entry: map.entrySet()) {
            requestParams += entry.getKey()+"="+entry.getValue()[0]+"&";
        }
        requestParams = requestParams.substring(0, requestParams.length()-1);
        return requestParams;
    }

}
