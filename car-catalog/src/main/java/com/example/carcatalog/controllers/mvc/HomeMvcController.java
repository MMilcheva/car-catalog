package com.example.carcatalog.controllers.mvc;

import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;

    public HomeMvcController(AuthenticationHelper authenticationHelper, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showHomePage(Model model) {

        return "HomeView";
    }

    @GetMapping("/error")
    public String showHomePageErrorHandler(Model model) {

        return "HomeView";
    }
}
