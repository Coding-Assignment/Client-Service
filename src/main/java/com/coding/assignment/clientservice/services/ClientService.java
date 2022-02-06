package com.coding.assignment.clientservice.services;

import com.coding.assignment.clientservice.models.AccountDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final RestTemplate restTemplate;

    @Value("${server.ip}")
    private String serverIp;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "errorHandler",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")})
    public List<AccountDto> launch(HttpServletRequest httpServletRequest) {

        List<AccountDto> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            //Huge test data
            list.add(new AccountDto("123456", "91029382110212331", "0000"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", httpServletRequest.getHeader("Authorization"));
        HttpEntity<List<AccountDto>> requestEntity = new HttpEntity<>(list, headers);

        logger.info("Importing Accounts Started...");

        List<AccountDto> response = restTemplate.exchange(
                "http://"+serverIp+":8080/bank-service/accounts",
                HttpMethod.POST,
                requestEntity,
                List.class).getBody();

        logger.info("Importing Accounts Finished.");
        return response;
    }

    public List<AccountDto> errorHandler(HttpServletRequest httpServletRequest) {
        System.out.println("Error while importing the accounts...");
        return new ArrayList<>();
    }

    public List<AccountDto> getAccounts(HttpServletRequest httpServletRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", httpServletRequest.getHeader("Authorization"));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        List<AccountDto> response = restTemplate.exchange(
                "http://"+serverIp+":8080/bank-service/accounts",
                HttpMethod.GET,
                requestEntity,
                List.class).getBody();

        return response;
    }
}
