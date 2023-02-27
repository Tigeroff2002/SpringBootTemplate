package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.vlsu.ispi.DAO.ClubDAO;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.logic.abstractions.IClubHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubHandler implements IClubHandler {

    private final ClubDAO clubDAO;

    public ClubHandler(ClubDAO clubDAO){
        this.clubDAO = clubDAO;
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
            model.addAttribute("club", clubDAO.getById(id));
        }
        System.out.println(clubDAO.getAllClubs().size());
        model.addAttribute(clubDAO.getAllClubs());
    }

    @Override
    public void ClubFormSubmit(Club club, Model model) throws SQLException{
        System.out.println("Club name: " + club.getName());
        if (!clubDAO.ifClubExists(club)){
            clubDAO.create(club);
        }
        else {
            clubDAO.update(club);
        }
        System.out.println(clubDAO.getAllClubs().size());
        model.addAttribute(clubDAO.getAllClubs());
    }

    @Override
    public void DeleteClub(Long id, Model model) throws SQLException{
        System.out.println("id: " + id);
        clubDAO.delete(id);
        System.out.println("A club with id = " + id + " was removed from list");
        System.out.println(clubDAO.getAllClubs().size());
        model.addAttribute(clubDAO.getAllClubs());
    }
}
