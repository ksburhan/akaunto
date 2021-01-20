package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountDTO;
import de.othr.sw.ksburhan.akaunto.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(long id) {
        return accountRepository.findByID(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void flush() {
        accountRepository.flush();
    }

    public List<Account> search(String s) {
        return accountRepository.search(s);
    }

    public Account findByID(long id) {
        return accountRepository.findByID(id);
    }

    public AccountDTO convertToDTO(Account account){
        AccountDTO accountDTO = new AccountDTO(account.getUsername(), account.getFirstName(), account.getLastName());
        return accountDTO;
    }
}
