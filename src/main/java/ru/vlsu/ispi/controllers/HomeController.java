package ru.vlsu.ispi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.TaskService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController implements ErrorController {
    @Autowired
    private TaskService taskHandler;

    @GetMapping("/")
    public String Index(Model model){
        List<Task> taskList = new ArrayList<>();

        taskList = taskHandler.getAllTasks();

        model.addAttribute("taskList", taskList);

        return "mycatalog";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String Test(){
        return "hello";
    }

/*
    @RequestMapping("/error")
    public String getErrorPath(){
        return "redirect:/";
    }
*/
}
