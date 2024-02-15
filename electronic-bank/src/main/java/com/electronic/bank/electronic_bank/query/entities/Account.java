package com.electronic.bank.electronic_bank.query.entities;

import java.util.List;

import com.electronic.bank.electronic_bank.commonapi.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    private String id;

    private String createdAt;
    private double balance;
    private AccountStatus status;
    private String currency;

    @OneToMany(mappedBy = "account")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<AccountTransaction> transactions;
}