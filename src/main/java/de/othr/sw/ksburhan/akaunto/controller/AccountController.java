package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountDTO;
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

    @PostMapping("/registeraccount")
    Account newAccount(@RequestBody Account newAccount) {
        return accountService.save(newAccount);
    }

    @GetMapping("/account/{id}")
    Account one(@PathVariable long id) {
        return accountService.findById(id);
    }

    @PutMapping("/account/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newAccount.getPassword());

        Account account = accountService.findById(id);
        account.setUsername(newAccount.getUsername());
        account.setFirstName(newAccount.getFirstName());
        account.setLastName(newAccount.getLastName());
        account.setPassword(encodedPassword);

        return accountService.save(account);
    }

    @DeleteMapping("/account/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
    }

    @GetMapping("/authenticate")
    public AccountDTO authenticate(@RequestParam String username, @RequestParam String password) {

        Account account = accountService.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, account.getPassword()))
            return accountService.convertToDTO(account);

        return null;
    }
}
