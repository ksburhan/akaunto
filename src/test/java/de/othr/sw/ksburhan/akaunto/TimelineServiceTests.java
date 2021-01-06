package de.othr.sw.ksburhan.akaunto;

import static org.assertj.core.api.Assertions.assertThat;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.TimelineService;
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
public class TimelineServiceTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private AccountService accountService;

    // test methods go below
    @Test
    public void testPost() {
        Post post = new Post();
        Account account = new Account("neguse", "123", "asd", "asd");
        post.setAuthorID(account.getId());
        post.setContent("hello akaunto");
        post.setCurrentDate();

        Post savedPost = timelineService.save(post);

        Post existPost = entityManager.find(Post.class, savedPost.getId());

        assertThat(post.getId()).isEqualTo(existPost.getId());

    }
}