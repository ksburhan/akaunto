package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    public List<Account> getAllAccounts() {

        // Repo access
        // Business logic

        return List.of(
                new Account(1L, "John", "Doe"),
                new Account(2L, "Maria", "Muster")
        );
    }
}
