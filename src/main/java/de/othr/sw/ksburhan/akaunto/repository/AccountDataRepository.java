package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDataRepository extends JpaRepository<AccountData, Long> {


}