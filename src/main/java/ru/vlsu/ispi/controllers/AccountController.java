package ru.vlsu.ispi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.logic.UserHandler;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/api/account/")
public class AccountController {
    private final UserHandler _userHandler;

    public AccountController(UserHandler userHandler) {
        this._userHandler = userHandler;
    }

    @GetMapping("register")
    public String Register(Model model){
        model.addAttribute(new RegisterModel());

        return "registerForm";
    }

    @PostMapping("registerPost")
    public String RegisterPost(@ModelAttribute RegisterModel registerModel, Model model) throws SQLException {
        if (registerModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }
        User user = _userHandler.RegisterUser(registerModel);

        return "auth_index";
    }

    @GetMapping("login")
    public String Login(Model model){
        model.addAttribute(new LoginModel());

        return "loginForm";
    }

    @PostMapping("loginPost")
    public String LoginPost(@ModelAttribute LoginModel loginModel, Model model) throws SQLException{
        if (loginModel == null){
            throw new IllegalArgumentException("Null login model was provided");
        }
        User user = _userHandler.LoginUser(loginModel);

        if (user == null){
            return "/api/account/register";
        }
        else {
            return "auth_index";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model){

        return "index";
    }
}
