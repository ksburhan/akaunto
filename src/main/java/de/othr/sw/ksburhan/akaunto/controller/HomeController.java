package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.*;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.AdvertisementService;
import de.othr.sw.ksburhan.akaunto.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/")
    public String showStartPage(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String prepareRegistrationPage(Model model) {
        model.addAttribute("account", new Account());
        return "registration_form";
    }

    @GetMapping("/login")
    public String prepareLoginPage(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }

    @GetMapping("/login-error")
    public String prepareErrorLoginPage(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("loginError", true);
        return "login";
    }

    @PostMapping("/process_register")
    public String register(Account account) {

        accountService.createNewAccount(account);

        return "register_processed";
    }

    @RequestMapping("/home")
    public String prepareHomescreen(@AuthenticationPrincipal CustomAccount customAccount, Model model) {

        Account ownAccount = accountService.findByUsername(customAccount.getUsername());
        List<Post> allFollowedPosts = postService.getAllFollowedPosts(ownAccount);
        Advertisement ad = advertisementService.getAdforAccount(ownAccount);

        model.addAttribute("allFollowedPosts", allFollowedPosts);
        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("ad", ad);
        model.addAttribute("post", new Post());

        return "home";
    }
}
