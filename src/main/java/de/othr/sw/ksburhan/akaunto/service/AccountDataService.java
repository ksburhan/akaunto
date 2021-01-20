package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.repository.AccountDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDataService {

    AccountDataRepository accountDataRepository;

    @Autowired
    public AccountDataService(AccountDataRepository accountDataRepository) {
        this.accountDataRepository = accountDataRepository;
    }

}
