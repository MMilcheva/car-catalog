package com.example.carcatalog.controllers.mvc;

import com.example.carcatalog.dto.LoginDto;
import com.example.carcatalog.exceptions.AuthenticationFailureException;
import com.example.carcatalog.exceptions.AuthorizationException;
import com.example.carcatalog.exceptions.EntityNotFoundException;
import com.example.carcatalog.helpers.AuthenticationHelper;
import com.example.carcatalog.helpers.AuthenticationMapper;
import com.example.carcatalog.models.User;
import com.example.carcatalog.services.contracts.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcController {

    private final AuthenticationHelper authenticationHelper;

    private final UserService userService;


    private final AuthenticationMapper mapper;

    public AuthenticationMvcController(AuthenticationHelper authenticationHelper, UserService userService,

                                       AuthenticationMapper mapper) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;


        this.mapper = mapper;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {

        model.addAttribute("login", new LoginDto());
        return "LoginView";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto loginDto,
                              BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "LoginView";
        }
        try {
            User user = userService.getUserByUsername(loginDto.getUsername());
            String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
            session.setAttribute("fullName", fullName);
            session.setAttribute("currentUser", user.getUsername());
        } catch (AuthenticationFailureException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";

        }

        try {
            User user = authenticationHelper.verifyAuthentication(loginDto.getUsername(), loginDto.getPassword());
            session.setAttribute("currentUser", user.getUsername());
            return "redirect:/";
        } catch (AuthenticationFailureException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "LoginView";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

//    @GetMapping("register")
//    public String showRegisterPage(Model model, HttpSession session) {
//
//        User user = authenticationHelper.tryGetUserWithSession(session);
//        checkAccessPermissions(user);
//        String loggerUsername = user.getUsername();
//        User loggedInUser = userService.getUserByUsername(loggerUsername);
//        if (loggedInUser.getRole().getRoleName().equals("admin")) {
//            model.addAttribute("register", new RegisterDto());
//            return "RegisterView";
//        }
//        return "redirect:/auth/login";
//    }

//    @PostMapping("register")
//    public String handleRegister(@Valid @ModelAttribute("register") RegisterDto registerDto,
//                                 BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes,
//                                 HttpServletRequest request, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "RegisterView";
//        }
//        try {
//            User user = mapper.fromDto(registerDto);
//
//            // Generate a random username and password for the user
//            String username = userMapper.generateRandomUsername(user.getEmail());
//            String password = userMapper.generateRandomPassword();
//            user.setUsername(username);
//            user.setPassword(password);
//
//            // Create the user in the database
//            userService.createUser(user);
//
//            // Generate password reset token
//            String token = UUID.randomUUID().toString();
//
//            // Save token to database
//            userService.createPasswordResetTokenForUser(user, token);
//
//            // Send password reset email
//            String resetUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/reset-password?token=" + token;
//            emailSenderService.sendRegistrationAndResetEmail(user.getEmail(), username, resetUrl);
//
//            redirectAttributes.addFlashAttribute("message", "You are registered successfully, please check your email for your credentials.");
//            return "redirect:/users";
//        }catch (DuplicateEntityException e) {
//            model.addAttribute("error", e.getMessage());
//            return "DuplicateEntityView";
//
//        }catch (EntityNotFoundException e) {
//            model.addAttribute("error", "Email or phone number already exists");
//            return "DuplicateEmailOrPhone";
//        }
//    }

//    @GetMapping("/admin")
//    public String showAdminPage(HttpSession session, Model model) {
//        try {
//            User user = authenticationHelper.tryGetUserWithSession(session);
////            checkAccessPermissions(user);
////            if (user.getRole().getRoleName().equals("admin")) {
////                return "AdminPanelView";
////            } else {
////                throw new AuthorizationException("You are not authorized to access this page.");
////            }
//        } catch (AuthorizationException e) {
//            model.addAttribute("error", e.getMessage());
//            return "AccessDeniedView";
//        }
//    }

    @GetMapping("/info")
    public String showUserInfo(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUserWithSession(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            //  user = userService.getUserById(userId);
            model.addAttribute("user", user);
            //  model.addAttribute(userService.getUserById(userId));
            //   model.addAttribute(userService.getPhoneNumberByUserId(userId));
            return "UserInfoView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

//    private static void checkAccessPermissions(User executingUser) {
//        if (!executingUser.getRole().getRoleName().equals("admin")) {
//            throw new UnauthorizedOperationException(ERROR_MESSAGE);
//        }
//    }
//

}
