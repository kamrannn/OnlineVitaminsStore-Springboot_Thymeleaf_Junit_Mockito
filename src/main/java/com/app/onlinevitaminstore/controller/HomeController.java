package com.app.onlinevitaminstore.controller;

import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent() && user.get().getRole().getName().equalsIgnoreCase("ROLE_USER")) {
            return "redirect:/product/all";
        } else if (user.isPresent() && user.get().getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "signin";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "signin";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String registerForSubmission(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Optional<User> existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                model.addAttribute("userAlreadyExists", true);
            } else {
                userService.save(user);
                model.addAttribute("successfully", true);
                model.addAttribute("user", new User());
            }
        }
        return "signup";
    }
}
