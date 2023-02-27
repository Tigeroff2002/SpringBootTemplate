package ru.vlsu.ispi.controllers;

import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.logic.UserHandler;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/api/account/")
public class AccountController {
    @Autowired
    private UserHandler userHandler;

    @GetMapping("index")
    public String AuthIndex(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);

        return "auth_index";
    }

    @GetMapping("lk")
    public String LK(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);

        return "lk";
    }

    @GetMapping("admin")
    public String AdminPage(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);

        return "adminPage";
    }

    @GetMapping("register")
    public String Register(Model model){
        model.addAttribute("register", new RegisterModel());

        return "registerForm";
    }

    @PostMapping("registerPost")
    public String RegisterPost(@ModelAttribute RegisterModel registerModel, RedirectAttributes attributes) throws SQLException {
        if (registerModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }
        User user = userHandler.RegisterUser(registerModel);

        if (user == null){

            return "redirect:/api/account/login";
        }
        else {
            attributes.addFlashAttribute("user", user);

            return "redirect:/api/account/index";
        }
    }

    @GetMapping("login")
    public String Login(Model model){
        model.addAttribute("login", new LoginModel());

        return "loginForm";
    }

    @PostMapping("loginPost")
    public String LoginPost(@ModelAttribute LoginModel loginModel, RedirectAttributes attributes) throws SQLException{
        if (loginModel == null){
            throw new IllegalArgumentException("Null login model was provided");
        }
        User user = userHandler.LoginUser(loginModel);

        if (user == null){

            return "redirect:/api/account/register";
        }
        else {
            attributes.addFlashAttribute("user", user);

            return "redirect:/api/account/index";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model){

        return "index";
    }
}
