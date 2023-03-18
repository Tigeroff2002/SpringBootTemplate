package ru.vlsu.ispi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.TaskHandler;
import ru.vlsu.ispi.logic.UserHandler;
import ru.vlsu.ispi.models.TaskModel;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "/menu/")
public class TaskController {
    @Autowired
    private UserHandler userHandler;

    @Autowired
    private TaskHandler taskHandler;
    @GetMapping("{userId}/new-task")
    public String CreateTask(@PathVariable Long userId, Model model) throws SQLException {
        User user = userHandler.FindUserById(userId);
        if (user == null){
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
            model.setTaskId(taskHandler.GenerateNewTask(3L));

            if (model.getTaskId() == 2){
                task.setId(2L);
                task.setType(TaskType.Cleaning);
                task.setCaption("Помыть квартиру");
                task.setPrice(500);
                task.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
                task.setExecutorId(11L);
            }
            else {
                task.setId(1L);
                task.setType(TaskType.Repairing);
                task.setCaption("Починить двигатель");
                task.setPrice(1000);
                task.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
                task.setExecutorId(10L);
            }

            attributes.addFlashAttribute("task", task);
            attributes.addFlashAttribute("user", user);

            return "redirect:/menu" + Long.toString(userId) + "/details/task/" + Long.toString(task.getId()) + "";
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
            if (taskId == 1){
                task.setId(1L);
                task.setType(TaskType.Repairing);
                task.setCaption("Починить двигатель");
                task.setPrice(1000);
                task.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
                task.setExecutorId(10L);
            }
            else if (taskId == 2) {
                task.setId(2L);
                task.setType(TaskType.Cleaning);
                task.setCaption("Помыть квартиру");
                task.setPrice(500);
                task.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
                task.setExecutorId(11L);

            }

            model.addAttribute("task", task);
            model.addAttribute("user", user);
            return "details";
        }
    }

    @GetMapping("details/task/{taskId}")
    public String TaskWithoutUserDetails(@PathVariable Long taskId, Model model) throws SQLException{
        Task task = new Task();

        if (taskId == 1){
            task.setId(1L);
            task.setType(TaskType.Repairing);
            task.setCaption("Починить двигатель");
            task.setPrice(1000);
            task.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
            task.setExecutorId(10L);
        }
        else if (taskId == 2) {
            task.setId(2L);
            task.setType(TaskType.Cleaning);
            task.setCaption("Помыть квартиру");
            task.setPrice(500);
            task.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
            task.setExecutorId(11L);

        }

        model.addAttribute("task", task);
        return "details_nouser";
    }

    @GetMapping("{userId}/room/task/{taskId}")
    public String TaskRoom(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = new Task();
            if (taskId == 1){
                task.setId(1L);
                task.setType(TaskType.Repairing);
                task.setCaption("Починить двигатель");
                task.setPrice(1000);
                task.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
                task.setExecutorId(10L);
            }
            else if (taskId == 2) {
                task.setId(2L);
                task.setType(TaskType.Cleaning);
                task.setCaption("Помыть квартиру");
                task.setPrice(500);
                task.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
                task.setExecutorId(11L);

            }

            model.addAttribute("task", task);
            model.addAttribute("user", user);
            return "taskRoomPage";
        }
    }

}
