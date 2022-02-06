package com.coding.assignment.clientservice.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private UUID id;

    private String password;

    private String accountNumber;

    private String clientId;

    private String status;

    public AccountDto(String password, String accountNumber, String clientId) {
        this.password = password;
        this.accountNumber = accountNumber;
        this.clientId = clientId;
    }
}
