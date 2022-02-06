package com.coding.assignment.clientservice.controller;

import com.coding.assignment.clientservice.models.AccountDto;
import com.coding.assignment.clientservice.services.ClientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/import-accounts-launch")
    public ResponseEntity<List<AccountDto>> launch(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(clientService.launch(httpServletRequest));
    }

    @GetMapping("/get-accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(clientService.getAccounts(httpServletRequest));
    }
}
