package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.logic.UserService;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;
import java.sql.SQLException;


@Controller
@RequestMapping(value = "/account/")
public class AccountController {
    @Autowired
    private UserService userHandler;

    @GetMapping("index")
    public String AuthIndex(Model model,
                            HttpServletRequest request,
                            HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                model.addAttribute("user", user);
                return "auth_index";
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk")
    public String LK(Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.nameRoleUser(userId);
            if (user != null){
                model.addAttribute("user", user);
                return "lk";
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk/admin")
    public String AdminPage(Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                if (user.getRole() == RoleType.Admin){
                    model.addAttribute("user", user);
                    return "adminPage";
                }
                else {
                    return "redirect:/account/lk";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk/moderator")
    public String ModeratorPage(Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                if (user.getRole() == RoleType.Moderator){
                    model.addAttribute("user", user);
                    return "moderatorPage";
                }
                else {
                    return "redirect:/account/lk";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("register")
    public String Register(Model model){
        model.addAttribute("register", new RegisterModel());

        return "registerForm";
    }

    @PostMapping("registerPost")
    public String RegisterPost(@ModelAttribute RegisterModel registerModel, RedirectAttributes attributes, HttpSession session) throws SQLException {
        if (registerModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userHandler.RegisterUser(registerModel);

        if (user == null) {
            return "redirect:/account/login";
        }
        else if (user.getId() != -1){
            attributes.addFlashAttribute("user", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/account/index";
        }
        else {
            return "redirect:/account/register";
        }
    }


    @GetMapping("login")
    public String Login(Model model){
        model.addAttribute("login", new LoginModel());
        return "loginForm";
    }

    @PostMapping("loginPost")
    public String LoginPost(@ModelAttribute LoginModel loginModel, RedirectAttributes attributes, HttpSession session) throws SQLException{
        if (loginModel == null){
            throw new IllegalArgumentException("Null login model was provided");
        }
        User user = userHandler.LoginUser(loginModel);

        if (user != null){
            attributes.addFlashAttribute("user", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/account/index";
        }
        else {
            return "redirect:/account/register";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model, HttpSession session){
        session.removeAttribute("userId");
        return "redirect:/";
    }
}
