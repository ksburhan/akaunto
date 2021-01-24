package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountDTO;
import de.othr.sw.ksburhan.akaunto.entity.AccountData;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/allAccounts")
    List<Account> all(){
       return accountService.findAll();
    }

    @GetMapping("/registeraccount")
    AccountDTO newAccount(@RequestParam String username, @RequestParam String firstName,
                       @RequestParam String lastName, @RequestParam String password) {
        Account newAccount = new Account(username, password, firstName, lastName);
        newAccount = accountService.createNewAccount(newAccount);
        AccountDTO accountDTO = accountService.convertToDTO(newAccount);
        accountDTO.setPassword(password);
        return accountDTO;
    }

    @GetMapping("/account/{id}")
    Account one(@PathVariable long id) {
        return accountService.findById(id);
    }

    @DeleteMapping("/account/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
    }

    @GetMapping("/authenticate")
    public AccountDTO authenticate(@RequestParam String username, @RequestParam String password) {

        Account account = accountService.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, account.getPassword())) {
            AccountDTO accountDTO = accountService.convertToDTO(account);
            accountDTO.setPassword(password);
            return accountDTO;
        }

        return null;
    }
}
