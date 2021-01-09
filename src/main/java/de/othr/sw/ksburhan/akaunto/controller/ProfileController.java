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
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TimelineService timelineService;

    @RequestMapping("/u/{username}")
    public String showUserPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable String username) {
        Account targetUser = accountService.findByUsername(username);
        boolean isLoggedIn = false;
        if(customAccount != null)
            isLoggedIn = true;

        if(targetUser == null)
            return "error";

        model.addAttribute("targetUser", targetUser);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "profile";
    }
}
