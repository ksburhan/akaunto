package de.othr.sw.ksburhan.akaunto.controller;

import de.othr.sw.ksburhan.akaunto.entity.Account;
import de.othr.sw.ksburhan.akaunto.entity.CustomAccount;
import de.othr.sw.ksburhan.akaunto.service.AccountService;
import de.othr.sw.ksburhan.akaunto.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @RequestMapping("/u/{username}")
    public String prepareAccountPage(@AuthenticationPrincipal CustomAccount customAccount, Model model, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        boolean isOwnProfile = false;
        boolean isLoggedIn = false;
        boolean isFollowing = false;

        if (targetAccount == null)
            return "error";

        if (customAccount != null) {
            isLoggedIn = true;
            ownAccount = accountService.findByUsername(customAccount.getUsername());
            if (targetAccount.equals(ownAccount))
                isOwnProfile = true;
            else if (ownAccount.getFollowing().contains(targetAccount))
                isFollowing = true;
        }

        model.addAttribute("targetAccount", targetAccount);
        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("isFollowing", isFollowing);

        return "profile";
    }

    @RequestMapping("/follow/{username}")
    public String followUser(@AuthenticationPrincipal CustomAccount customAccount, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        if (customAccount == null)
            return "redirect:/login";
        if (targetAccount == null)
            return "redirect:/home";

        ownAccount = accountService.findByUsername(customAccount.getUsername());

        if (ownAccount.getFollowing().contains(targetAccount))
            return "redirect:/u/" + username;
        else {
            ownAccount.getFollowing().add(targetAccount);
        }

        accountService.save(ownAccount);
        accountService.save(targetAccount);
        accountService.flush();

        return "redirect:/u/" + username;
    }

    @RequestMapping("/unfollow/{username}")
    public String unfollowUser(@AuthenticationPrincipal CustomAccount customAccount, @PathVariable String username) {
        Account targetAccount = accountService.findByUsername(username);
        Account ownAccount = null;

        if (customAccount == null)
            return "redirect:/login";
        if (targetAccount == null)
            return "redirect:/home";

        ownAccount = accountService.findByUsername(customAccount.getUsername());

        if (!ownAccount.getFollowing().contains(targetAccount))
            return "redirect:/u/" + username;
        else {
            ownAccount.getFollowing().remove(targetAccount);
        }

        accountService.save(ownAccount);
        accountService.save(targetAccount);
        accountService.flush();

        return "redirect:/u/" + username;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String prepareSearchPage(@AuthenticationPrincipal CustomAccount customAccount, Model model,
                                    @RequestParam(value = "searchparam", required = false) String searchparam) {
        List<Account> foundAccounts = accountService.search('%' + searchparam + '%');
        Account ownAccount = null;

        boolean isOwnProfile = false;
        boolean isLoggedIn = false;
        boolean isEmpty = false;

        if (foundAccounts == null)
            isEmpty = true;

        if (customAccount != null) {
            isLoggedIn = true;
            ownAccount = accountService.findByUsername(customAccount.getUsername());
            if (foundAccounts.contains(ownAccount))
                isOwnProfile = true;
        }

        model.addAttribute("foundAccounts", foundAccounts);
        model.addAttribute("searchparam", searchparam);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("isEmpty", isEmpty);

        return "search";
    }

    @RequestMapping("/settings")
    public String prepareSettingscreen(@AuthenticationPrincipal CustomAccount customAccount, Model model) {
        Account ownAccount = accountService.findByUsername(customAccount.getUsername());
        model.addAttribute("ownAccount", ownAccount);
        model.addAttribute("checkPassword", "");
        return "settings";
    }

    @RequestMapping("/process_settingschange")
    public String processSettingsChange(@AuthenticationPrincipal CustomAccount customAccount, Model model, Account account, @RequestParam String checkPassword) {

        Account saveAccount = accountService.findByUsername(customAccount.getUsername());

        System.out.println("old data" + saveAccount);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(checkPassword, saveAccount.getPassword())) {
            System.out.println("password does not match");
            model.addAttribute("ownAccount", saveAccount);
            model.addAttribute("passwordError", true);
            return "settings";
        }

        saveAccount.setFirstName(account.getFirstName());
        saveAccount.setLastName(account.getLastName());
        saveAccount.setPassword(passwordEncoder.encode(account.getPassword()));

        accountService.save(saveAccount);

        model.addAttribute("ownAccount", saveAccount);
        model.addAttribute("checkPassword", "");
        model.addAttribute("passwordSuccess", true);
        return "settings";
    }
}
