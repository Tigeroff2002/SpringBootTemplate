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

import javax.validation.Valid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller

public class MainController {

    public final static String userName = "postgres";
    public final static String password = "root";
    public final static String dbms = "postgresql";
    public final static String serverName = "localhost";
    public final static String portNumber = "5432";
    public final static String DBName = "SportSpring";

    @Autowired
    private IClubDAO clubDAO;

    @Autowired
    private ISportsmanDAO sportsmanDAO;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (ClassNotFoundException ex){
            System.err.println("Драйвер PostgreSQL не найден");
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e1);
        }
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        serverName + ":" +
                        portNumber + "/" +
                        DBName,
                connectionProps);
        if (conn != null)
            System.out.println("Connected to database " + DBName);
        else
            throw new SQLException("Database was not found and connected");
        return conn;
    }

    @GetMapping("/hello/{id}")
    public String handle(@PathVariable long id, @RequestParam(required = false, name = "playerId") Integer playerId, Model model){
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
        return "index";
    }

    @GetMapping("/club")
    public String clubForm(@RequestParam(required = false, name = "id") Integer id, Model model){
        Connection connection = null;
        try{
            connection = getConnection();
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
        return "clubs";
    }

    @PostMapping("/club")
    public String clubSubmit(@ModelAttribute Club club, Model model){
        System.out.println("Club name: " + club.getName());
        Connection connection = null;
        try{
            connection = getConnection();
            if (!ifClubExists(club, connection)){
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
        return "club";
    }

    @GetMapping("/club/{id}")
    public String deleteGroup(@PathVariable long id, Model model){
        System.out.println("id: " + id);
        Connection connection = null;
        try{
            connection = getConnection();
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
        return "club";
    }

    @GetMapping("/sportsman")
    public String sportsmanForm(Model model){
        Connection connection = null;
        try{
            connection = getConnection();
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
        return "sportsmen";
    }

    @PostMapping("/sportsman")
    public String sportsmanSubmit(@Valid @ModelAttribute Sportsman sportsman, BindingResult result, Model model){
        if (result.hasErrors()){
            return "sportsmen";
        }
        System.out.println("Sportsman name: " + sportsman.getName());
        Connection connection = null;
        try{
            connection = getConnection();
            if (!ifSportsmanExists(sportsman, connection)){
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
        return "sportsman";
    }

    private boolean ifClubExists(Club club, Connection connection) throws SQLException{
        String query = "SELECT * FROM Clubs WHERE Id=?";
        Club currClub = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setLong(1, club.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    private boolean ifSportsmanExists(Sportsman sportsman, Connection connection) throws SQLException{
        String query = "SELECT * FROM Sportsmen WHERE Id=?";
        Sportsman currSportsman = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setLong(1, sportsman.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
