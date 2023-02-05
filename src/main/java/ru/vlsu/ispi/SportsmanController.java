package ru.vlsu.ispi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.logic.abstractions.IHandler;

import javax.validation.Valid;

@Controller

public class SportsmanController {
    @Autowired
    private IHandler _handler;

    @GetMapping("/hello/{id}")
    public String handle(@PathVariable Long id, @RequestParam(required = false, name = "playerId") Long playerId, Model model){
        _handler.HandleIndex(id, playerId, model);

        return "index";
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
