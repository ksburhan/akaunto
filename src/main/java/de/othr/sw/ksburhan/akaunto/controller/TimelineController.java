package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.AdvertisementService;
import de.othr.sw.ksburhan.akaunto.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping("/post")
    public String createNewPost(@AuthenticationPrincipal CustomAccount customAccount, Post post, Model model) {
        post.setAuthor(accountService.findByUsername(customAccount.getUsername()));
        post.setCurrentDate();

        postService.save(post);

        return "redirect:/home";
    }

    @RequestMapping("/p/{postID}")
    public String preparePostPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable long postID) {
        Post targetPost = postService.findByPostID(postID);
        Account ownAccount = null;

        boolean isLoggedIn = false;

        if (targetPost == null) {
            return "error";
        }

        if (customAccount != null) {
            isLoggedIn = true;
            ownAccount = accountService.findByUsername(customAccount.getUsername());
        }

        targetPost.setAuthor(accountService.findByID(targetPost.getAuthor().getId()));

        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("targetPost", targetPost);

        return "post";
    }

    @GetMapping("/ad/{adId}")
    public String redirectToAd(@PathVariable long adId) {
        Advertisement ad = advertisementService.findByAdID(adId);
        ad.incrementClicks();
        advertisementService.save(ad);
        return "redirect:" + ad.getUrl();
    }
}
