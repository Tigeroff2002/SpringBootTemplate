package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.IClubDAO;
import ru.vlsu.ispi.daoimpl.ISportsmanDAO;
import ru.vlsu.ispi.logic.abstractions.IConnector;
import ru.vlsu.ispi.logic.abstractions.IHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Handler implements IHandler {
    @Autowired
    private IClubDAO clubDAO;

    @Autowired
    private ISportsmanDAO sportsmanDAO;

    @Autowired
    private IConnector _connector;

    @Override
    public void ClubFormOpen(Long id, Model model) {
        Connection connection = null;
        try{
            connection = _connector.getConnection();
            if (id == null) {
                model.addAttribute("isCreate", true);
                model.addAttribute("club", new Club());
            }
            else {
                model.addAttribute("isCreate", false);
                model.addAttribute("club", clubDAO.getById(id, connection));
            }
            System.out.println(clubDAO.getAllClubs(connection).size());
            model.addAttribute(clubDAO.getAllClubs(connection));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
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
    public void ClubFormSubmit(Club club, Model model) {
        System.out.println("Club name: " + club.getName());
        Connection connection = null;
        try{
            connection = _connector.getConnection();
            if (!clubDAO.ifClubExists(club, connection)){
                clubDAO.create(club, connection);
            }
            else {
                clubDAO.update(club, connection);
            }
            System.out.println(clubDAO.getAllClubs(connection).size());
            model.addAttribute(clubDAO.getAllClubs(connection));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void DeleteClub(Long id, Model model) {
        System.out.println("id: " + id);
        Connection connection = null;
        try{
            connection = _connector.getConnection();
            clubDAO.delete(id, connection);
            System.out.println("A club with id = " + id + " was removed from list");
            System.out.println(clubDAO.getAllClubs(connection).size());
            model.addAttribute(clubDAO.getAllClubs(connection));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void SportsmanFormOpen(Model model) {
        Connection connection = null;
        try{
            connection = _connector.getConnection();
            model.addAttribute(new Sportsman());
            model.addAttribute(sportsmanDAO.getAllSportsmen(connection));
            System.out.println(clubDAO.getAllClubs(connection).size());
            model.addAttribute(clubDAO.getAllClubs(connection));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void SportsmanFormSubmit(Sportsman sportsman, Model model) {
        System.out.println("Sportsman name: " + sportsman.getName());
        Connection connection = null;
        try{
            connection = _connector.getConnection();
            if (sportsmanDAO.ifSportsmanExists(sportsman, connection)){
                sportsmanDAO.create(sportsman, connection);
            }
            else {
                sportsmanDAO.update(sportsman, connection);
            }
            System.out.println(sportsmanDAO.getAllSportsmen(connection).size());
            model.addAttribute(sportsmanDAO.getAllSportsmen(connection));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                if (connection != null){
                    connection.close();
                }
            }
            catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
}
