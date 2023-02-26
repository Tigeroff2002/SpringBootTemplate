package ru.vlsu.ispi.logic;

import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.logic.abstractions.IClubHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubHandler implements IClubHandler {
    private final IClubDAO _clubDAO;

    public ClubHandler(IClubDAO clubDAO){
        this._clubDAO = clubDAO;
    }
    @Override
    public void HandleIndex(Long id, Long playerId, Model model) {
        System.out.println("id" + id);
        System.out.println(("PlayerId" + playerId));
        model.addAttribute("message", "Hello, World!");
        Club club = new Club();
        club.setId(1L);
        club.setName("Zenit");
        model.addAttribute(club);
        List<Club> clubs = new ArrayList<Club>();
        clubs.add(club);
        model.addAttribute(clubs);
    }

    @Override
    public void ClubFormOpen(Long id, Model model) throws SQLException {
        if (id == null) {
            model.addAttribute("isCreate", true);
            model.addAttribute("club", new Club());
        }
        else {
            model.addAttribute("isCreate", false);
            model.addAttribute("club", _clubDAO.getById(id));
        }
        System.out.println(_clubDAO.getAllClubs().size());
        model.addAttribute(_clubDAO.getAllClubs());
    }

    @Override
    public void ClubFormSubmit(Club club, Model model) throws SQLException{
        System.out.println("Club name: " + club.getName());
        if (!_clubDAO.ifClubExists(club)){
            _clubDAO.create(club);
        }
        else {
            _clubDAO.update(club);
        }
        System.out.println(_clubDAO.getAllClubs().size());
        model.addAttribute(_clubDAO.getAllClubs());
    }

    @Override
    public void DeleteClub(Long id, Model model) throws SQLException{
        System.out.println("id: " + id);
        _clubDAO.delete(id);
        System.out.println("A club with id = " + id + " was removed from list");
        System.out.println(_clubDAO.getAllClubs().size());
        model.addAttribute(_clubDAO.getAllClubs());
    }
}
