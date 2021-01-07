package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class TimelineController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TimelineService timelineService;

    @PostMapping("/post")
    public String createNewPost(@AuthenticationPrincipal CustomAccount customAccount, Post post, Model model) {
        post.setAuthorID(accountService.findByUsername(customAccount.getUsername()).getId());
        post.setCurrentDate();

        timelineService.save(post);

        return "redirect:/home";
    }

    @RequestMapping("/p/{postID}")
    public String showPostPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable Long postID) {
        Post targetPost = timelineService.findByPostID(postID);

        if(targetPost == null){
            return "error";
        }
        model.addAttribute("targetPost", targetPost);

        return "post";
    }
}
