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
    /*Deposit and withdraw methods
     * 05.04.24*/
    public void addNewAccount(Account account) {
        Optional<Account> accountByName = accountRepository.findAccountByName(account.getName());
        if(accountByName.isPresent()){
            throw new IllegalStateException("Name taken");
        }
        accountRepository.save(account);

        System.out.println(account);
    }

    /*Deposit and withdraw methods
    * 08.04.24*/
    public void depositFunds(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdrawFunds(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    /*transfer between accounts method
     * 08.04.24*/
    public void transferBalance(Long fromAccountId, Long toAccountId, double amount) {
        Optional<Account> fromAccountOptional = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOptional = accountRepository.findById(toAccountId);

        if (fromAccountOptional.isPresent() && toAccountOptional.isPresent()) {
            Account fromAccount = fromAccountOptional.get();
            Account toAccount = toAccountOptional.get();

            if (fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);

                accountRepository.save(fromAccount);
                accountRepository.save(toAccount);
            } else {
                throw new IllegalArgumentException("Insufficient funds");
            }
        } else {
            throw new IllegalArgumentException("One or both accounts not found");
        }
    }



}
