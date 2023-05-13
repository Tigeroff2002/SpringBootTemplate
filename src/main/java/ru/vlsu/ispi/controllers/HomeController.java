package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlsu.ispi.logic.UserService;

import java.sql.SQLException;

@Controller
public class HomeController implements ErrorController {
    @Autowired
    private UserService userHandler;

    @GetMapping("/")
    public String Index(Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);

            if (user != null){
                return "redirect:/account/index";
            }
        }

        return "mycatalog";
    }
}
