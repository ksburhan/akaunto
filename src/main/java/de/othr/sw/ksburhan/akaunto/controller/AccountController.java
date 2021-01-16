package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/allAccounts")
    List<Account> all(){
       return accountRepository.findAll();
    }

    @PostMapping("/registeraccount")
    Account newAccount(@RequestBody Account newAccount) {
        return accountRepository.save(newAccount);
    }

    @GetMapping("/account/{id}")
    Account one(@PathVariable long id) {
        return accountRepository.findById(id)
                .orElseThrow();
    }

    @PutMapping("/account/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newAccount.getPassword());

        return accountRepository.findById(id)
                .map(account -> {
                    account.setUsername(newAccount.getUsername());
                    account.setFirstName(newAccount.getFirstName());
                    account.setLastName(newAccount.getLastName());
                    account.setPassword(encodedPassword);
                    return accountRepository.save(account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return accountRepository.save(newAccount);
                });
    }

    @DeleteMapping("/account/{id}")
    void deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
    }

    @GetMapping("/authenticate")
    public Account authenticate(@RequestParam String username, @RequestParam String password) {

        Account account = accountRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, account.getPassword()))
            return account;

        return null;
    }
}
