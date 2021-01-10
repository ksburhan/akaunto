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
    public String prepareAccountPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        boolean isOwnProfile = false;
        boolean isLoggedIn = false;
        boolean isFollowing = false;

        if(targetAccount == null)
            return "error";

        if(customAccount != null) {
            isLoggedIn = true;
            ownAccount = accountService.findByUsername(customAccount.getUsername());
            if(targetAccount.equals(ownAccount))
                isOwnProfile = true;
            else if (ownAccount.getFollowing().contains(targetAccount))
                isFollowing = true;
        }

        model.addAttribute("targetAccount", targetAccount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("isFollowing", isFollowing);

        return "profile";
    }

    @RequestMapping("/follow/{username}")
    public String followUser(@AuthenticationPrincipal CustomAccount customAccount, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        if(customAccount == null)
            return "redirect:/login";
        if(targetAccount == null)
            return "redirect:/home";

        ownAccount = accountService.findByUsername(customAccount.getUsername());

        if(ownAccount.getFollowing().contains(targetAccount))
            return "redirect:/u/" + username;
        else {
            ownAccount.getFollowing().add(targetAccount);
            //targetAccount.getFollowers().add(ownAccount);
        }

        accountService.save(ownAccount);
        accountService.save(targetAccount);
        accountService.flush();

        System.out.println("followed " + username);
        return "redirect:/u/" + username;
    }

    @RequestMapping("/unfollow/{username}")
    public String unfollowUser(@AuthenticationPrincipal CustomAccount customAccount, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        if(customAccount == null)
            return "redirect:/login";
        if(targetAccount == null)
            return "redirect:/home";

        ownAccount = accountService.findByUsername(customAccount.getUsername());

        if(!ownAccount.getFollowing().contains(targetAccount))
            return "redirect:/u/" + username;
        else {
            ownAccount.getFollowing().remove(targetAccount);
            //targetAccount.getFollowers().remove(ownAccount);
        }

        accountService.save(ownAccount);
        accountService.save(targetAccount);
        accountService.flush();

        System.out.println("unfollowed " + username);
        return "redirect:/u/" + username;
    }
}
