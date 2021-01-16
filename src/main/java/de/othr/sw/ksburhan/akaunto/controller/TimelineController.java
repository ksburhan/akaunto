package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.Advertisement;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.entity.Post;
import de.othr.sw.ksburhan.akaunto.repository.AccountRepository;
import de.othr.sw.ksburhan.akaunto.repository.AdvertisementRepository;
import de.othr.sw.ksburhan.akaunto.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class TimelineController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @PostMapping("/post")
    public String createNewPost(@AuthenticationPrincipal CustomAccount customAccount, Post post, Model model) {
        post.setAuthor(accountRepository.findByUsername(customAccount.getUsername()));
        post.setCurrentDate();

        postRepository.save(post);

        return "redirect:/home";
    }

    @RequestMapping("/p/{postID}")
    public String preparePostPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable long postID) {
        Post targetPost = postRepository.findByPostID(postID);
        Account ownAccount = null;

        boolean isLoggedIn = false;

        if (targetPost == null) {
            return "error";
        }

        if (customAccount != null) {
            isLoggedIn = true;
            ownAccount = accountRepository.findByUsername(customAccount.getUsername());
        }

        targetPost.setAuthor(accountRepository.findByID(targetPost.getAuthor().getId()));

        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("targetPost", targetPost);

        return "post";
    }

    @GetMapping("/ad/{adId}")
    public String redirectToAd(@PathVariable long adId) {
        Advertisement ad = advertisementRepository.findByAdID(adId);
        ad.incrementClicks();
        advertisementRepository.save(ad);
        return "redirect:" + ad.getUrl();
    }
}
