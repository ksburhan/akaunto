package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TimelineService timelineService;

    @RequestMapping("/u/{username}")
    public String showUserPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);

        if(targetAccount == null)
            return "error";

        boolean isOwnProfile = false;
        boolean isLoggedIn = false;
        boolean isFollowing = false;

        if(customAccount != null && targetAccount.getUsername().equals(customAccount.getUsername()))
            isOwnProfile = true;
        else if (customAccount != null) {
            isLoggedIn = true;
            if(false)
                isFollowing = true;
        }



        model.addAttribute("targetAccount", targetAccount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("isFollowing", isFollowing);

        return "profile";
    }

    @RequestMapping("/follow")
    public String followUser(@AuthenticationPrincipal CustomAccount customAccount, String username) {

        System.out.println("followed" + username);
        return "redirect:/u/" + username;
    }

    @RequestMapping("/unfollow")
    public String unfollowUser(@AuthenticationPrincipal CustomAccount customAccount, String username) {

        System.out.println("unfollowed");
        return "redirect:/u/" + username;
    }
}
