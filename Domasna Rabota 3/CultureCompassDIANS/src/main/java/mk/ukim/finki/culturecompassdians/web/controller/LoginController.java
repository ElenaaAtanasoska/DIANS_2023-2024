package mk.ukim.finki.culturecompassdians.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidArgumentsException;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidUserCredentialsException;
import mk.ukim.finki.culturecompassdians.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')") ne e za ovde, to be used
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;

        try {
            user = authService.login(request.getParameter("username"), request.getParameter("password"));
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            model.addAttribute("bodyContent", "login");
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "master-template";
        }

        request.getSession().setAttribute("user", user);
        return "redirect:/home_page";
    }

}