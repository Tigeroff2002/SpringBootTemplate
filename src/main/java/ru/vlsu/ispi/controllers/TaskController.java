package ru.vlsu.ispi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.logic.TaskHandler;
import ru.vlsu.ispi.logic.UserHandler;
import ru.vlsu.ispi.models.TaskModel;

import java.sql.SQLException;

@RequestMapping(value = "/menu/")
public class TaskController {

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private TaskHandler taskHandler;
    @GetMapping("{userId}/create")
    public String CreateTask(@PathVariable Long userId, Model model) throws SQLException {

        User user = userHandler.FindUserById(userId);
        if (user == null || userId < 1){
            return "redirect:/";
        }
        else {
            model.addAttribute("task", new TaskModel());
            model.addAttribute("user", user);

            return "createTask";
        }
    }

    @PostMapping("{userId}/createPost")
    public String SubmitCreateTask(@PathVariable Long userId, @ModelAttribute TaskModel model, RedirectAttributes attributes) throws SQLException{
        if (model == null){
            throw new IllegalArgumentException("Null model was provided");
        }

        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = new Task();
            task.setId(1L);
            attributes.addFlashAttribute("task", task);
            attributes.addFlashAttribute("user", user);

            return "redirect:/menu/task/" + Long.toString(task.getId()) + "";
        }
    }

    @GetMapping("{userId}/details/task/{taskId}")
    public String TaskDetails(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{

        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = new Task();
            task.setId(1L);

            model.addAttribute("task", task);
            model.addAttribute("user", user);
            return "details";
        }
    }

    @GetMapping("{userId}/room/task/{taskId}")
    public String TaskRoom(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{

        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = new Task();
            task.setId(1L);

            model.addAttribute("task", task);
            model.addAttribute("user", user);
            return "taskRoomPage";
        }
    }

}
