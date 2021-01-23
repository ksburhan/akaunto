package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.username = ?1")
    public Account findByUsername(String username);

    @Query("SELECT u FROM Account u WHERE u.id = ?1")
    public Account findByID(long id);

    @Query("SELECT u FROM Account u WHERE u.username LIKE ?1 OR u.firstName LIKE ?1 OR u.lastName LIKE ?1")
    public List<Account> search(String parameter);

}