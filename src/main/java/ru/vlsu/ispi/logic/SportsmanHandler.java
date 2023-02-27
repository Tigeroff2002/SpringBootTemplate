package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.vlsu.ispi.DAO.ClubDAO;
import ru.vlsu.ispi.DAO.SportsmanDAO;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.daoimpl.ISportsmanDAO;
import ru.vlsu.ispi.logic.abstractions.ISportsmanHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SportsmanHandler implements ISportsmanHandler {
    private final SportsmanDAO sportsmanDAO;

    private final ClubDAO clubDAO;

    public SportsmanHandler(SportsmanDAO sportsmanDAO, ClubDAO clubDAO){
        this.sportsmanDAO = sportsmanDAO;
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
    public void SportsmanFormOpen(Model model) throws SQLException {
        model.addAttribute(new Sportsman());
        model.addAttribute(sportsmanDAO.getAllSportsmen());
        System.out.println(clubDAO.getAllClubs().size());
        model.addAttribute(clubDAO.getAllClubs());
    }

    @Override
    public void SportsmanFormSubmit(Sportsman sportsman, Model model) throws SQLException {
        System.out.println("Sportsman name: " + sportsman.getName());
        if (sportsmanDAO.ifSportsmanExists(sportsman)) {
            sportsmanDAO.create(sportsman);
        } else {
            sportsmanDAO.update(sportsman);
        }
        System.out.println(sportsmanDAO.getAllSportsmen().size());
        model.addAttribute(sportsmanDAO.getAllSportsmen());
    }
}
