package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController implements ErrorController {
    @Autowired
    private TaskService taskHandler;

    @Autowired
    private UserService userHandler;

    @Autowired
    private ActionService actionHandler;

    @GetMapping("/")
    public String Index(Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);

            if (user != null){
                return "redirect:/account/index1";
            }
        }

        List<ExtraTask> taskList = new ArrayList<>();

        taskList = actionHandler.nameAllLikedAndUnlikedTasks();

        model.addAttribute("taskList", taskList);

        return "mycatalog";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String Test(){
        return "hello";
    }


    @RequestMapping("/error")
    public String getErrorPath(HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                return "redirect:/account/index1";
            }
        }

        return "redirect:/";
    }

    /* Code example for lab4 with transactions

    @Transactional(propagation = Propagation.SUPPORTS)
    @GetMapping("/test1")
    public String Test1() {
        try {
            userHandler.DeleteNonExistingUserWithRuntimeException();

            userHandler.InsertingUsersWithoutException();
        }
        catch (RuntimeException ex){
           ex.printStackTrace();
        }

        return "redirect:/";
    }
    */
}
