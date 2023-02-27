package ru.vlsu.ispi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.logic.SportsmanHandler;
import ru.vlsu.ispi.logic.abstractions.IHandler;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller

public class SportsmanController {

    @Autowired
    private SportsmanHandler sportsmanHandler;

    public SportsmanController(SportsmanHandler sportsmanHandler){
        this.sportsmanHandler = sportsmanHandler;
    }

    @GetMapping("/sportsman/hello/{id}")
    public String handle(@PathVariable Long id, @RequestParam(required = false, name = "playerId") Long playerId, Model model){
        sportsmanHandler.HandleIndex(id, playerId, model);

        return "index";
    }

    @GetMapping("/sportsman")
    public String sportsmanForm(Model model) throws SQLException {
        sportsmanHandler.SportsmanFormOpen(model);

        return "sportsmen";
    }

    @PostMapping("/sportsman")
    public String sportsmanSubmit(@Valid @ModelAttribute Sportsman sportsman, BindingResult result, Model model) throws SQLException{
        if (result.hasErrors()){
            return "sportsmen";
        }
        else {
            sportsmanHandler.SportsmanFormSubmit(sportsman, model);

            return "sportsman";
        }
    }
}
