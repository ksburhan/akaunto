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
public class HomeController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TimelineService timelineService;

    @GetMapping("/")
    public String showStartPage(Model model) {
        return "index";
    }

    @RequestMapping("/accounts")
    public String prepareAccountPage(Model model) {
        List<Account> allAccounts = accountService.findAll();
        model.addAttribute("allAccounts", allAccounts);
        return "accounts";
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        accountService.save(account);

        return "register_processed";
    }
}
