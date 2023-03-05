package ru.vlsu.ispi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vlsu.ispi.beans.Task;

import java.util.ArrayList;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String Index(Model model){
        ArrayList<Task> taskList = new ArrayList<>();

        Task task1 = new Task();
        task1.setId(1L);
        task1.setCaption("Починить двигатель");
        task1.setPrice(1000);
        task1.setExecutorId(10L);

        Task task2 = new Task();
        task2.setId(2L);
        task1.setCaption("Помыть квартиру");
        task1.setPrice(500);
        task1.setExecutorId(11L);

        taskList.add(task2);
        taskList.add(task1);

        model.addAttribute("taskList", taskList);

        return "catalog";
    }
}
