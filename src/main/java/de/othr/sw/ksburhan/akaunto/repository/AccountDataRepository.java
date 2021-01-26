package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {

    @Query("SELECT u FROM AccountData u WHERE u.account = ?1")
    public AccountData findByAccountID(Account account);
}