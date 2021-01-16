package de.othr.sw.ksburhan.akaunto.repository;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.author = ?1")
    public List<Post> findByAuthorID(Account author);

    @Query("SELECT p FROM Post p WHERE p.id = ?1")
    public Post findByPostID(long postId);

    @Query("SELECT p FROM Post p")
    public List<Post> findAll();

}