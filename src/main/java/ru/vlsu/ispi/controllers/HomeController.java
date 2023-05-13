package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
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
    public String DefaultIndex(HttpSession session){

        if (session.getAttribute("rowToFind") == null){
            session.setAttribute("rowToFind", "empty");
        }

        if (session.getAttribute("type") == null){
            session.setAttribute("type", "default");
        }

        if (session.getAttribute("status") == null){
            session.setAttribute("status", "default");
        }

        if (session.getAttribute("sorter") == null){
            session.setAttribute("sorter", "default_sort");
        }

        if (session.getAttribute("elementsPerPage") == null){
            session.setAttribute("elementsPerPage", 5);
        }

        if (session.getAttribute("currentPageNumber") == null){
            session.setAttribute("currentPageNumber", 1);
        }

        var rowToFind = (String) session.getAttribute("rowToFind");
        var type = (String) session.getAttribute("type");
        var status = (String) session.getAttribute("status");
        var sorter = (String) session.getAttribute("sorter");

        var elementsPerPage = (int) session.getAttribute("elementsPerPage");

        var currentPageNumber = 1;
        session.setAttribute("currentPageNumber", currentPageNumber);

        return "redirect:/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);
    }

    @GetMapping("index1")
    public String RedirectAmongThePages(@RequestParam("currentPageNumber") int currentPageNumber,
                                        @RequestParam("elementsPerPage") int elementsPerPage,
                                        HttpSession session){

        if (currentPageNumber <= 0 || elementsPerPage <= 0){
            return "redirect:/";
        }

        if (session.getAttribute("rowToFind") == null){
            session.setAttribute("rowToFind", "empty");
        }

        if (session.getAttribute("type") == null){
            session.setAttribute("type", "default");
        }

        if (session.getAttribute("status") == null){
            session.setAttribute("status", "default");
        }

        if (session.getAttribute("sorter") == null){
            session.setAttribute("sorter", "default_sort");
        }

        session.setAttribute("elementsPerPage", elementsPerPage);

        session.setAttribute("currentPageNumber", currentPageNumber);

        var rowToFind = (String) session.getAttribute("rowToFind");
        var type = (String) session.getAttribute("type");
        var status = (String) session.getAttribute("status");
        var sorter = (String) session.getAttribute("sorter");

        return "redirect:/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);
    }

    @GetMapping("index2")
    public String RedirectAmongFilters(@RequestParam(name = "rowToFind") String rowToFind,
                                       @RequestParam(name = "type") String type,
                                       @RequestParam(name = "status") String status,
                                       @RequestParam(name = "sorter") String sorter,
                                       HttpSession session){

        if (stringIsNullOrEmptyOrBlank(rowToFind) || stringIsNullOrEmptyOrBlank(type)
                || stringIsNullOrEmptyOrBlank(sorter) || stringIsNullOrEmptyOrBlank(sorter)){
            return "redirect:/";
        }

        session.setAttribute("rowToFind", rowToFind);

        session.setAttribute("type", type);

        session.setAttribute("status", status);

        session.setAttribute("sorter", sorter);

        var elementsPerPage = (int) session.getAttribute("elementsPerPage");

        var currentPageNumber = 1;
        session.setAttribute("currentPageNumber", currentPageNumber);

        return "redirect:/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);

    }

    @GetMapping("/index")
    public String Index(@RequestParam(name = "rowToFind") String rowToFind,
                        @RequestParam(name = "type") String type,
                        @RequestParam(name = "status") String status,
                        @RequestParam(name = "sorter") String sorter,
                        @RequestParam(name = "elementsPerPage") int elementsPerPage,
                        @RequestParam(name = "currentPageNumber") int currentPageNumber,
                        Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);

            if (user != null){
                return "redirect:/account/default_index";
            }
        }

        var taskList = actionHandler.nameAllLikedAndUnlikedTasks();

        var filterSet = new WholeFilterSet(rowToFind, type, status, sorter);

        var obtainedTaskList = userHandler.filterByRowParameters(taskList, rowToFind, filterSet, sorter);

        if (obtainedTaskList.size() == 0 && taskList.size() != 0){

            session.setAttribute("rowToFind", "empty");
            session.setAttribute("type", "default");
            session.setAttribute("status", "default");
            session.setAttribute("sorter", "default_sort");

            return "redirect:/";
        }

        var pagination = new Pagination();

        pagination.setElementsPerPage(elementsPerPage);

        pagination.setCurrentPageNumber(currentPageNumber);

        var listOfListsTasks = taskHandler.distributeTasksByPages(obtainedTaskList, pagination);

        pagination.setLastPageNumber(listOfListsTasks.size() - 1);

        session.setAttribute("lastPageNumber", pagination.getLastPageNumber());

        var listTasksForCurrentPage = taskHandler.getElementsForCurrentPage(listOfListsTasks, currentPageNumber);

        if (listTasksForCurrentPage == null && taskList.size() != 0) {

            return "redirect:/";
        }

        model.addAttribute("taskList", listTasksForCurrentPage);

        model.addAttribute("filterSet", new WholeFilterSet(rowToFind, type, status, sorter));

        model.addAttribute("pagination", pagination);

        return "mycatalog";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String Test(){
        return "hello";
    }

    private boolean stringIsNullOrEmptyOrBlank(String row){
        return row == null || row.isEmpty() || row.trim().isEmpty();
    }

/*
    @RequestMapping("/error")
    public String getErrorPath(HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                return "redirect:/account/default_index";
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
