package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.DTOs.AccountDTO;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/registeraccount")
    public AccountDTO newAccount(@RequestParam String username, @RequestParam String firstName,
                       @RequestParam String lastName, @RequestParam String password ,@AuthenticationPrincipal CustomAccount customAccount) {
        if(!customAccount.getUsername().equals("vinzent"))
            return null;

        Account newAccount = new Account(username, password, firstName, lastName);
        newAccount = accountService.createNewAccount(newAccount);
        if(newAccount == null){
            return new AccountDTO();
        }
        AccountDTO accountDTO = accountService.convertToDTO(newAccount);
        accountDTO.setPassword(password);
        accountDTO.setAuthenticated(true);
        return accountDTO;
    }

    @GetMapping("/authenticate")
    public AccountDTO authenticate(@RequestParam String username, @RequestParam String password,
                                   @AuthenticationPrincipal CustomAccount customAccount) {

        if(!customAccount.getUsername().equals("vinzent"))
            return null;

        Account account = accountService.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, account.getPassword())) {
            AccountDTO accountDTO = accountService.convertToDTO(account);
            accountDTO.setPassword(password);
            accountDTO.setAuthenticated(true);
            return accountDTO;
        }
        return new AccountDTO();
    }
}
