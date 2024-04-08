package com.example.demo.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class AccountController {


    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

//    @GetMapping
//    public List<Account> getAccounts() {
//        return accountService.getAccounts();
//
//    }
//
//    @PostMapping
//    public void registerNewAccount(@RequestBody Account account) {
//        accountService.addNewAccount(account);
//    }



    @GetMapping("/")
    public String getAccounts(Model model) {
        List<Account> accounts = accountService.getAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", new Account()); // Empty account for form submission
        return "index";
    }

    @PostMapping("/api/v1/account")
    public String registerNewAccount(@ModelAttribute Account account) {
        accountService.addNewAccount(account);
        return "redirect:/";
    }


    /*Controller for transaction page
     * 08.04.24*/
    @GetMapping("/transactions")
    public String showTransactionsPage(Model model) {
        List<Account> accounts = accountService.getAccounts();
        model.addAttribute("accounts", accounts);
        return "transactions";
    }

    @PostMapping("/api/v1/account/deposit")
    public String depositFunds(@RequestParam Long accountId, @RequestParam double amount) {
        try {
            accountService.depositFunds(accountId, amount);
        } catch (IllegalArgumentException ex) {

            return "redirect:/error";
        }
        return "redirect:/";
    }

    @PostMapping("/api/v1/account/withdraw")
    public String withdrawFunds(@RequestParam Long accountId, @RequestParam double amount) {
        try {
            accountService.withdrawFunds(accountId, amount);
        } catch (IllegalArgumentException ex) {

            return "redirect:/error";
        }
        return "redirect:/";
    }

    @PostMapping("/api/v1/account/transfer")
    public String transferBalance(@RequestParam Long fromAccountId,
                                  @RequestParam Long toAccountId,
                                  @RequestParam double amount) {
        try {
            accountService.transferBalance(fromAccountId, toAccountId, amount);
            return "redirect:/";
        } catch (IllegalArgumentException ex) {

            return "redirect:/error";
        }
    }





}
