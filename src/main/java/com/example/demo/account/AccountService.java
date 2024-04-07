package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public void addNewAccount(Account account) {
        Optional<Account> accountByName = accountRepository.findAccountByName(account.getName());
        if(accountByName.isPresent()){
            throw new IllegalStateException("Name taken");
        }
        accountRepository.save(account);

        System.out.println(account);
    }
}
