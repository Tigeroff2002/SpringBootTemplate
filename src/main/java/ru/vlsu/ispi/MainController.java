package ru.vlsu.ispi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.vlsu.ispi.DAO.ClubDAO;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.daoimpl.ISportsmanDAO;
import ru.vlsu.ispi.logic.abstractions.IHandler;

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller

public class MainController {
    @Autowired
    private IHandler _handler;

    @GetMapping("/hello/{id}")
    public String handle(@PathVariable Long id, @RequestParam(required = false, name = "playerId") Long playerId, Model model){
        _handler.HandleIndex(id, playerId, model);

        return "index";
    }

    @GetMapping("/club")
    public String clubForm(@RequestParam(required = false, name = "id") Long id, Model model){
        _handler.ClubFormOpen(id, model);

        return "clubs";
    }

    @PostMapping("/club")
    public String clubSubmit(@ModelAttribute Club club, Model model){
        _handler.ClubFormSubmit(club, model);

        return "club";
    }

    @GetMapping("/club/{id}")
    public String deleteClub(@PathVariable Long id, Model model){
        _handler.DeleteClub(id, model);

        return "club";
    }

    @GetMapping("/sportsman")
    public String sportsmanForm(Model model){
        _handler.SportsmanFormOpen(model);

        return "sportsmen";
    }

    @PostMapping("/sportsman")
    public String sportsmanSubmit(@Valid @ModelAttribute Sportsman sportsman, BindingResult result, Model model){
        if (result.hasErrors()){
            return "sportsmen";
        }
        else {
            _handler.SportsmanFormSubmit(sportsman, model);

            return "sportsman";
        }
    }
}
