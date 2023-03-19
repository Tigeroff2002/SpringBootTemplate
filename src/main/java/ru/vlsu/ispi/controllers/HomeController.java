package ru.vlsu.ispi.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskType;

import java.util.ArrayList;

@Controller
public class HomeController implements ErrorController {
    @GetMapping("/")
    public String Index(Model model){
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
        model.addAttribute("task1", task1);

        return "mycatalog";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String Test(){
        return "hello";
    }

    @RequestMapping("/error")
    public String getErrorPath(){
        return "redirect:/";
    }
}
