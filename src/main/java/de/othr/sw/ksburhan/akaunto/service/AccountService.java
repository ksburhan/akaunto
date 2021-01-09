package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountService extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.username = ?1")
    public Account findByUsername(String username);

    @Query("SELECT u FROM Account u WHERE u.id = ?1")
    public Account findByID(long id);

}