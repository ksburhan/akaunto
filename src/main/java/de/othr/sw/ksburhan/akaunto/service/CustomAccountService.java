package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAccountService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Account not found");
        }
        return new CustomAccount(account);
    }

}
