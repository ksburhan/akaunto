package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimelineService extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.authorID = ?1")
    public List<Post> findByAuthorID(Long authorId);

}