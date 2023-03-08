package ru.vlsu.ispi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.enums.TaskType;

import java.util.ArrayList;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String Index(Model model){
        ArrayList<Task> taskList = new ArrayList<>();

        Task task1 = new Task();
        task1.setId(1L);
        task1.setType(TaskType.Repairing);
        task1.setCaption("Починить двигатель");
        task1.setPrice(1000f);
        task1.setDescription("Требуется починить двигатель внутреннего сгорания в автомобиле");
        task1.setExecutorId(10L);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setType(TaskType.Cleaning);
        task2.setCaption("Помыть квартиру");
        task2.setPrice(500f);
        task2.setDescription("Требуется помыть пол с моющим средством в двухкомнатной квартире");
        task2.setExecutorId(11L);

        taskList.add(task1);
        taskList.add(task2);

        model.addAttribute("taskList", taskList);

        return "catalog";
    }
}