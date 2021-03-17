package com.soen390.erp.accounting.service;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public Optional<Account> getAccount(int id){
        Optional<Account> account = accountRepository.findById(id);
        return account;
    }
}
