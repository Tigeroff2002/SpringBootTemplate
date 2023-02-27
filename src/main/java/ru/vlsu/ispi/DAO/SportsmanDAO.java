package ru.vlsu.ispi.DAO;

import ru.vlsu.ispi.beans.Club;
import ru.vlsu.ispi.beans.Sportsman;
import ru.vlsu.ispi.daoimpl.DAOConnector;
import ru.vlsu.ispi.daoimpl.ISportsmanDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SportsmanDAO extends DAOConnector implements ISportsmanDAO {

    private final DBImplementation impl;

    public SportsmanDAO(DBImplementation impl){
        this.impl = impl;
    }
    public void create(Sportsman sportsman) throws SQLException{
        Connection connection = getConnection();
        String query = "INSERT INTO Sportsmen (Id, ClubId, Name, gender, Age) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, sportsman.getId());
            statement.setLong(2, sportsman.getClubId());
            statement.setString(3, sportsman.getName());
            statement.setString(4, sportsman.getGender());
            statement.setLong(5, sportsman.getAge());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("A new sportsman was inserted successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }
    public void update(Sportsman sportsman) throws SQLException{
        Connection connection = getConnection();
        String query = "UPDATE Sportsmen SET ClubId=?, Name=?, gender=?, Age=? WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, sportsman.getClubId());
            statement.setString(2, sportsman.getName());
            statement.setString(3, sportsman.getGender());
            statement.setLong(4, sportsman.getAge());
            statement.setLong(5, sportsman.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0)
                System.out.println("A sportsman was updated successfully");
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }
    public void delete(long id) throws SQLException{
        Connection connection = getConnection();
        String query = "DELETE FROM Sportsmen WHERE Id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0)
                System.out.println("A sportsman was successfully deleted");
        } catch(SQLException ex){
            System.out.println("Query was not successfully executed");
        }
    }
    public Sportsman getById(long id) throws SQLException{
        Connection connection = getConnection();
        String query = "SELECT * FROM Sportsmen WHERE Id = " + Long.toString(id);
        Sportsman currSportsman = new Sportsman();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                currSportsman.setId(rs.getLong("Id"));
                currSportsman.setClubId(rs.getLong("ClubId"));
                currSportsman.setName(rs.getString("Name"));
                currSportsman.setGender(rs.getString("gender"));
                currSportsman.setAge(rs.getLong("Age"));
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return currSportsman;
    }

    @Override
    public boolean ifSportsmanExists(Sportsman sportsman) throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM Sportsmen WHERE Id=?";
        Sportsman currSportsman = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setLong(1, sportsman.getId());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public Club getClubBySportsmanId(long id) throws SQLException{
        Connection connection = getConnection();
        String query = "SELECT Clubs.Id, Clubs.Name FROM Clubs JOIN Sportsmen ON Clubs.Id = Sportsmen.ClubId WHERE Sportsmen.Id= " + Long.toString(id);
        Club currClub = new Club();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()){
                currClub.setId(rs.getLong("Club.Id"));
                currClub.setName(rs.getString("Club.Name"));
            }
        } catch (SQLException ex) {
            System.out.println("Query was not successfully executed");
        }
        return currClub;
    }
    public List<Sportsman> getAllSportsmen() throws SQLException{
        Connection connection = getConnection();
        String query = "SELECT * FROM Sportsmen";
        impl.sportsmen = new HashMap<Long, Sportsman>();
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Sportsman currSportsman = new Sportsman();
                currSportsman.setId(rs.getLong("Id"));
                currSportsman.setClubId(rs.getLong("ClubId"));
                currSportsman.setName(rs.getString("Name"));
                currSportsman.setGender(rs.getString("gender"));
                currSportsman.setAge(rs.getLong("Age"));
                impl.sportsmen.put(currSportsman.getId(), currSportsman);
            }
        } catch (SQLException ex){
            System.out.println("Query was not successfully executed");
        }
        return new ArrayList<>(impl.sportsmen.values());
    }
}
