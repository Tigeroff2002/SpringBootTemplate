package ru.vlsu.ispi.logic;

import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.daoimpl.ISportsmanDAO;
import ru.vlsu.ispi.logic.abstractions.ISportsmanHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SportsmanHandler implements ISportsmanHandler {
    private final ISportsmanDAO _sportsmanDAO;
    private final IClubDAO _clubDAO;

    public SportsmanHandler(ISportsmanDAO sportsmanDAO, IClubDAO clubDAO){
        this._sportsmanDAO = sportsmanDAO;
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
    public void SportsmanFormOpen(Model model) throws SQLException {
        model.addAttribute(new Sportsman());
        model.addAttribute(_sportsmanDAO.getAllSportsmen());
        System.out.println(_clubDAO.getAllClubs().size());
        model.addAttribute(_clubDAO.getAllClubs());
    }

    @Override
    public void SportsmanFormSubmit(Sportsman sportsman, Model model) throws SQLException {
        System.out.println("Sportsman name: " + sportsman.getName());
        if (_sportsmanDAO.ifSportsmanExists(sportsman)) {
            _sportsmanDAO.create(sportsman);
        } else {
            _sportsmanDAO.update(sportsman);
        }
        System.out.println(_sportsmanDAO.getAllSportsmen().size());
        model.addAttribute(_sportsmanDAO.getAllSportsmen());
    }
}
