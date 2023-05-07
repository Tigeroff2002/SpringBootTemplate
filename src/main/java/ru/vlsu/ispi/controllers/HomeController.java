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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.beans.extrabeans.Pagination;
import ru.vlsu.ispi.beans.extrabeans.WholeFilterSet;
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
    public String DefaultIndex(){
        return "redirect:/index" + DEFAULT_FILTER_HEADER + "elementsPerPage=5&currentPageNumber=1";
    }

    @GetMapping("index1")
    public String RedirectAmongThePages(@RequestParam("currentPageNumber") int currentPageNumber,
                                        @RequestParam("elementsPerPage") int elementsPerPage,
                                        HttpSession session){

        return "redirect:/index" + DEFAULT_FILTER_HEADER +
                "elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);
    }

    @GetMapping("/index")
    public String Index(@RequestParam(name = "rowToFind") String rowToFind,
                        @RequestParam(name = "filter") String filter,
                        @RequestParam(name = "sorter") String sorter,
                        @RequestParam(name = "elementsPerPage") int elementsPerPage,
                        @RequestParam(name = "currentPageNumber") int currentPageNumber,
                        Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);

            if (user != null){
                return "redirect:/account/index1";
            }
        }

        var taskList = actionHandler.nameAllLikedAndUnlikedTasks();

        var obtainedTaskList = userHandler.filterByRowParameters(taskList, rowToFind, filter, sorter);

        var pagination = new Pagination();

        pagination.setElementsPerPage(elementsPerPage);

        pagination.setCurrentPageNumber(currentPageNumber);

        var listOfListsTasks = taskHandler.distributeTasksByPages(obtainedTaskList, pagination);

        pagination.setLastPageNumber(listOfListsTasks.size() - 1);

        var listTasksForCurrentPage = taskHandler.getElementsForCurrentPage(listOfListsTasks, currentPageNumber);

        if (listTasksForCurrentPage == null) {

            return "redirect:/";
        }

        model.addAttribute("taskList", listTasksForCurrentPage);

        model.addAttribute("filterSet", new WholeFilterSet(rowToFind, filter, sorter));

        model.addAttribute("pagination", pagination);

        return "mycatalog";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String Test(){
        return "hello";
    }

    private static final String DEFAULT_FILTER_HEADER = "?rowToFind=empty&filter=default_filter&sorter=default_sort&";

    /*
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
