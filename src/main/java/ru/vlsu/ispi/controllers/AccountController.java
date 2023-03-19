package ru.vlsu.ispi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.UserService;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/account/")
public class AccountController {
    @Autowired
    private UserService userHandler;

    @GetMapping("index/{id}")
    public String AuthIndex(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (id == 2){
            return "redirect:/";
        }
        else {
            user = new User();
            model.addAttribute("user", user);

            ArrayList<Task> taskList = new ArrayList<>();

            Task task1 = new Task();
            task1.setId(1L);
            task1.setType(TaskType.Repairing);
            task1.setCaption("Починить двигатель");
            task1.setPrice(1000f);
            task1.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
            task1.setExecutor(new User());

            Task task2 = new Task();
            task2.setId(2L);
            task2.setType(TaskType.Cleaning);
            task2.setCaption("Помыть квартиру");
            task2.setPrice(500f);
            task2.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
            task2.setExecutor(new User());

            taskList.add(task1);
            taskList.add(task2);

            model.addAttribute("taskList", taskList);

            return "auth_index";
        }
    }

    @GetMapping("profile/{id}")
    public String Profile(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (id == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("user", user);

            return "profile";
        }
    }

    @GetMapping("lk/{id}")
    public String LK(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (id == 2){
            return "redirect:/";
        }
        else {
            user = new User();
            model.addAttribute("user", user);

            return "lk";
        }
    }

    @GetMapping("lk/admin/{id}")
    public String AdminPage(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else if (user.getRole() == RoleType.Admin){
            model.addAttribute("user", user);

            return "adminPage";
        }
        else {

            return "redirect:/account/lk/" + Long.toString(user.getId()) + "";
        }
    }

    @GetMapping("lk/moderator/{id}")
    public String ModeratorPage(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else if (user.getRole() == RoleType.Moderator){
            model.addAttribute("user", user);

            return "moderatorPage";
        }
        else {

            return "redirect:/account/lk/" + Long.toString(user.getId()) + "";
        }
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

            return "redirect:/account/login";
        }
        else {
            attributes.addFlashAttribute("user", user);

            return "redirect:/account/index/" + Long.toString(user.getId()) + "";
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

            return "redirect:/account/register";
        }
        else {
            attributes.addFlashAttribute("user", user);

            return "redirect:/account/index/" + Long.toString(user.getId()) + "";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model){

        return "catalog";
    }
}
