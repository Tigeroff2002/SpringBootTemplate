package ru.vlsu.ispi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.logic.ClubHandler;
import ru.vlsu.ispi.logic.abstractions.IHandler;

import java.sql.*;

@Controller

public class ClubController {

    @Autowired
    private ClubHandler clubHandler;

    @GetMapping("/club/hello/{id}")
    public String handle(@PathVariable Long id, @RequestParam(required = false, name = "playerId") Long playerId, Model model){
        clubHandler.HandleIndex(id, playerId, model);

        return "index";
    }

    @GetMapping("/club")
    public String clubForm(@RequestParam(required = false, name = "id") Long id, Model model) throws SQLException{
        clubHandler.ClubFormOpen(id, model);

        return "clubs";
    }

    @PostMapping("/club")
    public String clubSubmit(@ModelAttribute Club club, Model model) throws SQLException{
        clubHandler.ClubFormSubmit(club, model);

        return "club";
    }

    @GetMapping("/club/{id}")
    public String deleteClub(@PathVariable Long id, Model model) throws SQLException{
        clubHandler.DeleteClub(id, model);

        return "club";
    }

}
