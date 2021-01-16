package de.othr.sw.ksburhan.akaunto;

import static org.assertj.core.api.Assertions.assertThat;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class AccountRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    // test methods go below
    @Test
    public void testCreateAccount() {
        Account account = new Account();
        account.setUsername("neguse");
        account.setPassword("1234");
        account.setFirstName("bur");
        account.setLastName("han");

        Account savedUser = accountRepository.save(account);

        Account existAccount = entityManager.find(Account.class, savedUser.getId());

        assertThat(account.getUsername()).isEqualTo(existAccount.getUsername());

    }
}