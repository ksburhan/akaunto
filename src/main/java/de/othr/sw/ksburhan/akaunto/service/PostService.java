package de.othr.sw.ksburhan.akaunto.service;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.repository.AccountRepository;
import de.othr.sw.ksburhan.akaunto.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findByAuthorID(Account followed) {
        return postRepository.findByAuthorID(followed);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post findByPostID(long postID) {
        return postRepository.findByPostID(postID);
    }

    public List<Post> getAllFollowedPosts(Account account) {
        List<Post> allFollowedPosts = new ArrayList<>();
        allFollowedPosts.addAll(account.getPosts());
        for (Account followed : account.getFollowing()) {
            allFollowedPosts.addAll(findByAuthorID(followed));
        }
        allFollowedPosts.sort(Comparator.comparing(Post::getDate).reversed());
        return allFollowedPosts;
    }
}
