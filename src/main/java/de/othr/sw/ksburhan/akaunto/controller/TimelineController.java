package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class TimelineController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TimelineService timelineService;

    @RequestMapping("/home")
    public String prepareHomescreen(@AuthenticationPrincipal CustomAccount customAccount, Model model) {

        Account account = accountService.findByUsername(customAccount.getUsername());
        List<Post> allFollowedPosts = new ArrayList<>();
        allFollowedPosts.addAll(account.getPosts());
        for (Account followed : account.getFollowing()) {
            allFollowedPosts.addAll(timelineService.findByAuthorID(followed));
        }
        allFollowedPosts.sort(Comparator.comparing(Post::getDate).reversed());
        model.addAttribute("allFollowedPosts", allFollowedPosts);
        model.addAttribute("account", account);
        model.addAttribute("post", new Post());
        return "home";
    }

    @PostMapping("/post")
    public String createNewPost(@AuthenticationPrincipal CustomAccount customAccount, Post post, Model model) {
        post.setAuthor(accountService.findByUsername(customAccount.getUsername()));
        post.setCurrentDate();

        timelineService.save(post);

        return "redirect:/home";
    }

    @RequestMapping("/p/{postID}")
    public String preparePostPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable long postID) {
        Post targetPost = timelineService.findByPostID(postID);

        if(targetPost == null){
            return "error";
        }

        targetPost.setAuthor(accountService.findByID(targetPost.getAuthor().getId()));


        model.addAttribute("targetPost", targetPost);

        return "post";
    }
}
